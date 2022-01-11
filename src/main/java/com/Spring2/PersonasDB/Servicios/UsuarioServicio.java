package com.Spring2.PersonasDB.Servicios;

import com.Spring2.PersonasDB.Entidades.Persona;
import com.Spring2.PersonasDB.Entidades.Usuario;
import com.Spring2.PersonasDB.Enums.Role;
import com.Spring2.PersonasDB.Excepciones.WebException;
import com.Spring2.PersonasDB.Repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PersonaServicio personaServicio;

    @Transactional
    public Usuario save(String username, String password, String password2, String dni) throws WebException {
        Usuario usuario = new Usuario();

        if (dni == null || dni.isEmpty()) {
            throw new WebException("El DNI no puede estar vacio");
        }
        Persona persona = personaServicio.findByDni(dni);
        if (persona == null) {
            throw new WebException("No se puede registrar a un usuario con un DNI que no exista en la base de datos");
        }
        if (findByUsername(username)!=null) {
            throw new WebException("El nombre de usuario ya esta regisrado, intente con otro");
        }
        
        if (username.isEmpty() && username == null) {
            throw new WebException("El nombre de usuario no puede estar vacio");
        } 
        if (password == null || password2 == null || password.isEmpty() || password2.isEmpty()) {
            throw new WebException("La contraseña no puede estar vacia");
        }
        if (!password.equals(password2)) {
            throw new WebException("Las contraseñas deben ser iguales");
        }
        
        //Encripta la contraseña
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        usuario.setId(persona.getId());
        usuario.setNombre(persona.getNombre());
        usuario.setApellido(persona.getApellido());
        usuario.setEdad(persona.getEdad());
        usuario.setCiudad(persona.getCiudad());
        usuario.setDni(persona.getDni());
        usuario.setUsername(username);
        usuario.setPassword(encoder.encode(password));
        usuario.setRol(Role.USER);
        personaServicio.delete(persona);
        return usuarioRepositorio.save(usuario);

    }

    
    public Usuario findByUsername(String username){
        return usuarioRepositorio.findByUsername(username);
    }
    
     public List<Usuario> listAll(){
        return usuarioRepositorio.findAll();
    }
    
    public List<Usuario> listAllByQ(String q){
        return usuarioRepositorio.findAll();
//        return usuarioRepositorio.findAllByQ("%"+q+"%");
    }

    @Override
    //El siguiente metodo se usara cuando un usuario se quiera loguear
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Usuario usuario= usuarioRepositorio.findByUsername(username);
            User user;
            List<GrantedAuthority> authorities = new ArrayList();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+usuario.getRol()));
            return new User(username, usuario.getPassword(), authorities);
        } catch (Exception e) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
    }
}

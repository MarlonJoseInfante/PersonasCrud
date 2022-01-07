package com.Spring2.PersonasDB.Servicios;

import com.Spring2.PersonasDB.Entidades.Usuario;
import com.Spring2.PersonasDB.Excepciones.WebException;
import com.Spring2.PersonasDB.Repositorios.UsuarioRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServicio {

@Autowired
private UsuarioRepositorio usuarioRepositorio;

@Transactional
public Usuario save(String username, String password, String password2) throws WebException{
    Usuario usuario= new Usuario();
    if (username.isEmpty()|| username== null) {
        throw new WebException("El nombre de usuario no puede estar vacio");
    }
    if (password== null || password2== null || password.isEmpty() || password2.isEmpty()) {
        throw new WebException("La contraseña no puede estar vacia");
    }
    if (!password.equals(password2)) {
        throw new WebException("Las contraseñas deben ser iguales");
    }
    usuario.setUserName(username);
    usuario.setPassword(password);
    return usuarioRepositorio.save(usuario);
    
}
}

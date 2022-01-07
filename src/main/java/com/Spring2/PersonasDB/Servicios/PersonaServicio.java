package com.Spring2.PersonasDB.Servicios;

import com.Spring2.PersonasDB.Entidades.Ciudad;
import com.Spring2.PersonasDB.Entidades.Persona;
import com.Spring2.PersonasDB.Excepciones.WebException;
import com.Spring2.PersonasDB.Repositorios.PersonaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class PersonaServicio {
    
    
    @Autowired
    private PersonaRepositorio personaRepositorio;
    
    /* Como la ciudad es una entidad hay que persisitirla en la base de datos, se invoca al Ciudad Servicio*/
    
    @Autowired
    private CiudadServicio ciudadServicio;
    
    @Transactional
    public Persona save(Persona persona) throws WebException{
        
        if (findByDni(persona.getDni())!= null) {
            throw new WebException("No se puede registrar a una persona con un dni que ya existe");
        }
        
        if (persona.getNombre().isEmpty() || persona.getNombre()== null || persona.getNombre().length()<3) {
            throw new WebException("El nombre no puede estar vacio o tener menos de 3 caracteres");
        }
        if (persona.getApellido().isEmpty() || persona.getApellido()== null) {
            throw new WebException("El apellido no puede estar vacio");
        }
        if (persona.getEdad()== null || persona.getEdad()<1) {
            throw new WebException("La edad no debe estar vacia y debe ser mayor o igual a 0");
        }
        if (persona.getDni()== null || persona.getDni().isEmpty()) {
            throw new WebException("El dni no puede estar vacio");
        }
        if (persona.getCiudad()== null) {
            throw  new WebException("La ciudad no puede ser nula");
        } else {
            /* El ID  de la ciudad se crea de forma automatica y el nombre de la ciudad se inserta por medio del formulario*/
            persona.setCiudad(ciudadServicio.findById(persona.getCiudad()));
        }
        return personaRepositorio.save(persona);
    }
    
    /* Se pone Transactional por que va a modificar la base de datos*/
    @Transactional
    public Persona save(String nombre, String apellido, Integer edad){
        Persona persona= new Persona();
        persona.setNombre(nombre);
        persona.setEdad(edad);
        persona.setApellido(apellido);
        return personaRepositorio.save(persona);
    }
    
    public List<Persona> listAll(){
        
        return personaRepositorio.findAll();
    }
    
    public List<Persona> listAllByQ(String q){
        
        return personaRepositorio.findAllByQ("%"+q+"%");
    }
    public List<Persona> listAllByCiudad(String nombre){
        
        return personaRepositorio.findAllByCiudad(nombre);
    }
    
    public Optional<Persona> findById(String id){
        return personaRepositorio.findById(id);
    }
    
    public Persona findByDni(String dni){
        return personaRepositorio.findByDni(dni);
    }
    
    
    @Transactional
    public void delete(Persona persona){
        personaRepositorio.delete(persona);
    }
    
    @Transactional
    public void deleteById(String id){
        Optional<Persona> optional=personaRepositorio.findById(id);
        if (optional.isPresent()) {
        personaRepositorio.delete(optional.get());
        /* El get trae al objeto*/
        }
    }
    
    @Transactional
    public void deleteFieldCiudad(Ciudad ciudad){
    
        List<Persona> personas= listAllByCiudad(ciudad.getNombre());
        for(Persona persona: personas){
            persona.setCiudad(null);
        }
        personaRepositorio.saveAll(personas);
    }
    
    
}
  
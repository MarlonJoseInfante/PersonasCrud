package com.Spring2.PersonasDB.Servicios;

import com.Spring2.PersonasDB.Entidades.Ciudad;
import com.Spring2.PersonasDB.Excepciones.WebException;
import com.Spring2.PersonasDB.Repositorios.CiudadRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class CiudadServicio {

    @Autowired
    private CiudadRepositorio ciudadRepositorio;
    
    /* Solo los servicios de cada entidad tienen acceso al respositorio de su entidad*/
    @Lazy
    @Autowired
    private PersonaServicio personaServicio;
    
    @Transactional
    public Ciudad save(String name) {
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(name);
        return ciudadRepositorio.save(ciudad);
    }

    @Transactional
    public Ciudad save(Ciudad ciudad) throws WebException {
        if (ciudad.getNombre() == null) {
            throw new WebException("El nombre de la ciudad no puede ser nulo");
        }
        return ciudadRepositorio.save(ciudad);
    }
    
//    public Ciudad saveByPerson(Ciudad ciudad) throws WebException {
//        if (ciudad.getId()== null) {
//            throw new WebException("Ocurrio un error al guardar la ciudad");
//        }else{
//           Optional<Ciudad> optional= ciudadRepositorio.findById(ciudad.getId());
//            if (optional.isPresent()) {
//                ciudad=optional.get();
//            }
//        }
//        return save(ciudad);
//    }

    public List<Ciudad> listAll() {

        return ciudadRepositorio.findAll();
    }

    public List<Ciudad> listAll(String q) {

        return ciudadRepositorio.findAll("%" + q + "%");
    }

    public Optional<Ciudad> findById(String id) {
        return ciudadRepositorio.findById(id);
    }
    public Ciudad findById(Ciudad ciudad) {
         Optional<Ciudad> optional= ciudadRepositorio.findById(ciudad.getId());
            if (optional.isPresent()) {
                ciudad=optional.get();
            }
        return ciudad;
    }

    @Transactional
    public void delete(Ciudad ciudad) {
        ciudadRepositorio.delete(ciudad);
    }

    @Transactional
    public void deleteById(String id) {
       
        Optional<Ciudad> optional = ciudadRepositorio.findById(id);
        if (optional.isPresent()) {
            Ciudad ciudad= optional.get();
           /* personaServicio= new PersonaServicio();*/
            personaServicio.deleteFieldCiudad(ciudad);
            ciudadRepositorio.delete(ciudad);
            /* El get trae al objeto*/
        }
    }
}

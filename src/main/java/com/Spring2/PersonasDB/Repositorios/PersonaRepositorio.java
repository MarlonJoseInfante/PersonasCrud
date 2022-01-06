package com.Spring2.PersonasDB.Repositorios;

import com.Spring2.PersonasDB.Entidades.Persona;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Marlon
 */

@Repository
/* En los genericos del JPARepository va en este orden: Objeto y el tipo de ID del objeto*/
public interface PersonaRepositorio extends JpaRepository<Persona, String> {

    @Query("select p from Persona p where p.nombre LIKE :q or p.apellido LIKE :q or p.edad LIKE :q or p.ciudad.nombre LIKE :q")
    List<Persona> findAllByQ(@Param("q") String q);
    
    @Query("select p from Persona p where p.ciudad.nombre = :q")
    List<Persona> findAllByCiudad(@Param("q") String q);
}

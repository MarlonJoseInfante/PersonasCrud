package com.Spring2.PersonasDB.Repositorios;

import com.Spring2.PersonasDB.Entidades.Ciudad;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
@Repository
public interface CiudadRepositorio extends JpaRepository<Ciudad, String> {
    
    @Query("select c from Ciudad c where c.nombre LIKE :q")
    List<Ciudad> findAll(@Param("q") String q);
}

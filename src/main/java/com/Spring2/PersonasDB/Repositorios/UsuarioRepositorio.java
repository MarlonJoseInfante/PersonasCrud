
package com.Spring2.PersonasDB.Repositorios;

import com.Spring2.PersonasDB.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
     
    @Query("select u from Usuario u where u.username = :username")
    Usuario findByUsername(@Param("username") String username);
   
    
}

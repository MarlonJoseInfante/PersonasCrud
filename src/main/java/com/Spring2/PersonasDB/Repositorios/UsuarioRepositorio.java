
package com.Spring2.PersonasDB.Repositorios;

import com.Spring2.PersonasDB.Entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Component
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
     
}

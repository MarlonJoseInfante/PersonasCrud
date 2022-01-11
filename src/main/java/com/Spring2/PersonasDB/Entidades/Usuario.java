
package com.Spring2.PersonasDB.Entidades;



import com.Spring2.PersonasDB.Enums.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Entity
public class Usuario extends Persona{
    /* Hibernate noes proporcion 4 tipos de forma de implementar la herencia ya que en la base de datos la herencia no esta soportada*/
    /*Joined lo que hace es generar un tabla por cada entidad, pero en la tabla del padre y del hijo hay el mismo ID*/
    /*En este caso desde el usuario se puede llamar a los atributos de persona*/
    /*Como tiene el mismo ID del padre no hace falta anotarlo*/
  
    private String username;
  
    private String password;
   @Enumerated(EnumType.STRING)
   private Role rol;

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    
    
    
}

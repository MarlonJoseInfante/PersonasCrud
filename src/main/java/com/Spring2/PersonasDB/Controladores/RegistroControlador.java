package com.Spring2.PersonasDB.Controladores;

import com.Spring2.PersonasDB.Excepciones.WebException;
import com.Spring2.PersonasDB.Servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/registro")
public class RegistroControlador {
    
    @Autowired
    private UsuarioServicio usuarioServicio;
   //Las comillas hacen que entre a la url del RequestMapping
    @GetMapping("")
    public String registro(){
        return "registro";
                
    }
    
    @PostMapping("")
    public String registroSave(Model model, @RequestParam String username, @RequestParam String password, @RequestParam String password2, @RequestParam String dni){
        //El redirect:/ hace que se redirija a la pagian de inicio, si solo estuviese sin la diagonal se dirige al main controller
        try {
            usuarioServicio.save(username, password, password2,dni);
              return "redirect:/";
        } catch (WebException ex) {
            // Lo que se hace es pasar los errores a la vista
            model.addAttribute("error",ex.getMessage() );
            // Si hay un error se vuelve a poner el username en el modelo
            model.addAttribute("username",username );
            return "registro";
        }
      
    }
}


package com.Spring2.PersonasDB.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginControlador {
    
    @GetMapping("")
    public String login(Model model, @RequestParam(required = false) String error, @RequestParam (required = false) String username){
        if (error != null) {
            model.addAttribute("error", "El usuario o la contraseña ingresada son incorrectos");
        }
        if (username != null) {
            model.addAttribute("error", username);
        }
        return "login";
    }
}

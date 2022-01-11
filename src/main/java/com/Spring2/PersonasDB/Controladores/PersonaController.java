package com.Spring2.PersonasDB.Controladores;

import com.Spring2.PersonasDB.Entidades.Persona;
import com.Spring2.PersonasDB.Excepciones.WebException;
import com.Spring2.PersonasDB.Servicios.CiudadServicio;
import com.Spring2.PersonasDB.Servicios.PersonaServicio;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Marlon
 */
@Controller
@RequestMapping("/persona")
public class PersonaController {

    /*Lo que hace el Autowired es que instancia una sola vez para toda la clase y Spring la utiliza segun las necesidades de la clase*/
    @Autowired
    private PersonaServicio personaServicio;
    
    @Autowired
    private CiudadServicio ciudadServicio;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/lista")
    public String listarPersonas(Model model, @RequestParam(required = false)String q) {
        if (q!=null) {
            model.addAttribute("personas", personaServicio.listAllByQ(q));
        } else {
            model.addAttribute("personas", personaServicio.listAll());
        }
        return "persona-list";
    }

    @GetMapping("/form")
    public String crearPersona(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Persona> optional = personaServicio.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("persona", optional.get());
            } else {
                return "redirect:/persona/lista";
            }
        } else {
            model.addAttribute("persona", new Persona());
        }
        model.addAttribute("ciudad", ciudadServicio.listAll());
        return "persona-form";
    }

    @PostMapping("/save")
    public String guardarPersona(Model model,RedirectAttributes redirectAttributes, @ModelAttribute Persona persona) {
        try {
            personaServicio.save(persona);
            redirectAttributes.addFlashAttribute("success", "Persona guardada con exito");
        } catch (Exception ex) {
            /* El addFlash es capaz de mandar atributos a la redireccion*/
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("persona", persona);
            model.addAttribute("ciudad", ciudadServicio.listAll());
            
            return "persona-form";
        }
        return "redirect:/persona/lista";
    }

    @GetMapping("/delete")
    /* El required = true es para decir que ese parametro si o si debe estar presente*/
    public String eliminarPersona(@RequestParam(required = true) String id) {
        personaServicio.deleteById(id);
        return "redirect:/persona/lista";

    }
}

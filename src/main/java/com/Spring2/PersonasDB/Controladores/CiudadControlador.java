package com.Spring2.PersonasDB.Controladores;

import com.Spring2.PersonasDB.Entidades.Ciudad;
import com.Spring2.PersonasDB.Servicios.CiudadServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ciudad")
public class CiudadControlador {
    
    @Autowired
    private CiudadServicio ciudadServicio;

    @GetMapping("/lista")
    public String listarCiudades(Model model, @RequestParam(required = false)String q) {
        if (q!=null) {
            model.addAttribute("ciudad", ciudadServicio.listAll(q));
        } else {
            model.addAttribute("ciudad", ciudadServicio.listAll());
        }
        return "ciudad-list";
    }

    @GetMapping("/form")
    public String crearCiudad(Model model, @RequestParam(required = false) String id) {
        if (id != null) {
            Optional<Ciudad> optional = ciudadServicio.findById(id);
            if (optional.isPresent()) {
                model.addAttribute("ciudad", optional.get());
            } else {
                return "redirect:/ciudad/lista";
            }
        } else {
            model.addAttribute("ciudad", new Ciudad());
        }
        return "ciudad-form";
    }

    @PostMapping("/save")
    public String guardarCiudad(Model model,RedirectAttributes redirectAttributes, @ModelAttribute Ciudad ciudad) {
        try {
            ciudadServicio.save(ciudad);
            redirectAttributes.addFlashAttribute("success", "Ciudad guardada con exito");
        } catch (Exception ex) {
            /* El addFlash es capaz de mandar atributos a la redireccion*/
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/ciudad/lista";
    }

    @GetMapping("/delete")
    /* El required = true es para decir que ese parametro si o si debe estar presente*/
    public String eliminarCiudad(@RequestParam(required = true) String id) {
        ciudadServicio.deleteById(id);
        return "redirect:/ciudad/lista";

    }
}

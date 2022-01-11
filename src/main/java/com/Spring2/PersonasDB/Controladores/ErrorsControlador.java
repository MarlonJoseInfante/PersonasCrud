
package com.Spring2.PersonasDB.Controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorsControlador implements ErrorController{
   //Esta anotacion va aca y no al comiento de la clase por que tiene que atrapar los metodos get y post.
    // O sea cualquier tipo de error que suceda
    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public String mostrarPaginaError(HttpServletRequest httpServletRequest, Model model){
        String mensajeError= "";
        int codigoError= (int)httpServletRequest.getAttribute("javax.servlet.error.status_code");
        switch(codigoError){
            case 400:
                mensajeError = "El recurso solicitado no existe";
                break;
            case 401:
                mensajeError = "No se encuentra autorizado";
                break;
            case 403:
                mensajeError = "No tiene permiso para acceder al recurso";
                break;
            case 404:
                mensajeError = "El recurso solicitado no se ha encontrado";
                break;
            case 500:
                mensajeError = "El servidor no puede realizar con exito la operacion";
                break;
        }
        model.addAttribute("codigo", codigoError);
        model.addAttribute("mensaje", mensajeError);
        return "error";
    }
}

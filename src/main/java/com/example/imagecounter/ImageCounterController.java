package com.example.imagecounter;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageCounterController {

    private int counter1 = 2240; // valor inicial villano
    private int counter2 = 800;  // valor inicial entrenamiento
    // Obtiene la contraseña de la variable de entorno "ADMIN_PASSWORD" de Railway
    private final String ADMIN_PASSWORD = System.getenv("ADMIN_PASSWORD");

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("value1", counter1);
        model.addAttribute("value2", counter2);
        return "index"; // nombre del archivo HTML (index.html)
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateCounter(@RequestParam int image, @RequestParam int amount) {
        if (image == 1) {
            counter1 = Math.max(0, counter1 + amount);
            return String.valueOf(counter1);
        } else if (image == 2) {
            counter2 = Math.max(0, counter2 + amount);
            return String.valueOf(counter2);
        }
        return "0"; // valor por defecto si la imagen no es válida
    }

    // Endpoint para login admin; requiere ?admin=true en la URL
    @PostMapping("/admin/login")
    @ResponseBody
    public String adminLogin(@RequestParam String password,
                             @RequestParam(required = false, defaultValue = "false") boolean admin,
                             HttpSession session) {
        if (!admin) {
            return "Unauthorized";
        }
        if (ADMIN_PASSWORD != null && ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("admin", true);
            return "success";
        }
        return "failure";
    }

    // Endpoint para actualizar contadores en modo admin; también requiere ?admin=true
    @PostMapping("/admin/update")
    @ResponseBody
    public String updateCountersAdmin(@RequestParam int counter1,
                                      @RequestParam int counter2,
                                      @RequestParam(required = false, defaultValue = "false") boolean admin,
                                      HttpSession session) {
        if (!admin) {
            return "Unauthorized";
        }
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin == null || !isAdmin) {
            return "Unauthorized";
        }
        // Actualiza los contadores sin aplicar limitaciones
        this.counter1 = counter1;
        this.counter2 = counter2;
        return "success";
    }
}

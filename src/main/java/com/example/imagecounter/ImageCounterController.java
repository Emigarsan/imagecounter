package com.example.imagecounter;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageCounterController {

    private int counter1 = 2240; // valor inicial villano
    private int counter2 = 800;  // valor inicial entrenamiento
    // Puedes cambiar esta contraseña por la que prefieras
    private final String ADMIN_PASSWORD = "admin123";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("value1", counter1);
        model.addAttribute("value2", counter2);
        return "index"; // el nombre del archivo HTML (index.html)
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateCounter(@RequestParam int image, @RequestParam int amount) {
        if (image == 1) {
            // Se mantiene la lógica de usuario (con limitaciones)
            counter1 = Math.max(0, counter1 + amount);
            return String.valueOf(counter1);
        } else if (image == 2) {
            counter2 = Math.max(0, counter2 + amount);
            return String.valueOf(counter2);
        }
        return "0"; // valor por defecto si la imagen no es válida
    }

    @PostMapping("/admin/login")
    @ResponseBody
    public String adminLogin(@RequestParam String password, HttpSession session) {
        if (ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("admin", true);
            return "success";
        }
        return "failure";
    }

    @PostMapping("/admin/update")
    @ResponseBody
    public String updateCountersAdmin(@RequestParam int counter1, @RequestParam int counter2, HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin == null || !isAdmin) {
            return "Unauthorized";
        }
        // Actualiza los contadores sin limitaciones
        this.counter1 = counter1;
        this.counter2 = counter2;
        return "success";
    }
}

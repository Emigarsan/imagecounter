package com.example.imagecounter;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageCounterController {

    private int counter1 = 2240; // Valor inicial villano
    private int counter2 = 800;  // Valor inicial entrenamiento

    private final String ADMIN_PASSWORD = System.getenv("ADMIN_PASSWORD");

    // Página principal
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("value1", counter1);
        model.addAttribute("value2", counter2);
        return "index";
    }

    // Actualización básica (usuarios normales)
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
        return "0";
    }

    // Página de administración (GET)
    @GetMapping("/admin")
    public String adminPage(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin != null && isAdmin) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("counter1", counter1);
            model.addAttribute("counter2", counter2);
        } else {
            model.addAttribute("notLoggedIn", true);
        }
        return "admin";
    }

    // Login del admin (POST)
    @PostMapping("/admin/login")
    public String handleAdminLogin(@RequestParam String password,
                                   HttpSession session,
                                   Model model) {
        if (ADMIN_PASSWORD != null && ADMIN_PASSWORD.equals(password)) {
            session.setAttribute("admin", true);
            return "redirect:/admin";
        } else {
            model.addAttribute("notLoggedIn", true);
            model.addAttribute("loginFailed", true);
            return "admin";
        }
    }

    // Actualizar contadores como admin (POST)
    @PostMapping("/admin/update")
    public String updateCountersFromAdmin(@RequestParam int counter1,
                                          @RequestParam int counter2,
                                          HttpSession session,
                                          Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin == null || !isAdmin) {
            return "redirect:/admin";
        }

        this.counter1 = counter1;
        this.counter2 = counter2;

        model.addAttribute("loggedIn", true);
        model.addAttribute("updateSuccess", true);
        model.addAttribute("counter1", this.counter1);
        model.addAttribute("counter2", this.counter2);
        return "admin";
    }

    // Visualización para pantalla grande
    @GetMapping("/display")
    public String displayOnly(Model model) {
        model.addAttribute("value1", counter1);
        model.addAttribute("value2", counter2);
        return "display";
    }
}

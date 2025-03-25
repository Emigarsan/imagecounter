package com.example.imagecounter;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageCounterController {

    private int counter1 = 2560; // Valor inicial villano
    private int counter2 = 800;  // Valor inicial entrenamiento

    private String villainImageMode = "normal"; // Estado de imagen del villano: "normal" o "alt"
    private boolean gameEnded = false; // Indica si la partida ha terminado

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
        if (gameEnded) return "0"; // No se puede modificar si la partida ha terminado

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
            model.addAttribute("value1", counter1);
            model.addAttribute("value2", counter2);
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

    // Actualización de contadores desde admin
    @PostMapping("/admin/update")
    @ResponseBody
    public String updateCountersAdmin(@RequestParam("value1") int newValue1,
                                      @RequestParam("value2") int newValue2,
                                      HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin == null || !isAdmin || gameEnded) {
            return "Unauthorized";
        }

        this.counter1 = newValue1;
        this.counter2 = newValue2;
        return "success";
    }

    // Cambiar modo de imagen del villano desde admin
    @PostMapping("/admin/set-image")
    @ResponseBody
    public String setVillainImage(@RequestParam("imageMode") String mode,
                                  HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin == null || !isAdmin || gameEnded) {
            return "Unauthorized";
        }

        if (mode.equals("normal") || mode.equals("alt")) {
            villainImageMode = mode;
            return "success";
        } else {
            return "Invalid mode";
        }
    }

    // Finalizar partida desde admin
    @PostMapping("/admin/end-game")
    @ResponseBody
    public String endGame(HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("admin");
        if (isAdmin == null || !isAdmin) {
            return "Unauthorized";
        }

        this.gameEnded = true;
        return "success";
    }

    // Visualización para pantalla grande
    @GetMapping("/display")
    public String displayOnly(Model model) {
        model.addAttribute("value1", counter1);
        model.addAttribute("value2", counter2);
        return "display";
    }

    // Valores actuales para display y cliente
    @GetMapping("/display/values")
    @ResponseBody
    public Map<String, Object> getDisplayValues() {
        Map<String, Object> data = new HashMap<>();
        data.put("value1", counter1);
        data.put("value2", counter2);
        String imagePath = "/images/image1.jpg";
        if ("alt".equals(villainImageMode)) {
            imagePath = "/images/image1_alt.jpg";
        }
        data.put("image1", imagePath);
        data.put("gameEnded", gameEnded);
        return data;
    }
    // QR
    @GetMapping("/qr")
	public String redirectToQr() {
	return "redirect:/qr.html";
    }

}

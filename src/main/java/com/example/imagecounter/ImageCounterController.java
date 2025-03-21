package com.example.imagecounter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageCounterController {

    private int counter1 = 2240; // valor inicial villano
    private int counter2 = 800;  // valor inicial entrenamiento

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("value1", counter1);
        model.addAttribute("value2", counter2);
        return "index"; // el nombre del archivo HTML (index.html)
    }

    @PostMapping("/update")
    @ResponseBody
    public String updateCounter(@RequestParam("counter") int counterId, @RequestParam int amount) {
        if (counterId == 1) {
            counter1 = Math.max(0, counter1 + amount);
            return String.valueOf(counter1);
        } else if (counterId == 2) {
            counter2 = Math.max(0, counter2 + amount);
            return String.valueOf(counter2);
        }
        return "0"; // valor por defecto si el ID no es válido
    }

    @PostMapping("/set")
    @ResponseBody
    public String setCounter(
            @RequestParam("counter") int counterId,
            @RequestParam int value,
            @RequestParam String key
    ) {
        final String ADMIN_KEY = System.getenv("ADMIN_KEY");

        // Debug: imprimir claves para depuración
        System.out.println("Clave recibida: " + key);
        System.out.println("Clave esperada: " + ADMIN_KEY);

        if (ADMIN_KEY == null || !ADMIN_KEY.equals(key)) {
            return "ACCESO DENEGADO";
        }

        if (counterId == 1) {
            counter1 = value;
            return String.valueOf(counter1);
        } else if (counterId == 2) {
            counter2 = value;
            return String.valueOf(counter2);
        }
        return "0";
    }
}

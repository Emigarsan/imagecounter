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

    @PostMapping("/set")
    @ResponseBody
    public String setCounter(
            @RequestParam int image,
            @RequestParam int value,
            @RequestParam String key
    ) {
        final String ADMIN_KEY = System.getenv("ADMIN_KEY");

        if (ADMIN_KEY == null || !ADMIN_KEY.equals(key)) {
            return "ACCESO DENEGADO";
        }

        if (image == 1) {
            counter1 = value;
            return String.valueOf(counter1);
        } else if (image == 2) {
            counter2 = value;
            return String.valueOf(counter2);
        }
        return "0";
    }
}
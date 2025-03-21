package com.example.imagecounter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ImageCounterController {

    private int value1 = 0;
    private int value2 = 0;

    @GetMapping("/")
    public String showImages(Model model) {
        model.addAttribute("value1", value1);
        model.addAttribute("value2", value2);
        return "index";
    }

    @PostMapping("/update")
    @ResponseBody
    public int updateValue(@RequestParam("image") int image, @RequestParam("amount") int amount) {
        if (image == 1) {
            value1 += amount;
            return value1;
        } else if (image == 2) {
            value2 += amount;
            return value2;
        }
        return 0;
    }
}

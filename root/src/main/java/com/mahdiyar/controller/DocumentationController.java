package com.mahdiyar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mahdiyar
 */
@Controller
public class DocumentationController {
    @RequestMapping("/documentation")
    public String home() {
        return "redirect:/swagger-ui.html";
    }
}

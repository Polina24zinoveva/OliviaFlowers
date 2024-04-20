package com.example.OliviaFlowers.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class CatalogController {
    @GetMapping("/catalog")
    public String catalog(){
        return "catalog";
    }
}

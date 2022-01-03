package com.luxoft.oleksandr_shevchenko.webshop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ResourcesController {

    @RequestMapping(value = "/resources/static")
    public String staticResource(Model model) {
        return "resources/static";
    }
}

package com.luxoft.oleksandr_shevchenko.webshop.web.controller;

import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import com.luxoft.oleksandr_shevchenko.webshop.service.ProductService;
import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService productService;
    private final SecurityService securityService;

    @GetMapping()
    protected String showAllProducts(HttpServletRequest req, Model model) {
        dataForProductsList(req, model);
        return "products_list";
    }


    @GetMapping("/add")
    protected String getAddProductPage() {
        return "add_product";
    }


    @PostMapping("/add")
    protected String addProduct(@RequestParam String name, @RequestParam String price, Model model) {
            try {
                if(name != null && name.length() > 0 && price != null) {
                    try {
                        Product product = Product.builder()
                                .name(name)
                                .price(Double.parseDouble(price))
                                .creationDate(LocalDateTime.now().withNano(0).withSecond(0))
                                .build();
                        productService.add(product);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    String msgSuccess = "Product <i>" + name + "</i> was successfully added!";
                    model.addAttribute("msgSuccess", msgSuccess);
                    return "add_product";

                } else {
                    String errorMsg = "Please fill up all fields!";
                    model.addAttribute("errorMsg", errorMsg);
                    return "add_product";
                }
            } catch (Exception e) {
                String errorMsg = "Please fill up all fields!";
                model.addAttribute("errorMsg", errorMsg);
                return "add_product";
            }
    }


    @PostMapping("/delete")
    protected String deleteProduct(HttpServletRequest req, Model model) {
        dataForProductsList(req, model);

        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.prFindById(id);
        String name = product.getName();

        productService.remove(id);

        String msgSuccess = "Product " + name + " was successfully deleted!";
        model.addAttribute("msgSuccess", msgSuccess);
        return "products_list";
    }


    @GetMapping("/edit")
    protected String getEditProductPage(@RequestParam String id, Model model) {
        Product product = productService.prFindById(Integer.parseInt(id));
        model.addAttribute("product", product);
        return "edit_product";
    }


    @PostMapping("/edit")
    protected String editProduct(@RequestParam String id, @RequestParam String name, @RequestParam String price, Model model) {
            try {
                Product product = Product.builder().
                        name(name)
                        .price(Double.parseDouble(price))
                        .build();

                product.setId(Integer.parseInt(id));
                model.addAttribute("product", product);

                if(name != null && name.length() > 0 && price != null) {
                    productService.edit(product);

                    String msgSuccess = "Product <i>" + name + "</i> was successfully changed!";
                    model.addAttribute("msgSuccess", msgSuccess);
                    return "edit_product";

                } else {
                    String errorMsg = "Please fill up all fields";
                    model.addAttribute("errorMsg", errorMsg);
                    return "edit_product";
                }
            } catch (Exception e) {
                String errorMsg = "Please fill up all fields";
                model.addAttribute("errorMsg", errorMsg);
                return "edit_product";
            }
    }


    private void dataForProductsList(HttpServletRequest req, Model model) {
        List<Product> products = productService.findAll();
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("usrEmail");
        model.addAttribute("products", products);
        model.addAttribute("usrEmail", email);
        model.addAttribute("login", Boolean.toString(securityService.isAuth(req)));
    }



}

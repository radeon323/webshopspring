package com.luxoft.oleksandr_shevchenko.webshop.web.controller;

import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import com.luxoft.oleksandr_shevchenko.webshop.service.ProductService;
import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class ProductController {

    private final ProductService productService;
    private final SecurityService securityService;


    @Autowired
    public ProductController(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }


    @RequestMapping(path = "/products", method = RequestMethod.GET)
    protected String showAllProducts(HttpServletRequest req, Model model) {
        dataForProductsList(req, model);
        return "products_list";
    }


    @RequestMapping(path = "/products/add", method = RequestMethod.GET)
    protected String getAddProductPage() {
        return "add_product";
    }


    @RequestMapping(path = "/products/add", method = RequestMethod.POST)
    protected String addProduct(@RequestParam String name, @RequestParam String price, Model model) {
            try {
                if(name != null && name.length() > 0 && price != null) {
                    try {
                        Product product = Product.builder()
                                .name(name)
                                .price(Double.parseDouble(price))
                                .creationDate(Timestamp.valueOf(LocalDateTime.now().withNano(0).withSecond(0)))
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


    @RequestMapping(path = "/products/delete", method = RequestMethod.POST)
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


    @RequestMapping(path = "/products/edit", method = RequestMethod.GET)
    protected String getEditProductPage(@RequestParam String id, Model model) {
        Product product = productService.prFindById(Integer.parseInt(id));
        model.addAttribute("product", product);
        return "edit_product";
    }


    @RequestMapping(path = "/products/edit", method = RequestMethod.POST)
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
        String email = (String) session.getAttribute("email");
        model.addAttribute("products", products);
        model.addAttribute("email", email);
        model.addAttribute("login", Boolean.toString(securityService.isAuth(req)));
    }



}

package com.luxoft.oleksandr_shevchenko.webshop.web.controller;

import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
import com.luxoft.oleksandr_shevchenko.webshop.entity.User;
import com.luxoft.oleksandr_shevchenko.webshop.service.ProductService;
import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
import com.luxoft.oleksandr_shevchenko.webshop.service.UserService;
import com.luxoft.oleksandr_shevchenko.webshop.web.templater.PageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final List<String> userTokens;
    private final SecurityService securityService;

    @Autowired
    public ProductController(ProductService productService, UserService userService, List<String> userTokens, SecurityService securityService) {
        this.productService = productService;
        this.userService = userService;
        this.userTokens = userTokens;
        this.securityService = securityService;
    }



    @RequestMapping(path = "/products", method = RequestMethod.GET)
    @ResponseBody
    protected String findAll(HttpServletRequest req, Model model) {
            List<Product> products = productService.findAll();
//            model.addAttribute("products", products);
//            model.addAttribute("email", email);
//            model.addAttribute("login", Boolean.toString(securityService.isAuth(req)));
//            return "products_list";

            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            HashMap<String, Object> parameters = new HashMap<>();
            parameters.put("products", products);
            parameters.put("login", Boolean.toString(securityService.isAuth(req)));
            parameters.put("email", email);
            PageGenerator instance = PageGenerator.instance();
            return instance.getPage("products_list.html", parameters);
    }

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    @ResponseBody
    protected String test() {
        PageGenerator instance = PageGenerator.instance();
        return instance.getPage("test.html");
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    @ResponseBody
    protected String getLoginPage() {
        PageGenerator instance = PageGenerator.instance();
        return instance.getPage("login.html");
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    protected String login(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        System.out.println("email - " + email + " : password - " + password);

        if(userService.isUserExist(email)) {
            User user = userService.findByEmail(email);

            String md5 = null;
            try {
                md5 = SecurityService.md5(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            if(user.getPassword().equals(md5)) {
                String userToken = UUID.randomUUID().toString();
                System.out.println("User Token :" + userToken);

                userTokens.add(userToken);

                HttpSession session = req.getSession();
                session.setAttribute("email", email);
                session.setMaxInactiveInterval(-1);
                Cookie cookie = new Cookie("user-token", userToken);

                resp.addCookie(cookie);
                resp.sendRedirect("/products");
//                return "redirect:/";

            } else {
                PageGenerator instance = PageGenerator.instance();
                String errorMsg = "Please enter correct password. <a href='/login'> Forgot password?</a>";
                Map<String, Object> parameters = Map.of("errorMsg", errorMsg);
                return instance.getPage("login.html", parameters);
            }
        } else {
            PageGenerator instance = PageGenerator.instance();
            String errorMsg = "User not found. Please enter correct email or <a href='/register'>register</a>.";
            Map<String, Object> parameters = Map.of("errorMsg", errorMsg);
            return instance.getPage("login.html", parameters);
        }

        return "redirect:/";
    }

}

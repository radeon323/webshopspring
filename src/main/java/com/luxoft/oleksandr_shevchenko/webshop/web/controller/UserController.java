package com.luxoft.oleksandr_shevchenko.webshop.web.controller;

import com.luxoft.oleksandr_shevchenko.webshop.entity.User;
import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
import com.luxoft.oleksandr_shevchenko.webshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;

@Controller
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;


    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }


    @RequestMapping(path = "/login", method = RequestMethod.GET)
    protected String getLoginPage() {
        return "login";
    }


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    protected String login(@RequestParam String email, @RequestParam String password,
                           HttpSession session, HttpServletResponse resp, Model model) {

        System.out.println("email - " + email + " : password - " + password + " is user exist?" + userService.isUserExist(email));

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
                securityService.getUserTokens().add(userToken);
                session.setAttribute("email", email);
                session.setMaxInactiveInterval(-1);

                Cookie cookie = new Cookie("user-token", userToken);
                resp.addCookie(cookie);
                return "redirect:/products";

            } else {
                String errorMsg = "Please enter correct password. <a href='/login'> Forgot password?</a>";
                model.addAttribute("errorMsg", errorMsg);
                return "login";
            }

        } else {
            String errorMsg = "User not found. Please enter correct email or <a href='/register'>register</a>.";
            model.addAttribute("errorMsg", errorMsg);
            return "login";
        }

    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    protected String logout(HttpServletRequest req, HttpServletResponse resp) {
        String userToken = securityService.getUserToken(req);
        Cookie cookie = new Cookie("user-token", userToken);
        cookie.setValue(null);
        cookie.setMaxAge(0);

        HttpSession session = req.getSession();
        session.removeAttribute("name");
        resp.addCookie(cookie);
        return "redirect:/products";
    }


    @RequestMapping(path = "/register", method = RequestMethod.GET)
    protected String getRegisterPage() {
        return "register";
    }


    @RequestMapping(path = "/register", method = RequestMethod.POST)
    protected String register(@RequestParam String email, @RequestParam String password,
                              @RequestParam(defaultValue= "") String gender, @RequestParam(defaultValue= "") String firstName,
                              @RequestParam(defaultValue= "") String lastName, @RequestParam(defaultValue= "") String about,
                              @RequestParam(defaultValue= "0") int age, Model model) {
        String md5 = null;
        try {
            md5 = SecurityService.md5(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (!userService.isUserExist(email)) {
            if (email != null && email.length() > 0 && password != null) {
                try {
                    User user = User.builder().
                            email(email)
                            .password(md5)
                            .gender(gender)
                            .firstName(firstName)
                            .lastName(lastName)
                            .about(about)
                            .age(age)
                            .build();
                    userService.add(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new NullPointerException();
                }

                String msgSuccess = "User <i>" + email + "</i> was successfully registered!";
                model.addAttribute("msgSuccess", msgSuccess);
                return "register";

            } else {
                String errorMsg = "Please fill up all fields!";
                model.addAttribute("errorMsg", errorMsg);
                return "register";
            }

        } else {
            String errorMsg = "This user is already exist! <a href='/login'> Login page</a>";
            model.addAttribute("errorMsg", errorMsg);
            return "register";
        }
    }




}

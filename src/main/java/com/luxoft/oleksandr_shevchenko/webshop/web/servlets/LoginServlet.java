//package com.luxoft.oleksandr_shevchenko.webshop.web.servlets;
//
//import com.luxoft.oleksandr_shevchenko.webshop.entity.User;
//import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
//import com.luxoft.oleksandr_shevchenko.webshop.service.UserService;
//import com.luxoft.oleksandr_shevchenko.webshop.web.templater.PageGenerator;
//
//import javax.servlet.http.*;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//public class LoginServlet extends HttpServlet {
//private UserService userService;
//private List<String> userTokens;
//
//    public LoginServlet(UserService userService, List<String> userTokens) {
//        this.userService = userService;
//        this.userTokens = userTokens;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        PageGenerator instance = PageGenerator.instance();
//        String page = instance.getPage("login.html");
//        resp.getWriter().println(page);
//    }
//
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//        System.out.println("email - " + email + " : password - " + password);
//
//        if(userService.isUserExist(email)) {
//            User user = userService.findByEmail(email);
//
//            String md5 = null;
//            try {
//                md5 = SecurityService.md5(password);
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }
//
//            if(user.getPassword().equals(md5)) {
//                String userToken = UUID.randomUUID().toString();
//                System.out.println("User Token :" + userToken);
//
//                userTokens.add(userToken);
//
//                HttpSession session = req.getSession();
//                session.setAttribute("email", email);
//                session.setMaxInactiveInterval(-1);
//                Cookie cookie = new Cookie("user-token", userToken);
//
//                resp.addCookie(cookie);
//                resp.sendRedirect("/");
//
//            } else {
//                PageGenerator instance = PageGenerator.instance();
//                String errorMsg = "Please enter correct password. <a href='/login'> Forgot password?</a>";
//                Map<String, Object> parameters = Map.of("errorMsg", errorMsg);
//                String pageError = instance.getPage("login.html", parameters);
//                resp.getWriter().write(pageError);
//            }
//        } else {
//            PageGenerator instance = PageGenerator.instance();
//            String errorMsg = "User not found. Please enter correct email or <a href='/register'>register</a>.";
//            Map<String, Object> parameters = Map.of("errorMsg", errorMsg);
//            String pageError = instance.getPage("login.html", parameters);
//            resp.getWriter().write(pageError);
//        }
//    }
//
//
//}

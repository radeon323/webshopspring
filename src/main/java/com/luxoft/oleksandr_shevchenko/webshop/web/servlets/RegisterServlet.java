//package com.luxoft.oleksandr_shevchenko.webshop.web.servlets;
//
//import com.luxoft.oleksandr_shevchenko.webshop.entity.User;
//import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
//import com.luxoft.oleksandr_shevchenko.webshop.service.UserService;
//import com.luxoft.oleksandr_shevchenko.webshop.web.templater.PageGenerator;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.sql.SQLException;
//import java.util.Map;
//
//public class RegisterServlet extends HttpServlet {
//    private UserService userService;
//
//    public RegisterServlet(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        PageGenerator instance = PageGenerator.instance();
//        String page = instance.getPage("register.html");
//        resp.getWriter().println(page);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//            PageGenerator instance = PageGenerator.instance();
//            try {
//                String email = req.getParameter("email");
//                String password = req.getParameter("password");
//
//                if (!userService.isUserExist(email)) {
//
//                    if (email != null && email.length() > 0 && password != null) {
//                        try {
//                            User user = getUserFromRequest(req);
//                            userService.add(user);
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                            throw new NullPointerException();
//                        }
//
//                        String msgSuccess = "User <i>" + email + "</i> was successfully registered!";
//                        Map<String, Object> parameters = Map.of("msgSuccess", msgSuccess);
//                        String page = instance.getPage("register.html", parameters);
//                        resp.getWriter().write(page);
//
//                    } else {
//                        String errorMsg = "Please fill up all fields!";
//                        Map<String, Object> parameters = Map.of("errorMsg", errorMsg);
//                        String pageError = instance.getPage("register.html", parameters);
//                        resp.getWriter().write(pageError);
//                    }
//
//                } else {
//                    String errorMsg = "This user is already exist! <a href='/login'> Login page</a>";
//                    Map<String, Object> parameters = Map.of("errorMsg", errorMsg);
//                    String pageError = instance.getPage("register.html", parameters);
//                    resp.getWriter().write(pageError);
//                }
//
//
//            } catch (IOException e) {
////                throw new NullPointerException();
//                    String errorMsg = "Please fill up all fields!";
//                    Map<String, Object> parameters = Map.of("errorMsg", errorMsg);
//                    String pageError = instance.getPage("register.html", parameters);
//                    resp.getWriter().write(pageError);
//                }
//
//    }
//
//    private User getUserFromRequest(HttpServletRequest req) throws SQLException {
//
//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//
//
//        String gender = nullOff("gender", req);
//        System.out.println("GENDER" + gender);
//        String firstName = nullOff("firstName", req);
//        String lastName = nullOff("lastName", req);
//        String about = nullOff("about", req);
//        int age = 0;
//        System.out.println(req.getParameter("age"));
//        try {
//            age = Integer.parseInt(req.getParameter("age"));
//        } catch (NumberFormatException s) {
//            System.out.println(s);
//        }
//
//        String md5 = null;
//        try {
//            md5 = SecurityService.md5(password);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//
//            User user = User.builder().
//                    email(email)
//                    .password(md5)
//                    .gender(gender)
//                    .firstName(firstName)
//                    .lastName(lastName)
//                    .about(about)
//                    .age(age)
//                    .build();
//            return user;
//    }
//
//    private String nullOff(String txt, HttpServletRequest req) {
//        String text = null;
//        if(req.getParameter(txt) == null) {
//            text = "";
//        } else {
//            text = req.getParameter(txt);
//        }
//        return text;
//    }
//
//
//
//}

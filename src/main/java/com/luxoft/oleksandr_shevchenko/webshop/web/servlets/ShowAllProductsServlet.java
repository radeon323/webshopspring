//package com.luxoft.oleksandr_shevchenko.webshop.web.servlets;
//
//import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
//import com.luxoft.oleksandr_shevchenko.webshop.service.ProductService;
//import com.luxoft.oleksandr_shevchenko.webshop.service.SecurityService;
//import com.luxoft.oleksandr_shevchenko.webshop.service.UserService;
//import com.luxoft.oleksandr_shevchenko.webshop.web.templater.PageGenerator;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//
//public class ShowAllProductsServlet extends HttpServlet {
//    private final ProductService productService;
//    private final SecurityService securityService;
//    private List<String> userTokens;
//
//    public ShowAllProductsServlet(ProductService productService, SecurityService securityService, List<String> userTokens) {
//        this.productService = productService;
//        this.securityService = securityService;
//        this.userTokens = userTokens;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//            List<Product> products = productService.findAll();
//            PageGenerator instance = PageGenerator.instance();
//            HashMap<String, Object> parameters = new HashMap<>();
//            parameters.put("products", products);
//
//            parameters.put("login", Boolean.toString(securityService.isAuth(req)));
//
//            HttpSession session = req.getSession();
//            String email = (String) session.getAttribute("email");
//            System.out.println(email);
//            parameters.put("email", email);
//
//            String page = instance.getPage("products_list.html", parameters);
//            resp.getWriter().write(page);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
//            List<Product> products = productService.findAll();
//            PageGenerator instance = PageGenerator.instance();
//            HashMap<String, Object> parameters = new HashMap<>();
//            parameters.put("products", products);
//
//            parameters.put("login", Boolean.toString(securityService.isAuth(req)));
//
//            HttpSession session = req.getSession();
//            String email = (String) session.getAttribute("email");
//            System.out.println(email);
//            parameters.put("email", email);
//
//            try {
//                int id = Integer.parseInt(req.getParameter("id"));
//                System.out.println(id);
//                Product product = productService.prFindById(id);
//                String name = product.getName();
//
//                productService.remove(id);
//
//                String msgSuccess = "Product " + name + " was successfully deleted!";
//                parameters.put("msgSuccess", msgSuccess);
//
//                String page = instance.getPage("products_list.html", parameters);
//                resp.getWriter().write(page);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//    }
//
//}

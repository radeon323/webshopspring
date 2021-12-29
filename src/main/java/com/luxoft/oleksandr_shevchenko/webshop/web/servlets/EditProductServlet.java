//package com.luxoft.oleksandr_shevchenko.webshop.web.servlets;
//
//import com.luxoft.oleksandr_shevchenko.webshop.entity.Product;
//import com.luxoft.oleksandr_shevchenko.webshop.service.ProductService;
//import com.luxoft.oleksandr_shevchenko.webshop.web.templater.PageGenerator;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//
//
//public class EditProductServlet extends HttpServlet {
//    private ProductService productService;
//    private List<String> userTokens;
//
//    public EditProductServlet(ProductService productService, List<String> userTokens) {
//        this.productService = productService;
//        this.userTokens = userTokens;
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//            int id = Integer.parseInt(req.getParameter("id"));
//            Product product = productService.prFindById(id);
//            HashMap<String, Object> parameters = new HashMap<>();
//            parameters.put("product", product);
//
//            PageGenerator instance = PageGenerator.instance();
//            String page = instance.getPage("edit_product.html", parameters);
//            resp.getWriter().write(page);
//    }
//
//
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//            PageGenerator instance = PageGenerator.instance();
//            try {
//                int id = Integer.parseInt(req.getParameter("id"));
//                Product product = getProductFromRequest(req);
//                product.setId(id);
//                HashMap<String, Object> parameters = new HashMap<>();
//                parameters.put("product", product);
//
//
//                String name = req.getParameter("name");
//                if(name != null && name.length() > 0 && req.getParameter("price") != null) {
//                    productService.edit(product);
//
//                    String msgSuccess = "Product <i>" + name + "</i> was successfully changed!";
//                    parameters.put("msgSuccess", msgSuccess);
//                    String page = instance.getPage("edit_product.html", parameters);
//                    resp.getWriter().write(page);
//
//                } else {
//                    String errorMsg = "Please fill up all fields";
//                    parameters.put("errorMsg", errorMsg);
//                    String page = instance.getPage("edit_product.html", parameters);
//                    resp.getWriter().write(page);
//                }
//            } catch (Exception e) {
//                String errorMsg = "Please fill up all fields";
//                resp.getWriter().println(errorMsg);
//            }
//    }
//
//    private Product getProductFromRequest(HttpServletRequest req){
//        Product product = Product.builder().
//                name(req.getParameter("name"))
//                .price(Double.parseDouble(req.getParameter("price")))
//                .build();
//        return product;
//    }
//}

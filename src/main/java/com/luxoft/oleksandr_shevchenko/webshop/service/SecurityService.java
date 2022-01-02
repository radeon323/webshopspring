package com.luxoft.oleksandr_shevchenko.webshop.service;


import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SecurityService {

    private List<String> userTokens = Collections.synchronizedList(new ArrayList<>());
    private final UserService userService;

    public List<String> getUserTokens() {
        return userTokens;
    }

    public SecurityService(UserService userService) {
        this.userService = userService;
    }

    public boolean isAuth(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        System.out.println("userTokens: " + userTokens);
        if(cookies !=null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("user-token")) {
                    if(userTokens.contains(cookie.getValue())) {
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }


    public static String md5(String text) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");

        List<Object> chars = Collections.synchronizedList(new ArrayList<>());
        for (char c : text.toCharArray()) {
            chars.add(c);
        }
        chars.sort(Collections.reverseOrder());

        String txt = text + chars;
        System.out.println(txt);
        byte[] bytes = messageDigest.digest(txt.getBytes());
        System.out.println(Hex.encodeHexString(bytes));
        return Hex.encodeHexString(bytes);
    }


    public static String getUserToken(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}

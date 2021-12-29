package com.luxoft.oleksandr_shevchenko.webshop.collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CollectionsBean {


    private List<String> userTokens = Collections.synchronizedList(new ArrayList<>());

    public List<String> getUserTokens() {
        return userTokens;
    }

    public void setUserTokens(List<String> userTokens) {
        this.userTokens = userTokens;
    }
}

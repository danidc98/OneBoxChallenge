package com.onebox.challenge.storage.model;

import java.util.List;

public class CartObject {

    private String id;
    private List<ProductDAO> products;
    private long lastTimestamp;


    public List<ProductDAO> getProducts() {
        return products;
    }

    public Long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public void setProducts(List<ProductDAO> products) {
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.onebox.challenge.storage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class ProductDAO {

    private long id;

    private String description;

    private int amount;

    public ProductDAO() {
        super();
    }



    public ProductDAO id(long id) {
        this.id = id;
        return this;
    }

    /**
     * Identifier of a ProductDAO
     * @return id
     */
    public Long getId() {
        return id;
    }


    public ProductDAO description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Description of the ProductDAO.
     * @return description
     */
    public String getDescription() {
        return description;
    }



    public ProductDAO amount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Number of items you will buy of this ProductDAO.
     * @return amount
     */
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }



}



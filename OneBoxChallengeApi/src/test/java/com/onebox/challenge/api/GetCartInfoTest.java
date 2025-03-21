package com.onebox.challenge.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebox.challenge.DTO.CartItems;
import com.onebox.challenge.DTO.CreatedCart;
import com.onebox.challenge.DTO.Error;
import com.onebox.challenge.DTO.Errors;
import com.onebox.challenge.DTO.Product;
import com.onebox.challenge.OneBoxChallengeApplication;
import com.onebox.challenge.service.CartsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = OneBoxChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class GetCartInfoTest {


    @Autowired
    private MockMvc rest;

    @Value("${expiration.millis}")
    private long expirationMillis;
    @Autowired
    private CartsService cartsService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String cartId;

    @Before
    public void addCart() throws Exception {
        cartId = objectMapper.readValue(rest.perform(
                        post("/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo((print())).andReturn().getResponse().getContentAsString(), CreatedCart.class).getId();
    }

    /**
     * This test handles the situation in which the user deletes a cart
     * that already exists in the database.
     *
     * @throws Exception
     */
    @Test
    public void getCartTest() throws Exception {


        CartItems cartItems = objectMapper.readValue(
                rest.perform(
                                get("/cart?cartId=" + cartId)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo((print()))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), CartItems.class);

        assertTrue(cartItems.getItems().isEmpty());


        List<Product> products = new ArrayList<>();
        Product productIn = new Product().id(12331L).amount(1).description("This is the product");
        products.add(productIn);
        CartItems cartItemsInput = new CartItems(products);

        rest.perform(
                        patch("/cart?cartId=" + cartId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cartItemsInput)))

                .andDo((print()))
                .andExpect(status().isNoContent());

        cartItems = objectMapper.readValue(
                rest.perform(
                                get("/cart?cartId=" + cartId)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo((print()))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(), CartItems.class);

        assertTrue(cartItems.getItems().size() == 1);
        Product productOut = cartItems.getItems().get(0);
        assertEquals(productOut.getAmount(), productIn.getAmount());
        assertEquals(productOut.getId(), productIn.getId());
        assertEquals(productOut.getDescription(), productIn.getDescription());

    }


    /**
     * This test handles the situation in which the user adds products to a cart
     * that doesn´t exist in the database.
     *
     * @throws Exception
     */
    @Test
    public void getCartNotFoundTest() throws Exception {

        rest.perform(
                        get("/cart?cartId=fake-id")
                )
                .andDo((print()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new Errors().errors( Collections.singletonList(new Error()
                        .code("404")
                        .level(Error.LevelEnum.ERROR)
                        .description("Not Found.")
                        .message("The cart with id fake-id doesn´t exist in the database."))))));

    }


    /**
     * This test handles the situation in which the user adds products to a cart
     * that has expired in the database.
     *
     * @throws Exception
     */
    @Test
    public void getInfoOfExpiredCartTest() throws Exception {

        Thread.sleep(expirationMillis);

        rest.perform(
                        get("/cart?cartId="+cartId)
                )
                .andDo((print()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new Errors().errors( Collections.singletonList(new Error()
                        .code("404")
                        .level(Error.LevelEnum.ERROR)
                        .description("Not Found.")
                        .message("The cart with id "+cartId+" doesn´t exist in the database."))))));


    }


}

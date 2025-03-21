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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = OneBoxChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AddProductsTest {


    @Autowired
    private MockMvc rest;

    @Autowired
    private CartsService cartsService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String cartId;

    @Value("${expiration.millis}")
    private long expirationMillis;

    @Before
    public void addCart() throws Exception {
        cartId = objectMapper.readValue(rest.perform(
                        post("/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo((print())).andReturn().getResponse().getContentAsString(), CreatedCart.class).getId();
    }

    /**
     * This test handles the situation in which the user adds products to a cart
     * that already exists in the database.
     *
     * @throws Exception
     */
    @Test
    public void addProductTest() throws Exception {

        List<Product> products = new ArrayList<>();
        Product productIn = new Product().id(12331L).amount(1).description("This is the product");
        products.add(productIn);
        CartItems cartItemsInput = new CartItems(products);

        rest.perform(
                        patch("/cart?cartId="+cartId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cartItemsInput)))

                .andDo((print()))
                .andExpect(status().isNoContent());


        //We check that the result is in the database and it'ś reachable  through cartsService methods
        CartItems cartItemsResult = cartsService.getCartInfo(cartId);
        assert (cartItemsResult.getItems().size() == 1);
        Product product = cartItemsResult.getItems().get(0);
        assertEquals (product.getAmount(), productIn.getAmount());
        assertEquals (product.getDescription(), productIn.getDescription());
        assertEquals (product.getId(), productIn.getId());




        rest.perform(
                        patch("/cart?cartId="+cartId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cartItemsInput)))

                .andDo((print()))
                .andExpect(status().isNoContent());


        //If we add the same product, we get a list with one product but with amount+1
        cartItemsResult = cartsService.getCartInfo(cartId);
        assert (cartItemsResult.getItems().size() == 1);
        product = cartItemsResult.getItems().get(0);
        assertEquals ( product.getAmount(), productIn.getAmount()*2);
        assertEquals (product.getDescription(), productIn.getDescription());
        assertEquals (product.getId(), productIn.getId());


        productIn = new Product().id(123312L).amount(1).description("This is another product");
        products.add(productIn);
        cartItemsInput = new CartItems(products);

        rest.perform(
                        patch("/cart?cartId="+cartId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cartItemsInput)))

                .andDo((print()))
                .andExpect(status().isNoContent());

        //If we add the same product, we get a list with one product but with amount+1
        cartItemsResult = cartsService.getCartInfo(cartId);
        assert (cartItemsResult.getItems().size() == 2);
        product = cartItemsResult.getItems().get(1);
        assertEquals ( product.getAmount(), productIn.getAmount());
        assertEquals (product.getDescription(), productIn.getDescription());
        assertEquals (product.getId(), productIn.getId());
    }


    /**
     * This test handles the situation in which the user adds products to a cart
     * that doesn´t exist in the database.
     *
     * @throws Exception
     */
    @Test
    public void addProductNotFoundTest() throws Exception {

        List<Product> products = new ArrayList<>();
        Product productIn = new Product().id(12331L).amount(1).description("This is the product");
        products.add(productIn);
        CartItems cartItemsInput = new CartItems(products);

        rest.perform(
                        patch("/cart?cartId=fake-id")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cartItemsInput)))

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
    public void addProductToExpiredCartTest() throws Exception {

        Thread.sleep(expirationMillis);
        List<Product> products = new ArrayList<>();
        Product productIn = new Product().id(12331L).amount(1).description("This is the product");
        products.add(productIn);
        CartItems cartItemsInput = new CartItems(products);

        rest.perform(
                        patch("/cart?cartId="+cartId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cartItemsInput)))

                .andDo((print()))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(new Errors().errors( Collections.singletonList(new Error()
                        .code("404")
                        .level(Error.LevelEnum.ERROR)
                        .description("Not Found.")
                        .message("The cart with id "+cartId+" doesn´t exist in the database."))))));


    }



}

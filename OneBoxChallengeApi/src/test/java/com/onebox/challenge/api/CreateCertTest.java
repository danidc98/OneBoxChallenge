package com.onebox.challenge.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebox.challenge.DTO.CartItems;
import com.onebox.challenge.DTO.CreatedCart;
import com.onebox.challenge.OneBoxChallengeApplication;
import com.onebox.challenge.service.CartsService;
import com.onebox.challenge.storage.model.CartObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = OneBoxChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateCertTest {

    @Autowired
    private MockMvc rest;

    @Autowired
    private CartsService cartsService;

    private ObjectMapper objectMapper= new ObjectMapper();

    /**This test checks that the cart is created successfully. It calls the endpoint /cart with POST and checks
     * that the (empty) resource has been created.
     *
     * @throws Exception
     */
    @Test
    public void createCertTest() throws Exception {

        CreatedCart createdCart=objectMapper.readValue( rest.perform(
                        post("/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo((print()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString() , CreatedCart.class);

        String cartId= createdCart.getId();

        CartItems cartItems= cartsService.getCartInfo(cartId);
        assertTrue(cartItems.getItems().isEmpty());


    }


}

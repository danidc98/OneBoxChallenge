package com.onebox.challenge.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.onebox.challenge.DTO.CreatedCart;
import com.onebox.challenge.OneBoxChallengeApplication;
import com.onebox.challenge.storage.provider.CartsProvider;
import org.junit.Before;
import org.junit.Test;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(classes = OneBoxChallengeApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CheckDeepDeletionTest {
    @Autowired
    private MockMvc rest;

    @Value("${deletion.period}")
    private long deletionPeriod;
    @Autowired
    private CartsService cartsService;

    @Autowired
    private CartsProvider cartsProvider;
    private String cartId;

    private ObjectMapper objectMapper= new ObjectMapper();

    @Before
    public void addCart() throws Exception {
        cartId = objectMapper.readValue(rest.perform(
                        post("/cart")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo((print())).andReturn().getResponse().getContentAsString(), CreatedCart.class).getId();
    }


    /**With this test we check that the expired carts are successfully deleted after deletion.period milliseconds.
     *
     * @throws InterruptedException
     */
    @Test
    public void checkDeletedCart() throws InterruptedException {
        assertTrue(cartsProvider.findById(cartId).isPresent());
        Thread.sleep(deletionPeriod+2000L);
        assertFalse(cartsProvider.findById(cartId).isPresent());






    }

}

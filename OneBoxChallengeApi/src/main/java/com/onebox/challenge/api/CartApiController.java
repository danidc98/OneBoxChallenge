package com.onebox.challenge.api;

import com.onebox.challenge.DTO.CartItems;
import com.onebox.challenge.DTO.CreatedCart;


import com.onebox.challenge.exceptions.CartNotFoundException;
import com.onebox.challenge.storage.model.ProductDAO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.*;

import jakarta.annotation.Generated;
import com.onebox.challenge.service.CartsService;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T19:24:31.961443537+01:00[Europe/Madrid]", comments = "Generator version: 7.12.0")
@Controller
@RequestMapping("${openapi.oneBoxECommerceChallenge.base-path:}")
public class CartApiController implements CartApi {


    private final CartsService cartsService;
    private final HttpServletRequest request;
    private static Logger logger = LoggerFactory.getLogger(CartApiController.class);


    public CartApiController(CartsService cartsService, HttpServletRequest request) {
        this.request = request;
        this.cartsService = cartsService;
    }




    @Override
    /**
     * POST /cart : Creates a cart with products.
     * Creates a cart to store products with an expiration time of 10 minutes.
     *
     * @return Response containing the cart identifier when it&#39;s created. (status code 201)
     *         or Data structure containing the information about the error occurred during an Internal Server Error. (status code 500)
     */
    public ResponseEntity<CreatedCart> createCart(
    ) {
        logger.info("Received request to create a new cart.");
        String cartId = cartsService.createCart();
        logger.info("A cart with id: "+ cartId +" has been successfully created.");
        return ResponseEntity.ok(new CreatedCart().id(cartId));
    }

    @Override
    public ResponseEntity<Void> addProducts(
            @NotNull @Parameter(name = "cartId", description = "The Id of the requested Cart", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "cartId", required = true) String cartId,
            @Parameter(name = "CartItems", description = "This request body contains the items to add to the cart.", required = true) @Valid @RequestBody CartItems cartItems
    ) throws CartNotFoundException {
        logger.info("Received request to add products to the cart with id: "+ cartId);
        cartsService.addProductsToCart(cartId, cartItems.getItems());
        logger.info("The products have been succesfully added to the cart with id: "+ cartId);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> cartDelete(
            @NotNull @Parameter(name = "cartId", description = "The Id of the requested Cart", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "cartId", required = true) String cartId
    ) throws CartNotFoundException {
        logger.info("Received request to delete the cart with id: "+ cartId);

        cartsService.deleteCart(cartId);
        logger.info("The cart with id: "+ cartId+" has been successfully deleted from the database.");

        return ResponseEntity.noContent().build();
    }


    @Override
    public ResponseEntity<CartItems> retrieveCart(
            @NotNull @Parameter(name = "cartId", description = "The Id of the requested Cart", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "cartId", required = true) String cartId
    ) throws CartNotFoundException {
        logger.info("Received request to obtain information about the cart with id: "+ cartId);
        CartItems cartItems=cartsService.getCartInfo(cartId);
        logger.info("The information about the cart with id: "+ cartId+ " has been successfully obtained.");
        return ResponseEntity.ok().body(cartItems);

    }


}

/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.12.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.onebox.challenge.api;

import com.onebox.challenge.DTO.CartItems;
import com.onebox.challenge.DTO.CreatedCart;
import com.onebox.challenge.DTO.Errors;
import com.onebox.challenge.exceptions.CartNotFoundException;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T20:10:12.344131126+01:00[Europe/Madrid]", comments = "Generator version: 7.12.0")
@Validated
@Controller
@Tag(name = "Carts", description = "Operations related to cart management.")
public interface CartApi {

    default Optional<HttpServletRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * PATCH /cart : Retrieves a cart information.
     * Adds items to a cart given its id and the new items.
     *
     * @param cartId The Id of the requested Cart (required)
     * @param cartItems This request body contains the items to add to the cart. (required)
     * @return The specified resource has no content. (status code 204)
     *         or Data structure containing the information about the error occurred during an Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "addProducts",
        summary = "Retrieves a cart information.",
        description = "Adds items to a cart given its id and the new items.",
        tags = { "Carts" },
        responses = {
            @ApiResponse(responseCode = "204", description = "The specified resource has no content."),
            @ApiResponse(responseCode = "500", description = "Data structure containing the information about the error occurred during an Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Errors.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/cart",
        produces = { "application/json" },
        consumes = { "application/json" }
    )
    
    ResponseEntity<Void> addProducts(
        @NotNull @Parameter(name = "cartId", description = "The Id of the requested Cart", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "cartId", required = true) String cartId,
        @Parameter(name = "CartItems", description = "This request body contains the items to add to the cart.", required = true) @Valid @RequestBody CartItems cartItems
    ) throws CartNotFoundException;





    /**
     * DELETE /cart : Deletes a cart given its id.
     *
     * @param cartId The Id of the requested Cart (required)
     * @return The specified resource has no content. (status code 204)
     */
    @Operation(
        operationId = "cartDelete",
        summary = "Deletes a cart given its id.",
        tags = { "Carts" },
        responses = {
            @ApiResponse(responseCode = "204", description = "The specified resource has no content.")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/cart"
    )
    
    ResponseEntity<Void> cartDelete(
        @NotNull @Parameter(name = "cartId", description = "The Id of the requested Cart", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "cartId", required = true) String  cartId
    ) throws CartNotFoundException;



    /**
     * POST /cart : Creates a cart with products.
     * Creates a cart to store products with an expiration time of 10 minutes.
     *
     * @return Response containing the cart identifier when it&#39;s created. (status code 201)
     *         or Data structure containing the information about the error occurred during an Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "createCart",
        summary = "Creates a cart with products.",
        description = "Creates a cart to store products with an expiration time of 10 minutes.",
        tags = { "Carts" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Response containing the cart identifier when it's created.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedCart.class))
            }),
            @ApiResponse(responseCode = "500", description = "Data structure containing the information about the error occurred during an Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Errors.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/cart",
        produces = { "application/json" }
    )
    
    ResponseEntity<CreatedCart> createCart(
        
    ) ;


    /**
     * GET /cart : Retrieves a cart information.
     * Retrieves a cart information given its id.
     *
     * @param cartId The Id of the requested Cart (required)
     * @return Response containing the cart information. (status code 200)
     *         or Data structure containing the information about the error occurred during an Internal Server Error. (status code 500)
     */
    @Operation(
        operationId = "retrieveCart",
        summary = "Retrieves a cart information.",
        description = "Retrieves a cart information given its id.",
        tags = { "Carts" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Response containing the cart information.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CartItems.class))
            }),
            @ApiResponse(responseCode = "500", description = "Data structure containing the information about the error occurred during an Internal Server Error.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Errors.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/cart",
        produces = { "application/json" }
    )
    
    ResponseEntity<CartItems> retrieveCart(
        @NotNull @Parameter(name = "cartId", description = "The Id of the requested Cart", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "cartId", required = true) String cartId
    ) throws CartNotFoundException;

}

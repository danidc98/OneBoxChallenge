package com.onebox.challenge.DTO;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.onebox.challenge.DTO.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Data structure containing the items of a cart.
 */

@Schema(name = "CartItems", description = "Data structure containing the items of a cart.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T20:10:12.344131126+01:00[Europe/Madrid]", comments = "Generator version: 7.12.0")
public class CartItems {

  @Valid
  private List<@Valid Product> items = new ArrayList<>();

  public CartItems() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CartItems(List<@Valid Product> items) {
    this.items = items;
  }

  public CartItems items(List<@Valid Product> items) {
    this.items = items;
    return this;
  }


  /**
   * List containing the items of a cart.
   * @return items
   */
  @NotNull @Valid 
  @Schema(name = "items", description = "List containing the items of a cart.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("items")
  public List<@Valid Product> getItems() {
    return items;
  }

  public void setItems(List<@Valid Product> items) {
    this.items = items;
  }



}


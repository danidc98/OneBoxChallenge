package com.onebox.challenge.DTO;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Data structure containing information of a specific cart, with an id, description and an amount.
 */

@Schema(name = "Product", description = "Data structure containing information of a specific cart, with an id, description and an amount.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T20:10:12.344131126+01:00[Europe/Madrid]", comments = "Generator version: 7.12.0")
public class Product {

  private long id;

  private String description;

  private int amount;

  public Product() {
    super();
  }

  public Product id(long id) {
    this.id = id;
    return this;
  }

  /**
   * Identifier of a product
   * @return id
   */
  @NotNull 
  @Schema(name = "id", example = "1238944898", description = "Identifier of a product", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Product description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of the product.
   * @return description
   */
  @NotNull 
  @Schema(name = "description", example = "This ticket grants you VIP privileges in all festival venus.", description = "Description of the product.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Product amount(int amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Number of items you will buy of this product.
   * @return amount
   */
  @NotNull 
  @Schema(name = "amount", description = "Number of items you will buy of this product.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

}


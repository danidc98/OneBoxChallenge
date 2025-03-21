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
 * Data structure containing a cart identifier.
 */

@Schema(name = "CreatedCart", description = "Data structure containing a cart identifier.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T20:10:12.344131126+01:00[Europe/Madrid]", comments = "Generator version: 7.12.0")
public class CreatedCart {

  private String id;

  public CreatedCart() {
    super();
  }



  public CreatedCart id(String id) {
    this.id = id;
    return this;
  }

  /**
   * The cart identifier.
   * @return id
   */
  @NotNull 
  @Schema(name = "id", example = "1680419009", description = "The cart identifier.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


}


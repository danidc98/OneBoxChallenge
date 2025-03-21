package com.onebox.challenge.DTO;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.onebox.challenge.DTO.Error;
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
 * Data structure containing the details of errors.
 */

@Schema(name = "Errors", description = "Data structure containing the details of errors.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T20:10:12.344131126+01:00[Europe/Madrid]", comments = "Generator version: 7.12.0")
public class Errors {

  @Valid
  private List<@Valid Error> errors = new ArrayList<>();

  public Errors() {
    super();
  }



  public Errors errors(List<@Valid Error> errors) {
    this.errors = errors;
    return this;
  }


  /**
   * List of errors
   * @return errors
   */
  @NotNull @Valid 
  @Schema(name = "errors", description = "List of errors", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("errors")
  public List<@Valid Error> getErrors() {
    return errors;
  }




}


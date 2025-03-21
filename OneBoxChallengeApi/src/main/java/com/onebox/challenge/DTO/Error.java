package com.onebox.challenge.DTO;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Data structure containing the error details.
 */

@Schema(name = "Error", description = "Data structure containing the error details.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-18T20:10:12.344131126+01:00[Europe/Madrid]", comments = "Generator version: 7.12.0")
public class Error {

  private String code;

  private String message;

  /**
   * Level of the reported issue.
   */
  public enum LevelEnum {
    ERROR("ERROR"),
    
    FATAL("FATAL"),
    
    INFO("INFO"),
    
    WARNING("WARNING");

    private String value;

    LevelEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }


  }

  private @Nullable LevelEnum level;

  private @Nullable String description;

  public Error() {
    super();
  }


  public Error code(String code) {
    this.code = code;
    return this;
  }

  /**
   * Unique alphanumeric human readable error code.
   * @return code
   */
  @NotNull 
  @Schema(name = "code", example = "badRequest", description = "Unique alphanumeric human readable error code.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public String getCode() {
    return code;
  }


  public Error message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Brief summary of the reported issue.
   * @return message
   */
  @NotNull 
  @Schema(name = "message", example = "The request must contain an identifier.", description = "Brief summary of the reported issue.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }


  public Error level(LevelEnum level) {
    this.level = level;
    return this;
  }

  /**
   * Level of the reported issue.
   * @return level
   */
  
  @Schema(name = "level", description = "Level of the reported issue.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("level")
  public LevelEnum getLevel() {
    return level;
  }


  public Error description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Detailed description of the reported issue.
   * @return description
   */
  
  @Schema(name = "description", example = "This error occurs when some parameters of the request are missing.", description = "Detailed description of the reported issue.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }




}


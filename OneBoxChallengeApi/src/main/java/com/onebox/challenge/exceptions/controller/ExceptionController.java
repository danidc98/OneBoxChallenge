package com.onebox.challenge.exceptions.controller;


import com.onebox.challenge.DTO.Error;
import com.onebox.challenge.DTO.Errors;
import com.onebox.challenge.api.CartApiController;
import com.onebox.challenge.exceptions.CartNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class ExceptionController {
   private static Logger logger = LoggerFactory.getLogger(ExceptionController.class);

   @ExceptionHandler(CartNotFoundException.class)
   public ResponseEntity<Errors> handleCartNotFound(CartNotFoundException exc){
      logger.error(exc.getMessage(), exc);
      return new ResponseEntity<>(new Errors().errors(Collections.singletonList(new Error().code("404").description("Not Found.").message(exc.getMessage()).level(Error.LevelEnum.ERROR) )), HttpStatus.NOT_FOUND) ;
   }

}

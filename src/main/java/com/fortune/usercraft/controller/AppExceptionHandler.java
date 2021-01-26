package com.fortune.usercraft.controller;

import com.fortune.usercraft.controller.response.AppResponse;
import com.fortune.usercraft.exception.AppException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.lang.model.type.NullType;

import static com.fortune.usercraft.config.ErrorCode.INVALID_ARGUMENT;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = AppException.class)
    public AppResponse<NullType> handleAppException(AppException e) {
        return AppResponse.failure(e.getEcode(), e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.ok(AppResponse.failure(INVALID_ARGUMENT, message));
    }
}

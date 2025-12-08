package org.example.spring6restmvc.controlller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {


    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception) {

        ResponseEntity.BodyBuilder resBodyEntity = ResponseEntity.badRequest();

        if(exception.getCause().getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException vo = (ConstraintViolationException) exception.getCause().getCause();

            List errors  = vo.getConstraintViolations().stream()
                    .map(ConstraintViolation -> {
                        Map<String, String> errMap = new HashMap<>();
                        errMap.put(ConstraintViolation.getPropertyPath().toString(), ConstraintViolation.getMessage());
                        return errMap;
                    }).collect(Collectors.toList());
            return resBodyEntity.body(errors);
        }
        return ResponseEntity.badRequest().build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException ex) {

        List errorList = ex.getFieldErrors().stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorList);
    }
}

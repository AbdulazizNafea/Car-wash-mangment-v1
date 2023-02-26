package com.example.finalproject.advice;

import com.example.finalproject.apiException.ApiException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.format.DateTimeParseException;

@ControllerAdvice
public class Advice {


    // Our Exception

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiException(ApiException e){
        String message=e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    // Server Validation Exception
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = e.getFieldError().getDefaultMessage();
        return ResponseEntity.status(e.getStatusCode()).body(msg);
    }

    // SQL Constraint Exception
    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String msg=e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }

    // Method not allowed Exception
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(msg);
    }

    // Json parse Exception
    //HttpMessageNotReadableException
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiException> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }

    // TypesMisMatch Exception
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiException> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }
    //ConstraintViolationException
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ApiException> ConstraintViolationException(ConstraintViolationException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }
    //BindException
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<ApiException> BindException(BindException e) {
        String msg = e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }

    //JpaSystemException
    @ExceptionHandler(value = JpaSystemException.class)
    public ResponseEntity<ApiException> JpaSystemException(JpaSystemException e) {
        String msg =  e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }

    //DataIntegrityViolationException
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ApiException> DataIntegrityViolationException(DataIntegrityViolationException e) {
        String msg =  e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }

    //NullPointerException
    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<ApiException> NullPointerException(NullPointerException e) {
        String msg =  e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }

    //JpaObjectRetrievalFailureException
    @ExceptionHandler(value = JpaObjectRetrievalFailureException.class)
    public ResponseEntity<ApiException> JpaObjectRetrievalFailureException(JpaObjectRetrievalFailureException e) {
        String msg =  e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }

    //InvalidDataAccessApiUsageException
    @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
    public ResponseEntity<ApiException> InvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e) {
        String msg =  e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }
    ////DateTimeParseException
    @ExceptionHandler(value = DateTimeParseException.class)
    public ResponseEntity<ApiException> DateTimeParseException(DateTimeParseException e) {
        String msg =  e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }
    //IllegalArgumentException
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ApiException> IllegalArgumentException(IllegalArgumentException e) {
        String msg =  e.getMessage();
        return ResponseEntity.status(400).body(new ApiException(msg));
    }
}

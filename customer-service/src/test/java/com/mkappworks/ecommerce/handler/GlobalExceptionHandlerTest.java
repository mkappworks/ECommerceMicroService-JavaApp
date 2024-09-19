package com.mkappworks.ecommerce.handler;

import com.mkappworks.ecommerce.exception.CustomerNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    public void test_method_argument_not_valid_exception_handling() {
        // Mock the BindingResult and FieldErrors
        var bindingResult = mock(BindingResult.class);

        var fieldError1 = new FieldError("customerRequest", "firstName", "First name is required");
        var fieldError2 = new FieldError("customerRequest", "email", "Invalid email address");

        // Simulate the binding result with errors
        when(bindingResult.getAllErrors()).thenReturn(List.of(fieldError1, fieldError2));

        // Create MethodArgumentNotValidException with the mocked BindingResult
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);

        // Call the handler
        var responseEntity = globalExceptionHandler.handle(exception);

        // Assert the status code
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        // Assert the error response body
        ErrorResponse errorResponse = responseEntity.getBody();

        assert errorResponse != null;

        assertEquals(2, errorResponse.errors().size());
        assertEquals("First name is required", errorResponse.errors().get("firstName"));
        assertEquals("Invalid email address", errorResponse.errors().get("email"));
    }


    @Test
    public void test_customer_not_found_exception_handling() {
        var responseEntity = globalExceptionHandler.handle(new CustomerNotFoundException("error"));

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("error", responseEntity.getBody());
    }


}


//@Test
//public void test_method_argument_not_valid_exception_handling() {
//    final var responseEntity = globalExceptionHandler.handle(new MethodArgumentNotValidException("f", ));
//    assertEquals(409, responseEntity.getStatusCodeValue());
//}
package com.techcore.auth_service.keycloak.exception;

import com.techcore.auth_service.keycloak.dto.response.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;
import java.time.ZoneId;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest httpServletRequest){
        return new ResponseEntity<> (createExceptionResponse(exception, httpServletRequest), exception.getHttpStatus());
    }

    @ExceptionHandler(UserAlreadyActivateException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyActivateException(UserAlreadyActivateException ex, HttpServletRequest httpServletRequest){
        return new ResponseEntity<> (createExceptionResponse(ex, httpServletRequest), ex.getHttpStatus());
    }

    @ExceptionHandler(UserAlreadyDeactivateException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyDeactivateException(UserAlreadyDeactivateException ex, HttpServletRequest request){
        return new ResponseEntity<> (createExceptionResponse(ex, request), ex.getHttpStatus());
    }

    @ExceptionHandler(EmailHasAlreadyUsedException.class)
    public ResponseEntity<ExceptionResponse> handleEmailHasAlreadyUsedException(EmailHasAlreadyUsedException ex, HttpServletRequest request){
        return new ResponseEntity<> (createExceptionResponse(ex, request), ex.getHttpStatus());
    }

    @ExceptionHandler(PhoneNumberHasAlreadyBookedException.class)
    public ResponseEntity<ExceptionResponse> handlePhoneNumberHasAlreadyBooked(PhoneNumberHasAlreadyBookedException ex, HttpServletRequest request){
        return new ResponseEntity<> (createExceptionResponse(ex, request), ex.getHttpStatus());
    }

    @ExceptionHandler(ContactAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleContactAlreadyExistsException(ContactAlreadyExistsException ex, HttpServletRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), ex.getHttpStatus());
    }

    @ExceptionHandler(AddingYourselfIsProhibitedException.class)
    public ResponseEntity<ExceptionResponse> handleAddingYourselfIsProhibitedException(AddingYourselfIsProhibitedException ex, HttpServletRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), ex.getHttpStatus());
    }

    @ExceptionHandler(ContantNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleContantNotFoundException(ContantNotFoundException ex, HttpServletRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), ex.getHttpStatus());
    }

    @ExceptionHandler(ContactAlreadyBannedException.class)
    public ResponseEntity<ExceptionResponse> handleContactAlreadyBannedException(ContactAlreadyBannedException ex, HttpServletRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), ex.getHttpStatus());
    }

    @ExceptionHandler(ContactAlreadyUnbannedException.class)
    public ResponseEntity<ExceptionResponse> handleContactAlreadyUnbannedException(ContactAlreadyUnbannedException ex, HttpServletRequest request){
        return new ResponseEntity<>(createExceptionResponse(ex, request), ex.getHttpStatus());
    }








    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request){
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                "ValidationError",
                message,
                HttpStatus.BAD_REQUEST,
                Instant.now().atZone(ZoneId.of("Europe/Moscow")),
                request.getRequestURI()
        );

        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getHttpStatus());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFound(
            NoHandlerFoundException ex,
            HttpServletRequest httpServletRequest
    ) {
        ExceptionResponse response = new ExceptionResponse(
                "Not Found",
                "Handle " + httpServletRequest.getRequestURI() + " not found",
                HttpStatus.NOT_FOUND,
                Instant.now().atZone(ZoneId.of("Europe/Moscow")),
                httpServletRequest.getRequestURI()
                );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest httpServletRequest
    )
    {
        ExceptionResponse response = new ExceptionResponse(
                "Method Not Allowed",
                "HTTP method" + httpServletRequest.getMethod()+  "not supported for this request",
                HttpStatus.METHOD_NOT_ALLOWED,
                Instant.now().atZone(ZoneId.of("Europe/Moscow")),
                httpServletRequest.getRequestURI()
        );
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }


    private ExceptionResponse createExceptionResponse(BaseException exception, HttpServletRequest httpServletRequest) {

        ExceptionResponse exceptionResponse  = new ExceptionResponse(
                exception.getTitle(),
                exception.getDefinition(),
                exception.getHttpStatus(),
                Instant.now().atZone(ZoneId.of("Europe/Moscow")),
                httpServletRequest.getRequestURI()
        );

        return exceptionResponse;
    }
}

package com.project.fitness_project.exceptionHandler;

import com.project.fitness_project.dto.CustomErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //401 - Authentication Exception
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> incorrectCredentialsExceptionHandler(AuthenticationException e , HttpServletRequest request){


        CustomErrorResponse customErrorResponse = buildErrorResponse(HttpStatus.UNAUTHORIZED ,e.getMessage() , request);


        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customErrorResponse);
    }

    //404 - Resource Not Found exception
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> resourceNotFoundExceptionHandler(UsernameNotFoundException e , HttpServletRequest request){


        CustomErrorResponse customErrorResponse = buildErrorResponse(HttpStatus.NOT_FOUND ,e.getMessage() , request);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customErrorResponse);
    }


    //409 - Conflict
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<CustomErrorResponse> conflictExceptionHandler(DuplicateResourceException e , HttpServletRequest request){



        CustomErrorResponse customErrorResponse =
                buildErrorResponse(HttpStatus.CONFLICT ,e.getMessage() , request);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(customErrorResponse);
    }



    // 400 - Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> validationExceptionHandler(MethodArgumentNotValidException e , HttpServletRequest request){

        String message = e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();


        CustomErrorResponse customErrorResponse =
                buildErrorResponse(HttpStatus.BAD_REQUEST ,message, request);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customErrorResponse);
    }


    // 403 - Access Denied
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> accessDeniedExceptionHandler(AccessDeniedException e , HttpServletRequest request){

        CustomErrorResponse customErrorResponse =
                buildErrorResponse(HttpStatus.FORBIDDEN,e.getMessage() , request);

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(customErrorResponse);
    }

    //Bad credentials

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomErrorResponse> accessDeniedExceptionHandler(BadCredentialsException e , HttpServletRequest request){

        CustomErrorResponse customErrorResponse =
                buildErrorResponse(HttpStatus.UNAUTHORIZED,e.getMessage() , request);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(customErrorResponse);
    }







    // helper method
    private CustomErrorResponse buildErrorResponse(
            HttpStatus status,
            String message,
            HttpServletRequest request){

        CustomErrorResponse response = new CustomErrorResponse();

        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status.value());
        response.setError(status.getReasonPhrase());
        response.setMessage(message);
        response.setPath(request.getRequestURI());

        return response;
    }
}

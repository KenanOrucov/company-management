package com.example.management.exception;

import com.example.management.response.HttpResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.persistence.NoResultException;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class ExceptionHandling implements ErrorController {

    public static final String ENTER_AN_INTEGER_VALUE = "Enter an integer value";
    public static final String THIS_USER_EXIST = "This user exist";
    public static final String THIS_IMAGE_S_SIZE_IS_LARGE_TRY_ANOTHER = "This image's size is large, try another";
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    public static final String ERROR_PATH = "/error";

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<HttpResponse> notFoundException(NullPointerException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<HttpResponse> numberFormatException(MethodArgumentTypeMismatchException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_ACCEPTABLE, ENTER_AN_INTEGER_VALUE);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<HttpResponse> missingServletRequestParameterException(MissingServletRequestParameterException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_ACCEPTABLE, exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HttpResponse> constraintViolationException(ConstraintViolationException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_ACCEPTABLE, THIS_USER_EXIST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<HttpResponse> maxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
        LOGGER.error(exception.getMessage());
        return createHttpResponse(NOT_ACCEPTABLE, THIS_IMAGE_S_SIZE_IS_LARGE_TRY_ANOTHER);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus status, String message){
        return new ResponseEntity<>(new HttpResponse(now(), status.value(), status, status.getReasonPhrase(), message, null), status );
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<HttpResponse> notFound404() {
        return createHttpResponse(NOT_FOUND, "There is no mapping for this URL");
    }
}

package com.enes.ecommerce.commons.infra.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends BaseController {

    private static final String EXCEPTION_MESSAGE_SEPARATOR = ";";
    private final ResourceBundleMessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception, WebRequest request, Locale locale) {
        log.error("An error occurred! Details: ", exception);
        return createErrorResponse("common.system.error.occurred", request, locale,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindException(BindException bindException, WebRequest request, Locale locale) {
        log.error("Bad request!", bindException);
        return createFieldErrorResponse(bindException.getBindingResult(), request, locale, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidArgumentException(MethodArgumentNotValidException exception,
                                                            WebRequest request, Locale locale) {
        log.error("Method argument not valid. Message: $methodArgumentNotValidException.message", exception);
        return createFieldErrorResponse(exception.getBindingResult(), request, locale, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> createFieldErrorResponse(BindingResult bindingResult, WebRequest request,
                                                       Locale locale, HttpStatus status) {

        String[] codeAndMessage = retrieveLocalizationMessage("common.client.change.design.field", locale);
        String code = codeAndMessage[0];
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors()
                .forEach(fieldError -> {
                    String fieldName = fieldError.getField();
                    String errorMessage = retrieveLocalizationMessage(fieldError.getDefaultMessage(),
                            locale, fieldName)[1];
                    errors.put(fieldName, errorMessage);
                });

        ErrorResponse<Map<String, String>> errorResponse = ErrorResponse.<Map<String, String>>builder()
                .path(getUriFromRequest(request))
                .code(code)
                .description(errors)
                .build();

        return buildResponse(errorResponse, status);
    }


    private ResponseEntity<?> createErrorResponse(String key, WebRequest request, Locale locale, HttpStatus status,
                                                  String... args) {

        String[] codeAndMessage = retrieveLocalizationMessage(key, locale, args);
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .code(codeAndMessage[0])
                .description(codeAndMessage[1])
                .path(getUriFromRequest(request))
                .build();
        return buildResponse(errorResponse, status);
    }


    private String[] retrieveLocalizationMessage(String key, Locale locale, String... args) {
        String message = messageSource.getMessage(key, args, locale);
        return message.split(EXCEPTION_MESSAGE_SEPARATOR);
    }

    private String getUriFromRequest(WebRequest request) {
        String path = request.getDescription(false);
        return path.substring(path.indexOf("=") + 1);
    }
}

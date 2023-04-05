package ru.practicum.ewm.main.service.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {


    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ApiError handleValidationException(Exception e) {
        log.warn(e.getMessage());
        return ApiError.builder()
                .reason("Request data is not valid")
                .message(e.getMessage())
                .timestamp(Date.from(Instant.now()))
                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .status("BAD_REQUEST")
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IdNotFoundException.class)
    public ApiError handleIdNotFound(IdNotFoundException e) {
        log.warn(e.getMessage());
        return ApiError.builder()
                .reason("Object not found")
                .message(e.getMessage())
                .timestamp(Date.from(Instant.now()))
                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .status("NOT_FOUND")
                .build();
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ApiError handleForbiddenException(ForbiddenException e) {
        log.warn(e.getMessage());
        return ApiError.builder()
                .reason("Access denied")
                .message(e.getMessage())
                .timestamp(Date.from(Instant.now()))
                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .status("FORBIDDEN")
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ApiError handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.warn(NestedExceptionUtils.getMostSpecificCause(e).getMessage());
        return ApiError.builder()
                .reason("Integrity constraint has been violated")
                .message(e.getMessage())
                .timestamp(Date.from(Instant.now()))
                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .status("CONFLICT")
                .build();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PropertyValueException.class)
    public ApiError handlePropertyValueException(PropertyValueException e) {
        log.warn(e.getMessage());
        return ApiError.builder()
                .reason("Illegal property value cannot be persisted")
                .message(e.getMessage())
                .timestamp(Date.from(Instant.now()))
                .errors(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()))
                .status("BAD_REQUEST")
                .build();
    }

    @Value
    @Builder
    private static class ApiError {
        List<String> errors;
        String message;
        String reason;
        String status;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        Date timestamp;
    }

}

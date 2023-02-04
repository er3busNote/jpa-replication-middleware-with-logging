package com.opensw.master.global.error.presentation;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.opensw.master.global.error.application.ErrorService;
import com.opensw.master.global.error.domain.ErrorCode;
import com.opensw.master.global.error.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ErrorExceptionHandler {

    private final ErrorService errorService;

    public ErrorExceptionHandler(ErrorService errorService) {
        this.errorService = errorService;
    }

    /**
     * Restful API 통신시, 파라미터가 다른경우 발생함
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        Throwable throwable = e.getMostSpecificCause();
        HashMap<String, Object> message = new HashMap<>();
        if (throwable instanceof JsonParseException) {
            return getJsonParseException((JsonParseException) throwable);
        } else if (throwable instanceof UnrecognizedPropertyException) {
            return getUnrecognizedPropertyException((UnrecognizedPropertyException) throwable);
        } else if (throwable instanceof InvalidFormatException) {
            return getInvalidFormatException((InvalidFormatException) throwable);
        } else {
            if (throwable != null) {
                message.put("exceptionName", throwable.getClass().getName());
                message.put("message", throwable.getMessage());
            } else {
                message.put("message", e.getMessage());
            }
        }
        return errorService.buildError(ErrorCode.HANDLE_ACCESS_DENIED, message);
    }

    private ErrorResponse getJsonParseException(JsonParseException e) {
        HashMap<String, Object> message = new HashMap<>();
        message.put("message", e.getMessage());
        return errorService.buildError(ErrorCode.JSON_MAPPING_INVALID, message);
    }

    private ErrorResponse getUnrecognizedPropertyException(UnrecognizedPropertyException e) {
        HashMap<String, Object> message = new HashMap<>();
        message.put("unsupported", e.getPropertyName());
        message.put("supported", e.getKnownPropertyIds());
        return errorService.buildError(ErrorCode.INPUT_KEY_INVALID, message);
    }

    private ErrorResponse getInvalidFormatException(InvalidFormatException e) {
        HashMap<String, Object> message = new HashMap<>();
        message.put("unsupported", new HashMap<String, Object>(){{
            put("fieldType", e.getValue().getClass().getSimpleName());
            put("fieldValue", e.getValue());
        }});
        message.put("supported", new HashMap<String, Object>(){{
            put("fieldType", e.getTargetType().getSimpleName());
        }});
        return errorService.buildError(ErrorCode.INPUT_VALUE_INVALID, message);
    }
}

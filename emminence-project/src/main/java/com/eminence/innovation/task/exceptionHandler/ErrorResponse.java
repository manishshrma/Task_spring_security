package com.eminence.innovation.task.exceptionHandler;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Data
@Component
public class ErrorResponse {
    private String errorId;
    private String errorMessage;
    private String errorDetails;
    private HttpStatus status;

}

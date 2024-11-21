package com.kkimleang.rrms.exception;

import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceCreationException extends RuntimeException {
    private final String resourceName;
    private final transient Object fieldValue;

    public ResourceCreationException(String resourceName, Object fieldValue) {
        super(String.format("Error to create %s of this values %s", resourceName, fieldValue));
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }
}

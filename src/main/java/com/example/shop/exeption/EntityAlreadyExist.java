package com.example.shop.exeption;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EntityAlreadyExist extends RuntimeException {
    private static final long serialVersionUID = 2L;
    private String resourseName;
    private String fieldName;

    public EntityAlreadyExist(String resourseName, String fieldName) {
        super(String.format("%1s with name  %2s already exist ", resourseName, fieldName));
        this.resourseName = resourseName;
        this.fieldName = fieldName;
    }
}
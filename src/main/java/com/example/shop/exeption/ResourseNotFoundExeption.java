package com.example.shop.exeption;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourseNotFoundExeption extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String resourseName;
    private String fieldName;

    public ResourseNotFoundExeption(String resourseName, String fieldName) {
        super(String.format(  "%1s not found with name %2s ",resourseName, fieldName));
        this.resourseName = resourseName;
        this.fieldName = fieldName;
    }
}

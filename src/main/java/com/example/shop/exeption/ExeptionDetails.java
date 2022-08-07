package com.example.shop.exeption;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ExeptionDetails {
    private Date timestamp;
    private String message;
    private String details;

}

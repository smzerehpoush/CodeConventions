package com.mahdiyar.model.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Seyyed Mahdiyar Zerehpoush
 */
@Data
@NoArgsConstructor
public class BaseModel {
    private String id;
    private Date creationDate;
}

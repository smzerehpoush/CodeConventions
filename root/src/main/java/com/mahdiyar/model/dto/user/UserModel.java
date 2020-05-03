package com.mahdiyar.model.dto.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserModel {
    private String id;
    private Date creationDate;
    private String firstName;
    private String lastName;
    private String username;
}

package com.mahdiyar.model.dto.user;

import com.mahdiyar.model.base.BaseModel;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UserModel extends BaseModel {
    private String firstName;
    private String lastName;
    private String username;
}

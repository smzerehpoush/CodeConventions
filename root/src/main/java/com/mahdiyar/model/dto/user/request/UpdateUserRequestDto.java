package com.mahdiyar.model.dto.user.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
@ApiModel
public class UpdateUserRequestDto {
    private String firstName;
    private String lastName;
}
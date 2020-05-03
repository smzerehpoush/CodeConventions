package com.mahdiyar.model.general;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @author mahdiyar
 */
@Data
@NoArgsConstructor
@ApiModel
public class PagedQuery {
    @ApiModelProperty(notes = "Positive Numbers or else changed to 0")
    private Integer page;
    @ApiModelProperty(notes = "Positive Numbers. Special Value -1 means all data")
    private Integer size;

    public Integer getPage() {
        return Optional.of(page).orElse(0);
    }

    public Integer getSize() {
        return Optional.of(size).orElse(20);
    }
}

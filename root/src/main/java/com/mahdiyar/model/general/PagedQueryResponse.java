package com.mahdiyar.model.general;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author mahdiyar
 */
@Data
@AllArgsConstructor
public class PagedQueryResponse<T> {
    private List<T> content;
    private long totalRecords;
    private long filteredRecords;
}

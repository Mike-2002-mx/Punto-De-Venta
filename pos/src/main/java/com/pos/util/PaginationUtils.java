package com.pos.util;

import org.springframework.data.domain.Page;

import com.pos.dto.PaginatedResponse;

public final class PaginationUtils {
    
    private PaginationUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> PaginatedResponse<T> buildPaginatedResponse(Page<T> page) {
        PaginatedResponse<T> response = new PaginatedResponse<>();
        response.setData(page.getContent());
        response.setCurrentPage(page.getNumber());
        response.setTotalPages(page.getTotalPages());
        response.setTotalItems(page.getTotalElements());
        response.setPageSize(page.getSize());
        response.setHasNext(page.hasNext());
        response.setHasPrevious(page.hasPrevious());
        return response;
    }
}

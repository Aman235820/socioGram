package com.aman.socialMedia.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PaginationResponseDTO {

        private Object content;
        private int pageNumber;
        private int pageSize;
        private long totalElements;
        private long totalPages;
        private boolean lastPage;

}

package com.erick_marques.book_api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoogleBooksResponseDTO {
    private List<Item> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private VolumeInfo volumeInfo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VolumeInfo {
        private String title;
        private List<String> authors;
    }
}

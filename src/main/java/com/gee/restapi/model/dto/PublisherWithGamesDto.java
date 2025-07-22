package com.gee.restapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublisherWithGamesDto {

    private Long id;
    private String name;
    private List<GameDto> games;
}

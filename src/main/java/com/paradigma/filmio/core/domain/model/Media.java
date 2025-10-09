package com.paradigma.filmio.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Media {
    private UUID id;
    private String url;
    private MediaType mediaType;
}

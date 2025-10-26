package com.paradigma.filmio.core.port.in;

import com.paradigma.filmio.core.domain.model.Media;

import java.util.Set;

public interface MediaService {
    Set<Media> saveAll(Set<Media> media);
}

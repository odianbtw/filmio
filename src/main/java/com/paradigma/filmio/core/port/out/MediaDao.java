package com.paradigma.filmio.core.port.out;

import com.paradigma.filmio.core.domain.model.Media;

import java.util.Set;

public interface MediaDao {
    Set<Media> saveAll(Set<Media> media);
}

package com.paradigma.filmio.dao.postgres.dao;

import com.paradigma.filmio.core.domain.model.Media;
import com.paradigma.filmio.core.port.out.MediaDao;
import com.paradigma.filmio.dao.postgres.mapper.MediaEntityMapper;
import com.paradigma.filmio.dao.postgres.spring.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DefaultMediaDao implements MediaDao {

    private final MediaRepository mediaRepository;
    private final MediaEntityMapper mediaMapper;

    @Override
    public Set<Media> saveAll(Set<Media> media) {
        final var entities = media.stream()
                .map(mediaMapper::toMediaEntity)
                .collect(Collectors.toSet());
        entities.forEach(e -> {
            e.setCreatedAt(Instant.now());
            e.setUpdatedAt(Instant.now());
        });
        final var saved = mediaRepository.saveAll(entities);
        return saved.stream()
                .map(mediaMapper::toMedia)
                .collect(Collectors.toSet());
    }
}

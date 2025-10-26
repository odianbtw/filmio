package com.paradigma.filmio.core.service;

import com.paradigma.filmio.core.domain.model.Media;
import com.paradigma.filmio.core.port.in.MediaService;
import com.paradigma.filmio.core.port.out.MediaDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultMediaService implements MediaService {

    private final MediaDao mediaDao;
    private final UuidGenerator idGenerator;

    @Override
    public Set<Media> saveAll(Set<Media> media) {
        media.forEach(
                m -> m.setId(idGenerator.generateId())
        );
        return mediaDao.saveAll(media);
    }
}

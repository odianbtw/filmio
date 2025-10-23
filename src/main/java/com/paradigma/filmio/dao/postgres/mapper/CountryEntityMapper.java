package com.paradigma.filmio.dao.postgres.mapper;

import com.paradigma.filmio.core.domain.model.Country;
import com.paradigma.filmio.dao.postgres.model.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryEntityMapper {
    Country toCountry(CountryEntity country);
}

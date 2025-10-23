package com.paradigma.filmio.api.mapper;


import com.paradigma.filmio.api.model.CountryRepresentationV1;
import com.paradigma.filmio.core.domain.model.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryDTOMapper {
    CountryRepresentationV1 toCountryRepresentationV1(Country country);
    default Country toCountry(Integer countryId) {
        return new Country(countryId, null);
    }
}

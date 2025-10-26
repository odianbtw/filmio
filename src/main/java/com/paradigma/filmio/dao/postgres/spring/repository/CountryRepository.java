package com.paradigma.filmio.dao.postgres.spring.repository;

import com.paradigma.filmio.dao.postgres.model.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
}

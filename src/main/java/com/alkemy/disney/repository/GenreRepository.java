package com.alkemy.disney.repository;


import com.alkemy.disney.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}

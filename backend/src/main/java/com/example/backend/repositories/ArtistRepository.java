package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.models.Artist;
import com.example.backend.models.Country;
import com.example.backend.models.User;

import java.util.Optional;
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByName(String name);
}
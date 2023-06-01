package com.example.backend.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backend.models.Country;
import com.example.backend.models.Artist;
import java.util.Optional;
@Repository
public interface CountryRepository  extends JpaRepository<Country, Long>
{
    Optional<Country> findByName(String name);

}
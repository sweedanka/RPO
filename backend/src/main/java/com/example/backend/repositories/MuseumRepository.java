
package com.example.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.models.Museum;
import com.example.backend.models.Country;
import java.util.Optional;
public interface MuseumRepository extends JpaRepository<Museum, Long> {
    Optional<Museum> findByName(String name);
}
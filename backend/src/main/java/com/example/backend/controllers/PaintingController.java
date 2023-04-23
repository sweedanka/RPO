package com.example.backend.controllers;

import com.example.backend.models.Artist;
import com.example.backend.models.Museum;
import com.example.backend.models.Painting;
import com.example.backend.repositories.ArtistRepository;
import com.example.backend.repositories.MuseumRepository;
import com.example.backend.repositories.PaintingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PaintingController {

    @Autowired
    PaintingRepository paintingRepository;

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    MuseumRepository museumRepository;


    @GetMapping("/paintings")
    public List getAllPaintings() {
        return paintingRepository.findAll();
    }

    @PostMapping("/paintings")
    public ResponseEntity<Object> createPainting(@RequestBody Painting painting) throws Exception {
        try {
            Optional<Artist> aa = artistRepository.findById(painting.artist.id);
            if (aa.isPresent()) {
                painting.artist = aa.get();
            }
            Optional<Museum> mm = museumRepository.findById(painting.museum.id);
            if (mm.isPresent()) {
                painting.museum = mm.get();
            }
            Painting nc = paintingRepository.save(painting);
            return new ResponseEntity<Object>(nc, HttpStatus.OK);
        }
        catch(Exception ex) {
            String error;
            if (ex.getMessage().contains("painting.name_UNIQUE"))
                error = "paintingalreadyexists";
            else
                error = ex.getMessage();
            Map<String, String> map = new HashMap<>();
            map.put("error", error);
            return ResponseEntity.ok(map);
        }
    }
}
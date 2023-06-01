package com.example.backend.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.backend.models.*;
import com.example.backend.repositories.ArtistRepository;
import com.example.backend.repositories.CountryRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.*;
import com.example.backend.tools.DataValidationException;
import javax.validation.Valid;
/**
 * Метод, который отражает логику работы таблицы художников
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1")
public class ArtistController {
    // Здесь используется два репозитория: репозиторий артистов и репозиторий стран
    @Autowired
    ArtistRepository artistsRepository;

    @Autowired
    CountryRepository countryRepository;
    /**
     * Метод, который возвращает список артистов для данной БД
     * @return - список артистов, который представлен в JSON
     */
    @GetMapping("/artists")
    public List getAllCountries() {
        return artistsRepository.findAll();
    }

    public Page getAllArtists(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return artistsRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "name")));

        @GetMapping("/artists/{id}/paintings")
        public ResponseEntity<Object> getMuseumsFromArtist(@PathVariable(value = "id") Long artistID) {
            Optional<Artist> optionalArtists = artistsRepository.findById(artistID);
            @GetMapping("/artists/{id}")
            public ResponseEntity getArtist(@PathVariable(value = "id") Long artistId)
            throws DataValidationException {
                Artist artist = artistsRepository.findById(artistId)
                        .orElseThrow(() -> new DataValidationException("Художник с таким индексом не найден"));
                return ResponseEntity.ok(artist);
            }

            if (optionalArtists.isPresent()) {
                return ResponseEntity.ok(optionalArtists.get().paintings);
            }

            return ResponseEntity.ok(new ArrayList<Museum>());
        }

        /**
         * Метод, который добавляет артистов в базу данных
         * @param artists - Структура данных, которая поступает из PostMan в виде JSON-файла
         *                  где распарсивается и представлется в нужном для нас виде
         * @return - Статус. 404, если ок. В противном случае, будет выдавать ошибку
         * @throws Exception - выброс исключения. Обязательное требование
         */
        @PostMapping("/artists")
        public ResponseEntity<Object> createArtist(@RequestBody Artist artists) throws Exception {
            try {

                // Извлекаем самостоятельно страну из пришедших данных
                Optional<Country> cc = countryRepository.findById(artists.country.id);
                if (cc.isPresent()) {
                    artists.country = cc.get();
                }
                // Формируем новый объект класса Artists и сохраняем его в репозиторий
                Artist nc = artistsRepository.save(artists);
                return new ResponseEntity<Object>(nc, HttpStatus.OK);
            } catch (Exception exception) {
                // Указываем тип ошибки
                String error;
                if (exception.getMessage().contains("ConstraintViolationException")) {
                    error = "artistAlreadyExists";
                } else {
                    error = exception.getMessage();
                }
                Map<String, String> map = new HashMap<>();
                map.put("error", error + "\n");
                return ResponseEntity.ok(map);
            }
        }

        /**
         * Метод, который обновляет данные для художников
         * @param artistsID - ID художника, по которому будет осуществляться собственно поиск
         * @param artistDetails - детальная информация по художникам
         * @return - возвращает заголовок. Если всё ок, то 200. Иначе будет ошибка
         */
        @PutMapping("/artists/{id}")
        public ResponseEntity<Artist> updateCountry(@PathVariable(value = "id") Long artistsID,
                @RequestBody Artist artistDetails) {
            Artist artist = null;
            Optional<Artist> cc = artistsRepository.findById(artistsID);
            if (cc.isPresent()) {
                artist = cc.get();

                // Обновляем информацию по художникам
                artist.name = artistDetails.name;
                artist.century   = artistDetails.century;
                artist.country = artistDetails.country;
                artistsRepository.save(artist);
                return ResponseEntity.ok(artist);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "artist not found");
            }
        }

        /**
         * Метод, который удаляет художников
         * @param artistID - ID художника, который будет удалён из базы данных
         * @return - вернёт 200, если всё было ок
         */
        @DeleteMapping("/artists/{id}")
        public ResponseEntity<Object> deleteCountry(@PathVariable(value = "id") Long artistID) {
            Optional<Artist> artists = artistsRepository.findById(artistID);
            Map<String, Boolean> resp = new HashMap<>();
            // Возвратит true, если объект существует (не пустой)
            if (artists.isPresent()) {
                artistsRepository.delete(artists.get());
                resp.put("deleted", Boolean.TRUE);
            } else {
                resp.put("deleted", Boolean.FALSE);
            }
            return ResponseEntity.ok(resp);
        }
    }
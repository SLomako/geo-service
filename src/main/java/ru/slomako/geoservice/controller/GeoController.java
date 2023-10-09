package ru.slomako.geoservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.slomako.geoservice.data.dto.AddGeoDTO;
import ru.slomako.geoservice.data.GeoEntity;
import ru.slomako.geoservice.data.dto.UpdateCountryNameDTO;
import ru.slomako.geoservice.domain.GeoFeature;
import ru.slomako.geoservice.service.GeoService;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/geo")
@RestController
public class GeoController {

    private final GeoService geoFeatureService;

    @Autowired
    public GeoController(GeoService geoFeatureService) {
        this.geoFeatureService = geoFeatureService;
    }

    @GetMapping("/all")
    public List<GeoFeature> getAll() {
        return geoFeatureService.getAllGeoFeatures();
    }


    @PostMapping("/add")
    public ResponseEntity<GeoEntity> addGeoFeature(@RequestBody AddGeoDTO geoFeatureDTO) {
        GeoEntity newFeature = geoFeatureService.addGeoFeature(geoFeatureDTO);
        return new ResponseEntity<>(newFeature, HttpStatus.CREATED);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateCountryName(@PathVariable UUID id, @RequestBody UpdateCountryNameDTO updateDTO) {
        boolean updated = geoFeatureService.updateCountryName(id, updateDTO.getCountryName());

        if (updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
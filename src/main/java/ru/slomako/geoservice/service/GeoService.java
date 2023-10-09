package ru.slomako.geoservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.slomako.geoservice.data.dto.AddGeoDTO;
import ru.slomako.geoservice.data.GeoEntity;
import ru.slomako.geoservice.repository.GeoRepository;
import ru.slomako.geoservice.domain.GeoFeature;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GeoService {

    private final GeoRepository geoFeatureRepository;

    @Autowired
    public GeoService(GeoRepository geoFeatureRepository) {
        this.geoFeatureRepository = geoFeatureRepository;
    }

    @Transactional
    public List<GeoFeature> getAllGeoFeatures() {
        return geoFeatureRepository.findAll().stream()
                .map(entity -> new GeoFeature(entity.getName(), entity.getIsoCode(), entity.getCoordinates()))
                .collect(Collectors.toList());
    }

    @Transactional
    public GeoEntity addGeoFeature(AddGeoDTO geoFeatureDTO) {
        GeoEntity entity = new GeoEntity();
        entity.setName(geoFeatureDTO.getName());
        entity.setIsoCode(geoFeatureDTO.getIsoCode());
        entity.setCoordinates(geoFeatureDTO.getCoordinates());

        return geoFeatureRepository.save(entity);
    }

    @Transactional
    public boolean updateCountryName(UUID id, String newCountryName) {
        Optional<GeoEntity> optionalGeoFeature = geoFeatureRepository.findById(id);

        if (optionalGeoFeature.isPresent()) {
            GeoEntity geoFeature = optionalGeoFeature.get();
            geoFeature.setName(newCountryName);
            geoFeatureRepository.save(geoFeature);
            return true;
        } else {
            return false;
        }
    }
}


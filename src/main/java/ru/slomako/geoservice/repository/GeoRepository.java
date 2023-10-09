package ru.slomako.geoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.slomako.geoservice.data.GeoEntity;

import java.util.UUID;

@Repository
public interface GeoRepository extends JpaRepository<GeoEntity, UUID> {
}

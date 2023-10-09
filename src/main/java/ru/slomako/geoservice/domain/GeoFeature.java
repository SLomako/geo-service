package ru.slomako.geoservice.domain;

import java.util.List;

public record GeoFeature(String N, String I, List<List<List<List<Double>>>> C) {
}

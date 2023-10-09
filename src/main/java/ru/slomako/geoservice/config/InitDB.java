package ru.slomako.geoservice.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.slomako.geoservice.repository.GeoRepository;
import ru.slomako.geoservice.data.GeoEntity;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Component
public class InitDB {

    private static final String DATA_URL = "https://raw.githubusercontent.com/yanivam/react-svg-worldmap/main/lib/src/countries.geo.ts";

    private final GeoRepository geoFeatureRepository;

    public InitDB(GeoRepository geoFeatureRepository) {
        this.geoFeatureRepository = geoFeatureRepository;
    }

    @PostConstruct
    public void loadData() {
        try {
            InputStream inputStream = new URL(DATA_URL).openStream();

            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String content = s.hasNext() ? s.next() : "";
            content = formatJson(content);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

            GeoFeatureWrapper wrapper = objectMapper.readValue(content, GeoFeatureWrapper.class);
            geoFeatureRepository.saveAll(wrapper.getFeatures());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String formatJson(String rawContent) {
        String formattedContent = rawContent;
        formattedContent = formattedContent.replace("Côted'Ivoire", "Côted\\\"Ivoire");
        formattedContent = formattedContent.replace("export default ", "");
        formattedContent = formattedContent.replace(" as const;", "");
        formattedContent = formattedContent.replace("/* prettier-ignore */", "");
        formattedContent = formattedContent.replace("'", "\"");
        formattedContent = formattedContent.replace("N:", "\"N\":");
        formattedContent = formattedContent.replace("I:", "\"I\":");
        formattedContent = formattedContent.replace("C:", "\"C\":");
        formattedContent = formattedContent.replace("features:", "\"features\":");
        formattedContent = formattedContent.replaceAll(",(?=\\s*?[}\\]])", "");
        return formattedContent;
    }

    private static class GeoFeatureWrapper {
        @JsonProperty("features")
        private List<GeoEntity> features;

        public List<GeoEntity> getFeatures() {
            return features;
        }
    }
}

package services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.CityInfoDTO;
import dtos.CityInfoResponseDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CityServices {

    private final ObjectMapper objectMapper;

    public CityServices() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public CityInfoDTO getCityInfo(String city) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://geocoding-api.open-meteo.com/v1/search?name=" + city))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                CityInfoResponseDTO cityResponse = objectMapper.readValue(response.body(), CityInfoResponseDTO.class);
                if (cityResponse.getResults() != null && !cityResponse.getResults().isEmpty()) {
                    CityInfoDTO cityDTO = cityResponse.getResults().get(0);

                    // Ensure optional fields are not null
                    if (cityDTO.getPostcodes() == null) cityDTO.setPostcodes(List.of());
                    if (cityDTO.getCountry() == null) cityDTO.setCountry("Unknown");
                    if (cityDTO.getTimezone() == null) cityDTO.setTimezone("Unknown");

                    return cityDTO;
                }
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

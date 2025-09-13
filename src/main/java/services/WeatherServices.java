package services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dtos.WeatherInfoDTO;
import exceptions.ApiException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherServices {

    private final ObjectMapper objectMapper;
    private final String BASE_URL = "https://api.open-meteo.com/v1/forecast?latitude=";
    private final String URL_longitude = "&longitude=";
    private final String CURRENT_WEATHER = "&current_weather=true";

    public WeatherServices() {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public WeatherInfoDTO getWeatherInfo(double latitude, double longitude) {


        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + latitude + URL_longitude + longitude + CURRENT_WEATHER))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                WeatherInfoDTO weatherDTO = objectMapper.readValue(response.body(), WeatherInfoDTO.class);
                return weatherDTO;
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (ApiException | IOException | InterruptedException e) {
            throw new RuntimeException("GET request failed. " + e.getMessage());
        } catch (URISyntaxException e) {
            throw new RuntimeException("GET request failed", e);
        }
        return null;
    }
}

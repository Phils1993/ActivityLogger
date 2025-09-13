package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.CityInfoDTO;
import dtos.CityInfoResponseDTO;
import dtos.WeatherInfoDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class CityServices {


    public CityInfoDTO getCityInfo(String city) {
        ObjectMapper objectMapper = new ObjectMapper();
        CityInfoDTO cityInfoDTO = null;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://geocoding-api.open-meteo.com/v1/search?name=" + city))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String json = response.body();
                CityInfoResponseDTO cityResponse = objectMapper.readValue(json, CityInfoResponseDTO.class);

                if (cityResponse.getResults() != null && !cityResponse.getResults().isEmpty()) {
                    cityInfoDTO = cityResponse.getResults().get(0);
                }
            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityInfoDTO;
    }
}

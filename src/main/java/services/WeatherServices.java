package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.WeatherInfoDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class WeatherServices {
        public WeatherInfoDTO getWeatherInfo(double latitude, double longitude) {
            ObjectMapper objectMapper = new ObjectMapper();
            WeatherInfoDTO weatherInfoDTO = null;

            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true"))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String json = response.body();
                    weatherInfoDTO = objectMapper.readValue(json, WeatherInfoDTO.class);
                } else {
                    System.out.println("GET request failed. Status code: " + response.statusCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return weatherInfoDTO;
        }

}

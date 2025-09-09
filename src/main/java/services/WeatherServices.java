package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.WeatherInfoDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherServices {


    public WeatherInfoDTO getWeatherInfo(String city) {
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherInfoDTO weatherInfoDTO = null;

        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://vejr.eu/api.php?location=" + city + "&degree=C"))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response.statusCode() == 200) {
                String json = response.body();
                weatherInfoDTO = objectMapper.readValue(json, WeatherInfoDTO.class);
                //System.out.println(response.body());

            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherInfoDTO;
    }

}

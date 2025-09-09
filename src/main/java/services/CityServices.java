package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.CityInfoDTO;
import dtos.WeatherInfoDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CityServices {


    public CityInfoDTO[] getCityInfo(String city) {
        ObjectMapper objectMapper = new ObjectMapper();
        CityInfoDTO[] cityInfoDTO = null;

        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://dawa.aws.dk/steder?hovedtype=Bebyggelse&undertype=by&prim%C3%A6rtnavn=" + city))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response.statusCode() == 200) {
                String json = response.body();
                cityInfoDTO = objectMapper.readValue(json, CityInfoDTO[].class);

            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityInfoDTO;
    }
}

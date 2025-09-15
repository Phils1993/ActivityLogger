package app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

// WeatherInfoDTO → represents the whole response.
//
//CurrentWeatherDTO → represents the nested "current_weather" object.
//
// This makes it easy to access weatherInfoDTO.getCurrentWeather().getTemperature()
// instead of digging manually into raw JSON.
public class CurrentWeatherDTO {
    private String time;
    private int interval;
    private double temperature;
    private double windspeed;
    private int winddirection;

    @JsonProperty("is_day")
    private int isDay;

    private int weathercode;
}

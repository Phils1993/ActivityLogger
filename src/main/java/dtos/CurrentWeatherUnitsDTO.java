package dtos;

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
public class CurrentWeatherUnitsDTO {
    private String time;
    private String interval;
    private String temperature;
    private String windspeed;
    private String winddirection;

    @JsonProperty("is_day")
    private String isDay;

    private String weathercode;
}

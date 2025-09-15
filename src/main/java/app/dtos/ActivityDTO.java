package app.dtos;

import app.enums.ExerciseType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ActivityDTO {
    private LocalDate executionDate;
    private ExerciseType exerciseType;
    private LocalTime timeOfDay;
    private int duration;
    private double distance;
    private String comment;

    private WeatherInfoDTO weatherInfoDTO;
    private CityInfoDTO cityInfoDTO;

}

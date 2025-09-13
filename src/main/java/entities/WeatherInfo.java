package entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weather_info")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder

public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String time;
    private double temperature;
    private double windspeed;
    private int winddirection;
    private int isDay;
    private int weathercode;

    // reverse mapping if you want it (optional)
    @OneToOne(mappedBy = "weatherInfo")
    private Activity activity;
}

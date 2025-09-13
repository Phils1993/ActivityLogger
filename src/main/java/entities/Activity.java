package entities;


import enums.ExerciseType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@ToString
@Table (name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "execution_Date")
    private LocalDate executionDate;
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;

    @Column(name = "time_of_day")
    private LocalTime timeOfDay;

    private int duration;
    private double distance;
    private String comment;

    // many acticities can happen in one City
    @ManyToOne
    @JoinColumn(referencedColumnName = "city_id")
    private CityInfo cityInfo;

    // one activity has one weather snapshot
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_id")
    private WeatherInfo weatherInfo;


    @PrePersist
    public void prePersist(){
        this.executionDate = LocalDate.now();
        this.timeOfDay = LocalTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        this.executionDate = LocalDate.now();
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
        if (weatherInfo != null) {
            weatherInfo.setActivity(this); // keep other side in sync
        }
    }



}

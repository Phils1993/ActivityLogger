package entities;


import enums.ExerciseType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@ToString
@Table (name = "activity")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CityInfo cityInfo;

    // one activity has one weather snapshot
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private WeatherInfo weatherInfo;


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

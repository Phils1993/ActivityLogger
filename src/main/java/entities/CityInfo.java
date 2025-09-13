package entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "city_info")
public class CityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private double latitude;
    private double longitude;
    private double elevation;
    private String country;
    private String admin1;
    private String admin2;

    // One city can have many activitie
    @OneToMany(mappedBy = "cityInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Activity> activities = new HashSet<>();


    public void addActivity(Activity activity) {
        activities.add(activity);
        if(activity != null) {
            activity.setCityInfo(this);
        }
    }

    public void removeActivity(Activity activity) {
        activities.remove(activity);
        activity.setCityInfo(null);
    }

}

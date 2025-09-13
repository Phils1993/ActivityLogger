package dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityInfoDTO {
    private int id;
    private String name;
    private double latitude;
    private double longitude;
    private double elevation;

    @JsonProperty("feature_code")
    private String featureCode;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("admin1_id")
    private long admin1Id;

    @JsonProperty("admin2_id")
    private long admin2Id;

    private String timezone;
    private long population;

    @JsonProperty("postcodes")
    private List<String> postcodes;

    @JsonProperty("country_id")
    private long countryId;

    private String country;
    private String admin1;
    private String admin2;
}

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
    private String id;
    @JsonProperty("primærtnavn")
    private String primaryName;
    @JsonProperty("visueltcenter")
    private List<Double> visueltCenter;
    @JsonProperty("kommuner")
    private List<KommuneDTO> kommuner;



}

package dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

// Without CityInfoResponseDTO, Jackson wouldn’t know how to map
// the "results" wrapper, and you’d only get raw JSON.
public class CityInfoResponseDTO {

    private List<CityInfoDTO> results;
    private double generationtime_ms;
}

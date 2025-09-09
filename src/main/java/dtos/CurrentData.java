package dtos;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CurrentData {
    private double temperature;
    private String skyText;
    private String humidity;
    private String windText;
}


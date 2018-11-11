package eus.unai.instancegen;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Service {

    private int startingTime;
    private int durationInMinutes;
    private int durationInKilometers;
    private int demand;

}

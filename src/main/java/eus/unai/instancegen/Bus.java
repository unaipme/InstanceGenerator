package eus.unai.instancegen;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Bus {

    private int capacity;
    private double eurosPerMinute;
    private double eurosPerKilometer;

}

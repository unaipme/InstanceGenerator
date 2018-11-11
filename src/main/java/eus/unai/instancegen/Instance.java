package eus.unai.instancegen;

import lombok.*;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Value
@Builder
public class Instance {

    private int nServices;
    private int nBuses;
    private int nDrivers;

    private int maxBuses;
    private int BM;
    private double CBM;
    private double CEM;

    private @Setter(AccessLevel.NONE) Service[] services;
    private @Setter(AccessLevel.NONE) Bus[] buses;
    private @Setter(AccessLevel.NONE) Driver[] drivers;

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.##");
        return "" +
                "/*********************************************\n" +
                " * OPL 12.8.0.0 Data\n" +
                " * Author: InstanceGenerator by unai.perez.mendizabal and ferran.torres.morales\n" +
                " * Creation Date: " + new Date().toString() + "\n" +
                " *********************************************/\n" +
                "\n" +
                " nServices = " + nServices + ";\n" +
                " nBuses = " + nBuses + ";\n" +
                " nDrivers = " + nDrivers + ";\n" +
                "\n" +
                " maxBuses = " + maxBuses + ";\n" +
                " BM = " + BM + ";\n" +
                " CBM = " + df.format(CBM) + ";\n" +
                " CEM = " + df.format(CEM) + ";\n" +
                "\n" +
                " st = [ " + Arrays.stream(this.services).map(s -> Integer.toString(s.getStartingTime())).collect(Collectors.joining(" ")) + " ];\n" +
                " sdt = [ " + Arrays.stream(this.services).map(s -> Integer.toString(s.getDurationInMinutes())).collect(Collectors.joining(" ")) + " ];\n" +
                " sdd = [ " + Arrays.stream(this.services).map(s -> Integer.toString(s.getDurationInKilometers())).collect(Collectors.joining(" ")) + " ];\n" +
                " dem = [ " + Arrays.stream(this.services).map(s -> Integer.toString(s.getDemand())).collect(Collectors.joining(" ")) + " ];\n" +
                "\n" +
                " cap = [ " + Arrays.stream(this.buses).map(b -> Integer.toString(b.getCapacity())).collect(Collectors.joining(" ")) + " ];\n" +
                " euros_min = [" + Arrays.stream(this.buses).map(b -> df.format(b.getEurosPerMinute())).collect(Collectors.joining(" ")) + " ];\n" +
                " euros_km = [" + Arrays.stream(this.buses).map(b -> df.format(b.getEurosPerKilometer())).collect(Collectors.joining(" ")) + " ];\n" +
                "\n" +
                " maxHours = [ " + Arrays.stream(this.drivers).map(d -> df.format(d.getMaxHours())).collect(Collectors.joining(" ")) + " ];\n";
    }

}

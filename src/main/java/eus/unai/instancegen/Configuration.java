package eus.unai.instancegen;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

import static java.lang.Math.round;
import static java.lang.Math.toIntExact;

@Setter(AccessLevel.PRIVATE)
@Getter
public class Configuration {

    private final static int DEFAULT_MIN_SERVICE_AMOUNT = 30;
    private final static int DEFAULT_MAX_SERVICE_AMOUNT = 100;

    private final static int DEFAULT_MIN_BUS_AMOUNT = 230;
    private final static int DEFAULT_MAX_BUS_AMOUNT = 450;

    private final static int DEFAULT_MIN_DRIVER_AMOUNT = 240;
    private final static int DEFAULT_MAX_DRIVER_AMOUNT = 500;

    private @Getter(AccessLevel.NONE) final static double DEFAULT_MIN_MAX_BUSES_PROPORTION = 0.65;
    private @Getter(AccessLevel.NONE) final static double DEFAULT_MAX_MAX_BUSES_PROPORTION = 0.9;

    private final static int DEFAULT_MIN_BASE_MINUTES = 100;
    private final static int DEFAULT_MAX_BASE_MINUTES = 300;

    private final static double DEFAULT_MIN_BASE_PAY = 8D;
    private final static double DEFAULT_MAX_BASE_PAY = 13D;

    private final static double DEFAULT_MIN_EXTRA_PAY = 13.5D;
    private final static double DEFAULT_MAX_EXTRA_PAY = 20D;

    private final static int DEFAULT_MIN_DEMAND = 30;
    private final static int DEFAULT_MAX_DEMAND = 90;

    private final static int DEFAULT_MIN_DURATION_KMS = 15;
    private final static int DEFAULT_MAX_DURATION_KMS = 30;

    private @Getter(AccessLevel.NONE) final static double DEFAULT_MIN_DURATION_MINS_PROPORTION = 3.0;
    private @Getter(AccessLevel.NONE) final static double DEFAULT_MAX_DURATION_MINS_PROPORTION = 5.5;

    private final static int DEFAULT_MIN_STARTING_TIME = 0; // Any minute starting from 7:00
    private final static int DEFAULT_MAX_STARTING_TIME = 945; // up until 22:45

    private final static int DEFAULT_MIN_CAPACITY = 35;
    private final static int DEFAULT_MAX_CAPACITY = 100;

    private final static double DEFAULT_MIN_EUROS_PER_MIN = 0.5;
    private final static double DEFAULT_MAX_EUROS_PER_MIN = 5.0;

    private final static double DEFAULT_MIN_EUROS_PER_KM = 1.0;
    private final static double DEFAULT_MAX_EUROS_PER_KM = 4.0;

    private final static int DEFAULT_MIN_MAX_HOURS = 6;
    private final static int DEFAULT_MAX_MAX_HOURS = 12;

    private int minServiceAmount;
    private int maxServiceAmount;

    private int minBusAmount;
    private int maxBusAmount;

    private int minDriverAmount;
    private int maxDriverAmount;

    private double minMaxBusesProportion;
    private double maxMaxBusesProportion;

    private Function<Integer, Integer> minMaxBuses = maxBuses -> toIntExact(round(minMaxBusesProportion * maxBuses));
    private Function<Integer, Integer> maxMaxBuses = maxBuses -> toIntExact(round(maxMaxBusesProportion * maxBuses));

    private int minBaseMinutes;
    private int maxBaseMinutes;

    private double minBasePay;
    private double maxBasePay;

    private double minExtraPay;
    private double maxExtraPay;

    private int minDemand;
    private int maxDemand;

    private int minDurationKms;
    private int maxDurationKms;

    private double minDurationMinsProportion;
    private double maxDurationMinsProportion;

    private Function<Integer, Integer> minDurationMins = durationInKms -> toIntExact(round(minDurationMinsProportion * durationInKms));
    private Function<Integer, Integer> maxDurationMins = durationInKms -> toIntExact(round(maxDurationMinsProportion * durationInKms));

    private int minStartingTime; // Any minute starting from 7:00
    private int maxStartingTime; // up until 22:45

    private int minCapacity;
    private int maxCapacity;

    private double minEurosPerMin;
    private double maxEurosPerMin;

    private double minEurosPerKm;
    private double maxEurosPerKm;

    private int minMaxHours;
    private int maxMaxHours;

    private Configuration() {}

    private static Configuration load(Properties props) {
        Configuration conf = new Configuration();
        conf.setMinServiceAmount(Optional.ofNullable(props.getProperty("minServiceAmount")).map(Integer::parseInt).orElse(DEFAULT_MIN_SERVICE_AMOUNT));
        conf.setMaxServiceAmount(Optional.ofNullable(props.getProperty("maxServiceAmount")).map(Integer::parseInt).orElse(DEFAULT_MAX_SERVICE_AMOUNT));
        conf.setMinBusAmount(Optional.ofNullable(props.getProperty("minBusAmount")).map(Integer::parseInt).orElse(DEFAULT_MIN_BUS_AMOUNT));
        conf.setMaxBusAmount(Optional.ofNullable(props.getProperty("maxBusAmount")).map(Integer::parseInt).orElse(DEFAULT_MAX_BUS_AMOUNT));
        conf.setMinDriverAmount(Optional.ofNullable(props.getProperty("minDriverAmount")).map(Integer::parseInt).orElse(DEFAULT_MIN_DRIVER_AMOUNT));
        conf.setMaxDriverAmount(Optional.ofNullable(props.getProperty("maxDriverAmount")).map(Integer::parseInt).orElse(DEFAULT_MAX_DRIVER_AMOUNT));
        conf.setMinMaxBusesProportion(Optional.ofNullable(props.getProperty("minMaxBusesProportion")).map(Double::parseDouble).orElse(DEFAULT_MIN_MAX_BUSES_PROPORTION));
        conf.setMaxMaxBusesProportion(Optional.ofNullable(props.getProperty("maxMaxBusesProportion")).map(Double::parseDouble).orElse(DEFAULT_MAX_MAX_BUSES_PROPORTION));
        conf.setMinBaseMinutes(Optional.ofNullable(props.getProperty("minBaseMinutes")).map(Integer::parseInt).orElse(DEFAULT_MIN_BASE_MINUTES));
        conf.setMaxBaseMinutes(Optional.ofNullable(props.getProperty("maxBaseMinutes")).map(Integer::parseInt).orElse(DEFAULT_MAX_BASE_MINUTES));
        conf.setMinBasePay(Optional.ofNullable(props.getProperty("minBasePay")).map(Double::parseDouble).orElse(DEFAULT_MIN_BASE_PAY));
        conf.setMaxBasePay(Optional.ofNullable(props.getProperty("maxBasePay")).map(Double::parseDouble).orElse(DEFAULT_MAX_BASE_PAY));
        conf.setMinExtraPay(Optional.ofNullable(props.getProperty("minExtraPay")).map(Double::parseDouble).orElse(DEFAULT_MIN_EXTRA_PAY));
        conf.setMaxExtraPay(Optional.ofNullable(props.getProperty("maxExtraPay")).map(Double::parseDouble).orElse(DEFAULT_MAX_EXTRA_PAY));
        conf.setMinDemand(Optional.ofNullable(props.getProperty("minDemand")).map(Integer::parseInt).orElse(DEFAULT_MIN_DEMAND));
        conf.setMaxDemand(Optional.ofNullable(props.getProperty("maxDemand")).map(Integer::parseInt).orElse(DEFAULT_MAX_DEMAND));
        conf.setMinDurationKms(Optional.ofNullable(props.getProperty("minDurationKms")).map(Integer::parseInt).orElse(DEFAULT_MIN_DURATION_KMS));
        conf.setMaxDurationKms(Optional.ofNullable(props.getProperty("maxDurationKms")).map(Integer::parseInt).orElse(DEFAULT_MAX_DURATION_KMS));
        conf.setMinDurationMinsProportion(Optional.ofNullable(props.getProperty("minDurationMinsProportion")).map(Double::parseDouble).orElse(DEFAULT_MIN_DURATION_MINS_PROPORTION));
        conf.setMaxDurationMinsProportion(Optional.ofNullable(props.getProperty("maxDurationMinsProportion")).map(Double::parseDouble).orElse(DEFAULT_MAX_DURATION_MINS_PROPORTION));
        conf.setMinStartingTime(Optional.ofNullable(props.getProperty("minStartingTime")).map(Integer::parseInt).orElse(DEFAULT_MIN_STARTING_TIME));
        conf.setMaxStartingTime(Optional.ofNullable(props.getProperty("maxStartingTime")).map(Integer::parseInt).orElse(DEFAULT_MAX_STARTING_TIME));
        conf.setMinCapacity(Optional.ofNullable(props.getProperty("minCapacity")).map(Integer::parseInt).orElse(DEFAULT_MIN_CAPACITY));
        conf.setMaxCapacity(Optional.ofNullable(props.getProperty("maxCapacity")).map(Integer::parseInt).orElse(DEFAULT_MAX_CAPACITY));
        conf.setMinEurosPerMin(Optional.ofNullable(props.getProperty("minEurosPerMin")).map(Double::parseDouble).orElse(DEFAULT_MIN_EUROS_PER_MIN));
        conf.setMaxEurosPerMin(Optional.ofNullable(props.getProperty("maxEurosPerMin")).map(Double::parseDouble).orElse(DEFAULT_MAX_EUROS_PER_MIN));
        conf.setMinEurosPerKm(Optional.ofNullable(props.getProperty("minEurosPerKm")).map(Double::parseDouble).orElse(DEFAULT_MIN_EUROS_PER_KM));
        conf.setMaxEurosPerKm(Optional.ofNullable(props.getProperty("maxEurosPerKm")).map(Double::parseDouble).orElse(DEFAULT_MAX_EUROS_PER_KM));
        conf.setMinMaxHours(Optional.ofNullable(props.getProperty("minMaxHours")).map(Integer::parseInt).orElse(DEFAULT_MIN_MAX_HOURS));
        conf.setMaxMaxHours(Optional.ofNullable(props.getProperty("maxMaxHours")).map(Integer::parseInt).orElse(DEFAULT_MAX_MAX_HOURS));
        return conf;
    }

    public static Configuration load(String filename) {
        try {
            Properties props = new Properties();
            props.load(Files.newInputStream(Paths.get(filename)));
            return load(props);
        } catch (IOException | NullPointerException e) {
            if (filename != null) {
                System.err.println("The file could not be found or read");
            }
            e.printStackTrace();
            return load(new Properties());
        }

    }

}

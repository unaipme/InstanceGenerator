package eus.unai.instancegen;

import org.apache.commons.cli.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Random;

public class InstanceGenerator {

    private final static int DEFAULT_INSTANCE_AMOUNT = 30;

    private static Options options() {
        Options options = new Options();
        options.addOption(Option.builder("a")
                .longOpt("amount")
                .desc("Amount of instances to generate")
                .hasArg()
                .build());
        options.addOption(Option.builder("f")
                .longOpt("file")
                .desc("Configuration file")
                .hasArg()
                .build());
        options.addOption(Option.builder("S")
                .longOpt("services")
                .argName("Amount of services")
                .hasArg()
                .build());
        options.addOption(Option.builder("B")
                .longOpt("buses")
                .desc("Amount of buses")
                .hasArg()
                .build());
        options.addOption(Option.builder("D")
                .longOpt("drivers")
                .desc("Amount of drivers")
                .hasArg()
                .build());
        options.addOption(Option.builder("x")
                .longOpt("max-buses")
                .desc("Maximum amount of buses that can be used")
                .hasArg()
                .build());
        options.addOption(Option.builder("b")
                .longOpt("base-minutes")
                .desc("Base minutes threshold, i.e., amount of minutes from which the pay increases")
                .hasArg()
                .build());
        options.addOption(Option.builder("c")
                .longOpt("base-pay")
                .desc("Amount payed to the drivers for each base minute worked")
                .hasArg()
                .build());
        options.addOption(Option.builder("e")
                .longOpt("extra-pay")
                .desc("Amount payed to the drivers for each extra minute worked")
                .hasArg()
                .build());
        options.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Prints help")
                .build());
        return options;
    }

    public static void main(String [] args) {
        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options(), args);

            if (cmd.hasOption("h")) {
                HelpFormatter help = new HelpFormatter();
                help.printHelp("InstanceGenerator", options());
            }

            Configuration conf = Configuration.load(Optional.ofNullable(cmd.getOptionValue("f")).orElse(null));

            Random random = new Random();

            int amount = Optional.ofNullable(cmd.getOptionValue("a"))
                    .map(Integer::parseInt)
                    .orElse(DEFAULT_INSTANCE_AMOUNT);
            for (int i = 0; i < amount; i++) {
                int nServices = Optional.ofNullable(cmd.getOptionValue("S"))
                        .map(Integer::parseInt)
                        .orElse(random.nextInt(conf.getMaxServiceAmount() - conf.getMinServiceAmount()) + conf.getMinServiceAmount());

                Service [] services = new Service[nServices];
                for (int s = 0; s < nServices; s++) {
                    int demand = random.nextInt(conf.getMaxDemand() - conf.getMinDemand()) + conf.getMinDemand();
                    int durationInKilometers = random.nextInt(conf.getMaxDurationKms() - conf.getMinDurationKms()) + conf.getMinDurationKms();
                    int durationInMinutes = random.nextInt(conf.getMaxDurationMins().apply(durationInKilometers)
                            - conf.getMinDurationMins().apply(durationInKilometers)) + conf.getMinDurationMins().apply(durationInKilometers);
                    int startingTime = random.nextInt(conf.getMaxStartingTime() - conf.getMinStartingTime()) + conf.getMinStartingTime();
                    services[s] = Service.builder()
                            .demand(demand)
                            .durationInKilometers(durationInKilometers)
                            .durationInMinutes(durationInMinutes)
                            .startingTime(startingTime)
                            .build();
                }

                int nBuses = Optional.ofNullable(cmd.getOptionValue("B"))
                        .map(Integer::parseInt)
                        .orElse(random.nextInt(conf.getMaxBusAmount() - conf.getMinBusAmount()) + conf.getMinBusAmount());

                Bus [] buses = new Bus[nBuses];
                for (int b = 0; b < nBuses; b++) {
                    int capacity = random.nextInt(conf.getMaxCapacity() - conf.getMinCapacity()) + conf.getMinCapacity();
                    double eurosPerKilometer = conf.getMinEurosPerKm() + (conf.getMaxEurosPerKm() - conf.getMinEurosPerKm()) * random.nextDouble();
                    double eurosPerMinutes = conf.getMinEurosPerMin() + (conf.getMaxEurosPerMin() - conf.getMinEurosPerMin()) * random.nextDouble();
                    buses[b] = Bus.builder()
                            .capacity(capacity)
                            .eurosPerKilometer(eurosPerKilometer)
                            .eurosPerMinute(eurosPerMinutes)
                            .build();
                }

                int nDrivers = Optional.ofNullable(cmd.getOptionValue("D"))
                        .map(Integer::parseInt)
                        .orElse(random.nextInt(conf.getMaxDriverAmount() - conf.getMinDriverAmount()) + conf.getMinDriverAmount());

                Driver [] drivers = new Driver[nDrivers];
                for (int d = 0; d < nDrivers; d++) {
                    int maxHours = random.nextInt(conf.getMaxMaxHours() - conf.getMinMaxHours()) + conf.getMinMaxHours();
                    drivers[d] = Driver.builder()
                            .maxHours(maxHours)
                            .build();
                }

                int maxBuses = Optional.ofNullable(cmd.getOptionValue("x"))
                        .map(Integer::parseInt)
                        .orElse(random.nextInt(conf.getMaxMaxBuses().apply(nBuses) - conf.getMinMaxBuses().apply(nBuses)) + conf.getMinMaxBuses().apply(nBuses));
                int BM = Optional.ofNullable(cmd.getOptionValue("b"))
                        .map(Integer::parseInt)
                        .orElse(random.nextInt(conf.getMaxBaseMinutes() - conf.getMinBaseMinutes()) + conf.getMinBaseMinutes());
                double CBM = Optional.ofNullable(cmd.getOptionValue("c"))
                        .map(Double::parseDouble)
                        .orElse(conf.getMaxBasePay() + (conf.getMaxBasePay() - conf.getMinBasePay()) * random.nextDouble());
                double CEM = Optional.ofNullable(cmd.getOptionValue("e"))
                        .map(Double::parseDouble)
                        .orElse(conf.getMaxExtraPay() + (conf.getMaxExtraPay() - conf.getMinExtraPay()) * random.nextDouble());
                Instance instance = Instance.builder()
                        .nServices(nServices)
                        .nBuses(nBuses)
                        .nDrivers(nDrivers)
                        .maxBuses(maxBuses)
                        .BM(BM)
                        .CBM(CBM)
                        .CEM(CEM)
                        .services(services)
                        .buses(buses)
                        .drivers(drivers)
                        .build();

                BufferedWriter writer = Files.newBufferedWriter(Paths.get("data" + i + ".dat"));
                writer.write(instance.toString());
                writer.close();
            }
        } catch (ParseException e) {
            System.err.println("An error happened parsing command line arguments");
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            System.err.println("An error happened when trying to write the instance into a file");
            e.printStackTrace();
            System.exit(2);
        }
    }

}

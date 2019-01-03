# Instance generator

Instance generator created for the AMMM project (UPC) by Unai Perez Mendizabal (unai.perez.mendizabal) and Ferran Torres Morales (ferran.torres.morales).

## Requirements

To build and run the generator, Java 8 and Maven are required. They can be installed in Ubuntu by running the following command:

```bash
sudo apt install -y openjdk-8-jdk maven
```

## Compiling and running

The generator can be compiled into a JAR with Maven using the following command:

```bash
mvn clean package
```

The generator can then be run with default configuration just running it like a regular JAR file:

```bash
java -jar target/instance-generator.jar
```

The generator understands a set of parameters that allow modifying its behaviour. The parameters that can be used are listed if the JAR is run using the `-h` or `--help` argument.

```bash
java -jar target/instance-generator.jar -h
```

This can also be done, in a more extensive way, with the use of a `properties` file. This file allows to define all the parameters that are taken into account to generate the instances. The possible properties are shown below. All are represented with integers unless otherwise noted.

```properties
minServiceAmount=
maxServiceAmount=
minBusAmount=
maxBusAmount=
minDriverAmount=
maxDriverAmount=
minMaxBusesProportion= #double
maxMaxBusesProportion= #double
minBaseMinutes=
maxBaseMinutes=
minBasePay= #double
maxBasePay= #double
minExtraPay= #double
maxExtraPay= #double
minDemand=
maxDemand=
minDurationKms=
maxDurationKms=
minDurationMinsProportion= #double
maxDurationMinsProportion= #double
minStartingTime=
maxStartingTime=
minCapacity=
maxCapacity=
minEurosPerMin= #double
maxEurosPerMin= #double
minEurosPerKm= #double
maxEurosPerKm= #double
minMaxHours=
maxMaxHours=
```

This file can the be used directly with the flag `-f` as in the example command below:
```bash
java -jar target/instance-generator.jar -f conf.properties
```

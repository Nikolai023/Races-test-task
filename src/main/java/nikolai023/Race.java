package nikolai023;

import nikolai023.entities.Vehicle;
import nikolai023.parser.XMLParser;
import nikolai023.stringUtils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Race {
    private final Logger logger = LogManager.getLogger(Race.class);
    private double trackLength;
    private List<Vehicle> vehicles;

    public Race(double trackLength, List<Vehicle> vehicles) {
        this.trackLength = trackLength;
        this.vehicles = vehicles;
        for (Vehicle vehicle : vehicles) {
            vehicle.setTrackLength(this.trackLength);
        }
    }

    public void startRace() throws InterruptedException, ExecutionException {
        logger.info("List of drivers:\n");
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<RaceResult>> results = executorService.invokeAll(vehicles);
        executorService.shutdown();

        List<RaceResult> raceResults = new ArrayList<>();
        for (Future<RaceResult> result : results) {
            raceResults.add(result.get());
        }

        raceResults.sort(Comparator.comparing(RaceResult::getTime));
        logger.info(getResultTable(raceResults));
    }

    private String getResultTable(List<RaceResult> results) {
        int placeColumnLength = "Place".length();
        int driverColumnLength = "Driver".length();
        int vehicleColumnLength = "Vehicle".length();
        int timeColumnLength = 9;
        for (RaceResult result : results) {
            int driverNameLength = result.getDriverName().length();
            int vehicleNameLength = result.getVehicleName().length();
            if (driverNameLength > driverColumnLength) {
                driverColumnLength = driverNameLength;
            }
            if (vehicleNameLength > vehicleColumnLength) {
                vehicleColumnLength = vehicleNameLength;
            }
        }
        int tableWidth = placeColumnLength + driverColumnLength + vehicleColumnLength + timeColumnLength + 4;
        String separatorLine = "+" + StringUtils.getRightPaddedString(tableWidth, "+")
                .replace(" ", "-") + "\n";

        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder
                .append(separatorLine)
                .append("|")
                .append(StringUtils.getLeftPaddedString(placeColumnLength, "Place"))
                .append("|")
                .append(StringUtils.getLeftPaddedString(driverColumnLength, "Driver"))
                .append("|")
                .append(StringUtils.getLeftPaddedString(vehicleColumnLength, "Vehicle"))
                .append("|")
                .append(StringUtils.getLeftPaddedString(timeColumnLength, "Time"))
                .append("|")
                .append("\n")
                .append(separatorLine);
        for (int i = 0; i < results.size(); i++) {
            tableBuilder
                    .append("|")
                    .append(StringUtils.getLeftPaddedString(placeColumnLength, Integer.toString(i + 1)))
                    .append("|")
                    .append(StringUtils.getLeftPaddedString(driverColumnLength, results.get(i).getDriverName()))
                    .append("|")
                    .append(StringUtils.getLeftPaddedString(vehicleColumnLength, results.get(i).getVehicleName()))
                    .append("|")
                    .append(StringUtils.getLeftPaddedString(timeColumnLength, results.get(i).getTime()))
                    .append("|")
                    .append("\n");
        }
        tableBuilder
                .append(separatorLine);
        return tableBuilder.toString();
    }

    public boolean checkIfRestart() {
        logger.info("Do you want to restart the race? Y/N");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            if (reader.readLine().equals("Y")) {
                return true;
            }
        } catch (IOException e) {
            logger.error("Input exception!", e);
        }
        return false;
    }

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        try {

            logger.info("Loading properties...");
            String schemaPath = ClassLoader.getSystemClassLoader().getResource("RaceProperties.xsd").getPath();
            String xmlPath = ClassLoader.getSystemClassLoader().getResource("Vehicles.xml").getPath();
            logger.info("Done.\n");

            logger.info("Parsing XML...");
            RaceProperties properties = XMLParser.parseXML(xmlPath, schemaPath);
            logger.info("Done.\n");

            double trackLength = properties.getTrackLength();
            List<Vehicle> vehicles = properties.getVehicles();
            Race race = new Race(trackLength, vehicles);

            logger.info("Starting the race...\n");
            do {
                race.startRace();
            } while (race.checkIfRestart());

        } catch (JAXBException | SAXException e) {
            logger.error("Error: parse error!", e);
        } catch (InterruptedException e) {
            logger.error("Error: interrupted exception!", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("Error: execution exception!", e);
        }
    }
}

package nikolai023.entities;

import nikolai023.RaceResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


@XmlRootElement
public abstract class Vehicle implements Callable<RaceResult> {
    private static final Logger logger = LogManager.getLogger(Vehicle.class);
    private String driverName;
    private int wheelChangeTime;
    private double speed;
    private double piercingChance;
    private double trackLength;

    public RaceResult call() throws InterruptedException {
        logger.info(getInfoMessage());
        double distance = 0;
        long startTime = System.currentTimeMillis();
        Random random = new Random();
        while (distance < trackLength) {
            if (random.nextDouble() < piercingChance) {
                logger.info(getPierceMessage());
                TimeUnit.MILLISECONDS.sleep(wheelChangeTime);
            }

            double diff;
            if (speed > (diff = trackLength - distance)) {
                distance += diff;
                TimeUnit.MILLISECONDS.sleep((long) (diff * 1000 / speed));
            } else {
                distance += speed;
                TimeUnit.MILLISECONDS.sleep(1000);
                Thread.sleep(1000);
            }
            logger.info(getDistanceMessage(distance));
        }
        return new RaceResult(this.driverName, getClass().getSimpleName(), new SimpleDateFormat("mm:ss.SSS").format(new Date(System.currentTimeMillis() - startTime)));
    }

    private String getInfoMessage() {
        return getCommonInfo() + getIndividualInfo();
    }

    private String getCommonInfo() {
        return "Racer name: " + this.driverName + "\n" +
                "Vehicle: " + getClass().getSimpleName() + "\n" +
                "Speed: " + this.speed + "\n" +
                "Wheel pierce chance: " + this.piercingChance + "\n";
    }

    protected abstract String getIndividualInfo();

    private String getPierceMessage() {
        return this.driverName + " on " + getClass().getSimpleName().toLowerCase() + " pierced the wheel";
    }

    private String getDistanceMessage(double distance) {
        return this.driverName + " on " + getClass().getSimpleName().toLowerCase() + " drove distance is " + new DecimalFormat(".00").format(distance);
    }

    @XmlElement
    public void setWheelChangeTime(int wheelChangeTime) {
        this.wheelChangeTime = wheelChangeTime;
    }

    @XmlElement
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @XmlElement
    public void setPiercingChance(double piercingChance) {
        this.piercingChance = piercingChance;
    }

    public void setTrackLength(double trackLength) {
        this.trackLength = trackLength;
    }

    @XmlElement
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}

package nikolai023;

import nikolai023.entities.Car;
import nikolai023.entities.Motorcycle;
import nikolai023.entities.Truck;
import nikolai023.entities.Vehicle;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class RaceProperties {
    private double trackLength;
    private List<Vehicle> vehicles;

    public double getTrackLength() {
        return trackLength;
    }

    @XmlElement
    public void setTrackLength(double trackLength) {
        this.trackLength = trackLength;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    @XmlElementWrapper(name = "vehicles")
    @XmlElements({
            @XmlElement(name = "car", type = Car.class),
            @XmlElement(name = "motorcycle", type = Motorcycle.class),
            @XmlElement(name = "truck", type = Truck.class),
    })
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}

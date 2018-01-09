package nikolai023.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Truck extends Vehicle {
    private double cargoWeight;

    public double getCargoWeight() {
        return cargoWeight;
    }

    @XmlElement
    public void setCargoWeight(double cargoWeight) {
        this.cargoWeight = cargoWeight;
    }

    @Override
    protected String getIndividualInfo() {
        return "Cargo weight: " + this.cargoWeight + "\n";
    }
}

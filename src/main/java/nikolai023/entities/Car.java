package nikolai023.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Car extends Vehicle {
    private int peopleCount;

    @XmlElement
    public int getPeopleCount() {
        return peopleCount;
    }

    @Override
    protected String getIndividualInfo() {
        return "People count: " + this.peopleCount + "\n";
    }
}

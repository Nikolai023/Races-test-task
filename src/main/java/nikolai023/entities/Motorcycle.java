package nikolai023.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Motorcycle extends Vehicle {
    private boolean hasSidecar;

    public boolean isHasSidecar() {
        return hasSidecar;
    }

    @XmlElement(required = true)
    public void setHasSidecar(boolean hasSidecar) {
        this.hasSidecar = hasSidecar;
    }

    @Override
    protected String getIndividualInfo() {
        return "Sidecar: " + (this.hasSidecar ? "yes" : "no") + "\n";
    }
}

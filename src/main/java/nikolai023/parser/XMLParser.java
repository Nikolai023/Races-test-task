package nikolai023.parser;

import nikolai023.RaceProperties;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;
import java.io.File;

public class XMLParser {
    private XMLParser() {
    }

    public static RaceProperties parseXML(String xmlPath, String schemaPath) throws JAXBException, SAXException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RaceProperties.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        jaxbUnmarshaller.setSchema(schemaFactory.newSchema(new File(schemaPath)));
        return  (RaceProperties) jaxbUnmarshaller.unmarshal(new File(xmlPath));
    }
}

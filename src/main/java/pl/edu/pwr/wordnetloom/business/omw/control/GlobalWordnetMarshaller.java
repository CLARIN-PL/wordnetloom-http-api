package pl.edu.pwr.wordnetloom.business.omw.control;

import pl.edu.pwr.wordnetloom.business.omw.entity.LexicalResource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class GlobalWordnetMarshaller {

    private void buildResource(LexicalResource resource, String filename) {

        FileOutputStream file;

        try {

            file = new FileOutputStream(filename);
            PrintStream printer = new PrintStream(file);
            printer.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            printer.print("<!DOCTYPE LexicalResource SYSTEM \"http://globalwordnet.github.io/schemas/WN-LMF-1.0.dtd\">");

            Marshaller marshaller = prepareMarshaller(LexicalResource.class);
            marshaller.marshal(resource, file);

        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
    }

    private Marshaller prepareMarshaller(Class<?> clazz)
            throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        return jaxbMarshaller;
    }
}

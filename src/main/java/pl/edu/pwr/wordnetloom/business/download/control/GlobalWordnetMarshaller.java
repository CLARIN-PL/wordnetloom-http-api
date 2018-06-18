package pl.edu.pwr.wordnetloom.business.download.control;

import pl.edu.pwr.wordnetloom.business.download.entity.OmwLexicalResource;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalWordnetMarshaller {

    @Inject
    Logger log;

    public void buildResource(OmwLexicalResource resource, String filename) {

        FileOutputStream file = null;

        try {

            file = new FileOutputStream(filename);
            PrintStream printer = new PrintStream(file);
            printer.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
            printer.print("<!DOCTYPE OmwLexicalResource SYSTEM \"http://globalwordnet.github.io/schemas/WN-LMF-1.0.dtd\">");

            Marshaller marshaller = prepareMarshaller(OmwLexicalResource.class);
            marshaller.marshal(resource, file);

        } catch (FileNotFoundException | JAXBException e) {
            log.log(Level.SEVERE, "Error while building lexical resource", e);
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                log.log(Level.SEVERE, "Error while closing lexical resource", e);
            }
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

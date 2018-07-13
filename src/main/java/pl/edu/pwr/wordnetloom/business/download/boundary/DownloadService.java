package pl.edu.pwr.wordnetloom.business.download.boundary;

import pl.edu.pwr.wordnetloom.business.download.control.EnWordnetBuilder;
import pl.edu.pwr.wordnetloom.business.download.control.GlobalWordnetMarshaller;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.File;

@Stateless
public class DownloadService {

    @Inject
    GlobalWordnetMarshaller gwMarshaller;

    @Inject
    EnWordnetBuilder enWordnetBuilder;

    public File buildEnWordnetFile() {
        String filename = "/tmp/EnWordnet.xml";
        gwMarshaller.buildResource(enWordnetBuilder.buildLexicalResource(3L), filename);
        return new File(filename);
    }
}

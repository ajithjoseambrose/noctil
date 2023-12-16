package com.ajith.batch;

import com.ajith.batch.model.MainArtist;
import com.ajith.batch.model.OutputJson;
import com.ajith.batch.model.Recording;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

public class XmlToJsonProcessor implements ItemProcessor<Recording, OutputJson.RecordingOutput> {

    private final ObjectMapper objectMapper;

    public XmlToJsonProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public static String convertObjectToXml(Object object) {
        try {
            // Create a JAXB context for the class of the object
            JAXBContext context = JAXBContext.newInstance(object.getClass());

            // Create a marshaller
            Marshaller marshaller = context.createMarshaller();

            // Convert the object to XML
            StringWriter writer = new StringWriter();
            marshaller.marshal(object, writer);

            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OutputJson.RecordingOutput process(Recording recording) throws Exception {
        System.out.println("Processing Recording: " + recording.getTitle());
        System.out.println("Artist: " + recording.getMainArtists().get(0).getArtist());
        System.out.println("Raw XML Content: " + convertObjectToXml(recording));

        String artistValue = "N/A"; // Default value in case null

        if (recording != null && recording.getMainArtists() != null) {
            artistValue = recording.getMainArtists().get(0).getArtist();
        }

        System.out.println("Processing Artist: " + artistValue);

        OutputJson.RecordingOutput recordingOutput = new OutputJson.RecordingOutput();
        recordingOutput.setTitle(recording.getTitle());

        OutputJson.MainArtistOutput mainArtistOutput = new OutputJson.MainArtistOutput();
        mainArtistOutput.setArtist(artistValue.toUpperCase());

        recordingOutput.setMainArtists(List.of(mainArtistOutput));
        return recordingOutput;
    }
}
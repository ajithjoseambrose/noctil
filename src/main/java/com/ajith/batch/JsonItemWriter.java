package com.ajith.batch;

import com.ajith.batch.model.OutputJson;
import com.ajith.batch.model.Recording;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.json.JsonObjectMarshaller;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.List;

public class JsonItemWriter implements ItemWriter<OutputJson.RecordingOutput> {

    private final FlatFileItemWriter<String> writer;
    private final Resource outputResource;
    private final ObjectMapper objectMapper;
    private OutputJson outputJson;  // Store the OutputJson object

    public JsonItemWriter(Resource outputResource, ObjectMapper objectMapper) {
        this.outputResource = outputResource;
        this.objectMapper = objectMapper;
        this.outputJson = new OutputJson();  // Initialize the OutputJson object
        this.writer = new FlatFileItemWriterBuilder<String>()
                .name("jsonItemWriter")
                .resource(outputResource)
                .lineAggregator(createLineAggregator())
                .build();
    }

    @Override
    public void write(List<? extends OutputJson.RecordingOutput> items) throws Exception {
        // Add items to the Recordings array
        outputJson.getRecordings().addAll(items);
        // Serialize the updated OutputJson object to JSON string
        String jsonString = convertToJsonString(outputJson);

        // create an ExecutionContext
        ExecutionContext executionContext = new ExecutionContext();
        // Open, write, and close the writer using the ExecutionContext
        writer.open(executionContext);
        writer.write(List.of(jsonString));
        writer.close();
    }

    private LineAggregator<String> createLineAggregator() {
        // Use a lambda function to return the serialized JSON string
        return item -> item;
    }

    private String convertToJsonString(OutputJson outputJson) {
        try {
            return objectMapper.writeValueAsString(outputJson);
        } catch (Exception e) {
            throw new RuntimeException("Error converting object to JSON", e);
        }
    }
}
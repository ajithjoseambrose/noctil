package com.ajith.batch;

import com.ajith.batch.model.OutputJson;
import com.ajith.batch.model.Recording;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public ItemReader<Recording> xmlItemReader() {
        return new StaxEventItemReaderBuilder<Recording>()
                .name("xmlItemReader")
                .resource(inputResource())
                .addFragmentRootElements("RECORDING")
                .unmarshaller(recordingsUnmarshaller())
                .build();
    }

    @Bean
    public ItemWriter<OutputJson.RecordingOutput> jsonItemWriter(ObjectMapper objectMapper) {
        return new JsonItemWriter(outputResource(), objectMapper);
    }

    @Bean
    public Step myStep(ItemReader<Recording> xmlItemReader, ItemWriter<OutputJson.RecordingOutput> jsonItemWriter, ObjectMapper objectMapper) {
        return stepBuilderFactory.get("myStep")
                .<Recording, OutputJson.RecordingOutput>chunk(10)
                .reader(xmlItemReader)
                .processor(new XmlToJsonProcessor(objectMapper))
                .writer(jsonItemWriter)

                .build();
    }

    @Bean
    public Job myJob(Step myStep) {
        return jobBuilderFactory.get("myJob")
                .start(myStep)
                .build();
    }

    @Bean
    public Jaxb2Marshaller recordingsUnmarshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Recording.class);
        return marshaller;
    }

    @Bean
    public JacksonJsonObjectMarshaller<OutputJson.RecordingOutput> jsonObjectMarshaller() {
        return new JacksonJsonObjectMarshaller<>();
    }

    @Bean
    public FileSystemResource inputResource() {
        return new FileSystemResource("src/main/resources/input.xml");
    }

    @Bean
    public FileSystemResource outputResource() {
        return new FileSystemResource("src/main/resources/output.json");
    }
}
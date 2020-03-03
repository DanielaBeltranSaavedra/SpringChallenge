package com.MicroServer2.MicroServer2;

import com.MicroServer2.MicroServer2.dto.Anime;
import com.MicroServer2.MicroServer2.dto.AnimeOutput;
import com.MicroServer2.MicroServer2.processor.Processor;
import com.MicroServer2.MicroServer2.rest.RestAnimeReader;

import com.MicroServer2.MicroServer2.rest.RestReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class MicroServer2ServiceConfiguration extends DefaultBatchConfigurer {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private RestTemplate restTemplate;
    private Resource outputResource = new FileSystemResource("output/outputData.csv");
    public List<AnimeOutput> listAnimeAll = new ArrayList<AnimeOutput>();


    @Override
    public void setDataSource(DataSource dataSource) {
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ItemReader<Anime> reader(RestTemplate restTemplate) {
        return new RestAnimeReader(
                "http://localhost:8081/anime/all",
                restTemplate

        );
    }

    @Bean
    public RestReader reader2(RestTemplate restTemplate) {
        return new RestReader(
                "http://localhost:8081/anime/top?id=10&&genre='Drama'",
                restTemplate

        );
    }


    @Bean
    public ItemProcessor<Anime, AnimeOutput> processor() {
        return new Processor();
    }

    @Bean
    public FlatFileItemWriter<AnimeOutput> writer() {
        FlatFileItemWriter<AnimeOutput> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<AnimeOutput>() {
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<AnimeOutput>() {
                    {
                        setNames(new String[]{"anime_id", "name", "rating", "type", "source"});
                    }
                });
            }
        });
        return writer;
    }
    @Bean
    public FlatFileItemWriter<Integer> itemWriter() {
        FlatFileItemWriter<Integer> itemWriter = new FlatFileItemWriter<Integer>() {

            public String doWrite(List<? extends Integer> items) {
                String itemsAsString = super.doWrite(items);
                return "page header" + lineSeparator +
                        itemsAsString +
                        "page footer" + lineSeparator;
            }
        };
        itemWriter.setName("itemWriter");
        itemWriter.setResource(new FileSystemResource("output/topGenre.csv"));
        itemWriter.setLineAggregator(new PassThroughLineAggregator<>());

        return itemWriter;
    }
    @Bean
    @StepScope
    FlatFileItemReader<AnimeOutput> flatFileItemReader() {
        FlatFileItemReader<AnimeOutput> r = new FlatFileItemReader<>();
        r.setResource(new FileSystemResource((File) outputResource));
        r.setLineMapper(new DefaultLineMapper<AnimeOutput>() {
            {
                this.setLineTokenizer(new DelimitedLineTokenizer(",") {
                    {
                        this.setNames(new String[]{"anime_id", "name", "rating", "type", "source"});
                    }
                });
                this.setFieldSetMapper(new BeanWrapperFieldSetMapper<AnimeOutput>() {
                    {
                        this.setTargetType(AnimeOutput.class);
                    }
                });
            }
        });
        return r;
    }
    @Bean
    public Job getAllFromRestApi(@Qualifier("step1") Step step1) {
        return jobBuilderFactory.get("getAllFromRestApi")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(FlatFileItemWriter<AnimeOutput> writer) {
        return stepBuilderFactory.get("step1")
                .<Anime, AnimeOutput>chunk(10)
                .reader(reader(restTemplate))
                .writer(writer)
                .build();

    }
    @Bean
    public Job getAllFromRestApi2(@Qualifier("step2") Step step2) {
        return jobBuilderFactory.get("getAllFromRestApi2")
                .incrementer(new RunIdIncrementer())
                .flow(step2)
                .end()
                .build();
    }

    @Bean
    public Step step2(FlatFileItemWriter<Integer> itemWriter) {
        return stepBuilderFactory.get("step2")
                .<Integer, Integer> chunk(10)
                .reader(reader2(restTemplate))
                .writer(itemWriter)
                .build();


    }




}



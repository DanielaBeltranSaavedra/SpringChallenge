package com.MicroServer2.MicroServer2;

import com.MicroServer2.MicroServer2.dto.Anime;
import com.MicroServer2.MicroServer2.dto.AnimeOutput;
import com.MicroServer2.MicroServer2.rest.RestAnimeReader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

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
    @Override
    public void setDataSource(DataSource dataSource) {
    }
    @Bean
    public RestTemplate restTemplate (){
        return  new RestTemplate();
    }
    @Bean
    public ItemReader<Anime> reader(RestTemplate restTemplate){
        return new RestAnimeReader(
                "http://localhost:8081/anime/all",
                restTemplate

        );
    }
    @Bean
    public RestAnimeReader readerTopXgenre(RestTemplate restTemplate){
        return new RestAnimeReader(
                "http://localhost:8081/anime/top",
                restTemplate

        );
    }



    @Bean
    public ItemProcessor<Anime, AnimeOutput> processor(){
        return new processor();
    }
    @Bean
    public FlatFileItemWriter<AnimeOutput> writer(){
        FlatFileItemWriter<AnimeOutput> writer = new FlatFileItemWriter<>();
        writer.setResource(outputResource);
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<AnimeOutput>(){
            {
                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<AnimeOutput>(){
                    {
                        setNames(new String[] {"anime_id", "name", "rating", "type", "source"});
                    }
                });
            }
        });
        return writer;
    }
    @Bean
    public Job getAllFromRestApi(@Qualifier("step1") Step step1){
        return jobBuilderFactory.get("getAllFromRestApi")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
    @Bean
    public Step step1(FlatFileItemWriter<AnimeOutput> writer){

        return stepBuilderFactory.get("step1")
                .<Anime, AnimeOutput> chunk(10)
                .reader(reader(restTemplate))
                .writer(writer)
                .build();
    }
    @Bean
    public Step step2(FlatFileItemWriter<AnimeOutput> writer){

        return stepBuilderFactory.get("step2")
                .<Anime, AnimeOutput> chunk(10)
                .reader(readerTopXgenre(restTemplate))
                .writer(writer)
                .build();
    }

}



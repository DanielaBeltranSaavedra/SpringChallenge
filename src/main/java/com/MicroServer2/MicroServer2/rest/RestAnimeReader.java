package com.MicroServer2.MicroServer2.rest;

import com.MicroServer2.MicroServer2.dto.Anime;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RestAnimeReader implements ItemReader<Anime>{
    private final String apiUrl;
    private final RestTemplate restTemplate;
    private List<Anime> ctfData;
    private int nextIndex;

    public RestAnimeReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public Anime read() throws Exception{
        if (ctfDataIsNotInitialized()){
            ctfData = fetchCTFDataFromAPI();
        }
        Anime nextAnime = null;
        if(nextIndex < ctfData.size()){
            nextAnime = ctfData.get(nextIndex);
            nextIndex++;
        }
        return nextAnime;
    }
    private boolean ctfDataIsNotInitialized() {
        return this.ctfData == null;
    }
    private List<Anime> fetchCTFDataFromAPI(){
        ResponseEntity<Anime[]> response = restTemplate.getForEntity(
                apiUrl,
                Anime[].class
        );
        Anime[] ctfData = response.getBody();
        return Arrays.asList(ctfData);
    }
}





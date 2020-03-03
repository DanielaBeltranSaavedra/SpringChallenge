package com.MicroServer2.MicroServer2.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.batch.item.ItemReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class RestReader implements ItemReader<Integer> {
    private final String apiUrl;
    private final RestTemplate restTemplate;
    private List<Integer> ctfData;
    private int nextIndex;

    public RestReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }


    public Integer read() throws Exception {
        if (ctfDataIsNotInitialized()) {
            ctfData = fetchCTFDataFromAPI();
        }
        Integer nextAnime = null;
        if (nextIndex < ctfData.size()) {
            nextAnime = ctfData.get(nextIndex);
            nextIndex++;
        }
        return nextAnime;
    }

    private boolean ctfDataIsNotInitialized() {
        return this.ctfData == null;
    }

    private List<Integer> fetchCTFDataFromAPI() {
        ResponseEntity<Integer[]> response = restTemplate.getForEntity(
                apiUrl,
                Integer[].class
        );
        Integer[] ctfData = response.getBody();

        return Arrays.asList(ctfData);
    }



}





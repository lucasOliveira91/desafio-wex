package com.wex.desafio.service.integration.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ExchangeRateResponse {


    private List<ExchangeData> data;
    private MetaData meta;
    private Links links;

}

@Data
class MetaData {

    private int count;
    private Map<String, String> labels;
    private Map<String, String> dataTypes;
    private Map<String, String> dataFormats;
    private int total_count;
    private int total_pages;
}

@Data
class Links {
    private String self;
    private String first;
    private String prev;
    private String next;
    private String last;
}

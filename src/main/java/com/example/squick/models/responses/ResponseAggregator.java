package com.example.squick.models.responses;

import lombok.Data;

@Data
public class ResponseAggregator {

    private final Long sum;

    public ResponseAggregator(Long sum) {
        this.sum = sum;
    }
}

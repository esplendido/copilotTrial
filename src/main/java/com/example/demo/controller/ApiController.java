package com.example.demo.controller;

import com.example.demo.model.ApiRequest;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.ApiHistory;
import com.example.demo.service.ApiHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private ApiHistoryService apiHistoryService;

    @PostMapping("/execute")
    public ResponseEntity<ApiResponse> executeApi(@RequestBody ApiRequest apiRequest) {
        WebClient webClient = webClientBuilder.build();

        WebClient.RequestBodySpec requestSpec = webClient.method(apiRequest.getMethod())
                .uri(apiRequest.getUrl())
                .headers(headers -> apiRequest.getHeader().forEach(headers::add));

        WebClient.ResponseSpec responseSpec;
        if (apiRequest.getMethod().equals("POST")) {
            responseSpec = requestSpec.bodyValue(apiRequest.getBody()).retrieve();
        } else {
            responseSpec = requestSpec.retrieve();
        }

        ApiResponse apiResponse = responseSpec.bodyToMono(ApiResponse.class).block();

        ApiHistory apiHistory = new ApiHistory();
        apiHistory.setUrl(apiRequest.getUrl());
        apiHistory.setMethod(apiRequest.getMethod());
        apiHistory.setHeaders(apiRequest.getHeader().toString());
        apiHistory.setBody(apiRequest.getBody());
        apiHistory.setResponse(apiResponse.toString());
        apiHistory.setTimestamp(LocalDateTime.now());

        apiHistoryService.saveApiHistory(apiHistory);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/history")
    public List<ApiHistory> getApiHistory(@RequestParam(defaultValue = "0") int page) {
        return apiHistoryService.getApiHistory(page);
    }
}

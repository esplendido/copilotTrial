package com.example.demo.service;

import com.example.demo.model.ApiHistory;
import com.example.demo.repository.ApiHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiHistoryService {

    @Autowired
    private ApiHistoryRepository apiHistoryRepository;

    public void saveApiHistory(ApiHistory apiHistory) {
        apiHistoryRepository.save(apiHistory);
    }

    public List<ApiHistory> getApiHistory(int page) {
        int pageSize = 20;
        int offset = page * pageSize;
        return apiHistoryRepository.findLatestApiHistories(pageSize, offset);
    }
}

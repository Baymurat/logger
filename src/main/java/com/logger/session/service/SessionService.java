package com.logger.session.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionService {

    private Map<String, Integer> callCountMap = new HashMap<>();

    public void updateCallCount(String sessionId) {
        if (!callCountMap.containsKey(sessionId)) {
            callCountMap.put(sessionId, 1);
        } else {
            int callCount = callCountMap.get(sessionId);
            callCountMap.put(sessionId, ++callCount);
        }

    }

    public int getCallCount(String sessionId) {
        return callCountMap.getOrDefault(sessionId, -1);
    }
}

package com.logger.session.service;

import com.logger.session.entity.Session;

public interface SessionService {

    Session create(String sessionId);

    Session read(String sessionId);

    Session update(String sessionId, int callCount);

    void delete(Long id);
}

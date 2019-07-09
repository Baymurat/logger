package com.logger.session.service;

import com.logger.session.entity.Session;
import com.logger.session.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Session create(String sessionId) {
        return sessionRepository.save(new Session(sessionId, 0));
    }

    @Transactional(readOnly = true)
    @Override
    public Session read(String sessionId) {
        return sessionRepository.findBySessionID(sessionId);
    }

    @Transactional
    @Override
    public Session update(String sessionId, int callCount) {
        Session session = sessionRepository.findBySessionID(sessionId);
        session.setCallCount(callCount);
        return session;
    }

    @Override
    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }
}

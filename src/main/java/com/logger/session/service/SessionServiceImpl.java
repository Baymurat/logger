package com.logger.session.service;

import com.logger.session.entity.Session;
import com.logger.session.repository.SessionRepository;
import com.logger.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Override
    public Session create(String sessionId) {
        Session session = sessionRepository.save(new Session(sessionId, 0));
        return ObjectMapperUtils.map(session, Session.class);
    }

    @Transactional(readOnly = true)
    @Override
    public Session read(String sessionId) {
        Session session = sessionRepository.findBySessionID(sessionId).orElseThrow(IllegalStateException::new);
        return ObjectMapperUtils.map(session, Session.class);
    }

    @Transactional
    @Override
    public Session update(String sessionId, int callCount) {
        Session session = sessionRepository.findBySessionID(sessionId).orElseThrow(IllegalStateException::new);
        session.setCallCount(callCount);
        //sessionRepository.flush();
        return ObjectMapperUtils.map(session, Session.class);
    }

    @Override
    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }
}

package com.logger.session.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionID;
    private int callCount;

    public Session() {}

    /*public Session(String sessionID) {
        this.sessionID = sessionID;
    }*/

    public Session(String sessionID, int callCount) {
        this.sessionID = sessionID;
        this.callCount = callCount;
    }
}

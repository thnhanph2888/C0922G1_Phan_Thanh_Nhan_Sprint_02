package com.example.system_management_restaurant_qtgm.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blacklist")
public class Blacklist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "session_id")
  private String sessionId;

  @Column(name = "token")
  private String token;

  @Column(name = "expire_at")
  private LocalDateTime expireAt;

  public Blacklist() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public LocalDateTime getExpireAt() {
    return expireAt;
  }

  public void setExpireAt(LocalDateTime expireAt) {
    this.expireAt = expireAt;
  }
}

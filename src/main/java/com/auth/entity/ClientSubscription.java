package com.auth.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENT_SUBSCRIPTION_DETAILS")
@Entity
public class ClientSubscription {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@Column(name = "PLAN_NAME")
	private String planName;
	
	@Column(name = "PLAN_EXPIARY")
	private LocalDateTime planExpiary;
	
	@Column(name = "ENCRYPTED_CLIENT_ID")
	private String encryptedClientId;
	
	@Column(name = "USER_ID")
	private String userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public LocalDateTime getPlanExpiary() {
		return planExpiary;
	}

	public void setPlanExpiary(LocalDateTime planExpiary) {
		this.planExpiary = planExpiary;
	}

	public String getEncryptedClientId() {
		return encryptedClientId;
	}

	public void setEncryptedClientId(String encryptedClientId) {
		this.encryptedClientId = encryptedClientId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}

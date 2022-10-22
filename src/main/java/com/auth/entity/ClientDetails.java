package com.auth.entity;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Table(name = "CLIENT_DETAILS")
@Entity
public class ClientDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "CLIENT_ID")
	private String clientId;

	@Column(name = "CLIENT_NAME")
	private String clientName;

	@Column(name = "APP_ID")
	private String appId;

	@Column(name = "APP_NAME")
	private String appName;

	@Column(name = "PLAN_START_DATE")
	private Time planStartDate;

	@Column(name = "PLAN_END_DATE")
	private Time planEndDate;

	@Column(name = "ENCRYPTED_CLIENT_ID")
	private String encryptedClientId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CLIENT_USER_API_ID", referencedColumnName = "ID")
	private ClientUserApiDetails clientUserApiDetails;

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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Time getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Time planStartDate) {
		this.planStartDate = planStartDate;
	}

	public Time getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Time planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getEncryptedClientId() {
		return encryptedClientId;
	}

	public void setEncryptedClientId(String encryptedClientId) {
		this.encryptedClientId = encryptedClientId;
	}

	public ClientUserApiDetails getClientUserApiDetails() {
		return clientUserApiDetails;
	}

	public void setClientUserApiDetails(ClientUserApiDetails clientUserApiDetails) {
		this.clientUserApiDetails = clientUserApiDetails;
	}
}

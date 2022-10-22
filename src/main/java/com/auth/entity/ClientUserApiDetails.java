package com.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "CLIENT_USER_API_DETAILS")
@Entity
public class ClientUserApiDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "CLIENT_ID")
	private int clientId;

	@Column(name = "CLIENT_SECRET")
	private String clientSecret;

	@Column(name = "API_BASE_URL")
	private String apiBaseUrl;

	@Column(name = "API_ENDPOINT_NAME")
	private String apiEndpointName;
	
	@Column(name = "HTTP_METHOD")
	private String httpMethod;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getApiBaseUrl() {
		return apiBaseUrl;
	}

	public void setApiBaseUrl(String apiBaseUrl) {
		this.apiBaseUrl = apiBaseUrl;
	}

	public String getApiEndpointName() {
		return apiEndpointName;
	}

	public void setApiEndpointName(String apiEndpointName) {
		this.apiEndpointName = apiEndpointName;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	
}

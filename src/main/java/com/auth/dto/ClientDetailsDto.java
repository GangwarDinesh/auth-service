package com.auth.dto;

public interface ClientDetailsDto {
	
	public String getClientId();
	
	public String getAppId();
	
	public String getClientSecret();
	
	public String getUserApiBaseUrl();
	
	public String getApiEndpointName();
	
	public String getHttpMethod();
	
}

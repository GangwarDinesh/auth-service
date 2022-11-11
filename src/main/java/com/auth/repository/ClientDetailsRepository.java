package com.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.auth.dto.ClientDetailsDto;
import com.auth.entity.ClientDetails;

@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Integer> {

	@Query(value = "SELECT cd.CLIENT_ID as clientId, cd.APP_ID as appId, cuad.CLIENT_SECRET as clientSecret, cuad.api_base_url as userApiBaseUrl, cuad.api_endpoint_name as apiEndpointName, cuad.http_method as httpMethod  FROM CLIENT_DETAILS cd, CLIENT_USER_API_DETAILS cuad WHERE cd.CLIENT_ID =:clientId AND cd.APP_ID =:appId AND cd.PLAN_START_DATE<=:planStartDate AND cd.PLAN_END_DATE>=:planEndDate and cuad.id=cd.client_user_api_id", nativeQuery = true)
	public Optional<ClientDetailsDto> findClient(String clientId, String appId, String planStartDate, String planEndDate);
}

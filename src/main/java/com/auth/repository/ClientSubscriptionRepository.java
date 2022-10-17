package com.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.auth.entity.ClientSubscription;

@Repository
public interface ClientSubscriptionRepository extends JpaRepository<ClientSubscription, Integer>{

	@Query(value = "from ClientSubscription cs where encryptedClientId=:encryptedClientId")
	public ClientSubscription findByEncryptedClientId(String encryptedClientId);

}

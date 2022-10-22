package com.auth.util;

import java.util.Base64;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClientIdEncryption {

	public static void main(String[] args) {
		encryptClientDetails();
		encodeBcryptPassword();
	}
	
	public static void encryptClientDetails() {
		//combination of CLIENT_ID,PLAN_NAME and PLANEXPIARY 
				String input = "Client-1###App-1###2022-10-21 16:20:13###2022-10-23 16:20:20";
				String enc = Base64.getEncoder().encodeToString(input.getBytes());
				System.out.println(enc);
	}
	
	public static void encodeBcryptPassword() {
		System.out.println(new BCryptPasswordEncoder().encode("dkumar"));
	}

}

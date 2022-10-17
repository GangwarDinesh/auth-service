package com.auth.util;

import java.util.Base64;

public class ClientIdEncryption {

	public static void main(String[] args) {
		//combination of CLIENT_ID,PLAN_NAME and PLANEXPIARY 
		String input = "client1###normal###2025-10-17 12:20:01";
		String enc = Base64.getEncoder().encodeToString(input.getBytes());
		System.out.println(enc);

	}

}

package com.auth.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth.entity.ClientSubscription;
import com.auth.exception.AuthenticationException;
import com.auth.repository.ClientSubscriptionRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiary}")
	private long jwtExpiary;

	@Autowired
	private ClientSubscriptionRepository clientSubscriptionRepository;

	public String extractUsername(String token) throws AuthenticationException {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) throws AuthenticationException {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws AuthenticationException {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) throws AuthenticationException {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new AuthenticationException(401,"Invalid Jwt");
		}
	}

	private Boolean isTokenExpired(String token) throws AuthenticationException {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	private String createToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpiary))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) throws AuthenticationException {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public boolean validateClientSubscription(String encryptedClientId) {
		boolean isPlanExpired = true;
		ClientSubscription clientSubscription = clientSubscriptionRepository.findByEncryptedClientId(encryptedClientId);
		if (null != clientSubscription) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			byte[] decodedBytes = Base64.getDecoder().decode(encryptedClientId);
			String decodedString[] = new String(decodedBytes).split("###");
			LocalDateTime planExpiary = LocalDateTime.parse(decodedString[2], formatter);
			LocalDateTime currentDateTime = LocalDateTime.now();
			isPlanExpired = planExpiary.isBefore(currentDateTime);
		}
		return isPlanExpired;
	}

}

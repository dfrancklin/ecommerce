package br.com.company.ecommerce.security;

import java.time.Duration;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.company.ecommerce.models.Account;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtManager {

	private static final MacAlgorithm ALGORITHM = Jwts.SIG.HS512;

	@Value("${jwt.secret-key}")
	private String secretKey;

	@Value("${jwt.issuer}")
	private String issuer;

	@Value("${jwt.duration}")
	private Duration duration;

	public String createToken(Account account) {
		return Jwts.builder()
				.subject(account.getEmail())
				.issuer(issuer)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + duration.toMillis()))
				.signWith(getKey(), ALGORITHM)
				.compact();
	}

	public boolean isValid(String token) {
		try {
			createParser().parseSignedClaims(token);
			return true;
		} catch (Exception e) {
			log.error("Unable to validate JWT Token. Error: {}", e.getMessage(), e);
		}

		return false;
	}

	public String getEmail(String token) {
		return createParser().parseSignedClaims(token).getPayload().getSubject();
	}

	private JwtParser createParser() {
		return Jwts.parser().verifyWith(getKey()).build();

	}

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(secretKey.getBytes());
	}

}

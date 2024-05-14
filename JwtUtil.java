package com.example.project.security;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.project.Model.ApplicationUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtUtil {
	private static final String DEFAULT_ROLE = "ROLE_USER";

  private String secretyKey;
  private long validityInMs;

  private static final String ROLES_KEY = "roles";

  public JwtUtil(@Value("${jwt.secret}") String secretKey, @Value("${jwt.token.validity}") long validityInMs) {
	  this.secretyKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	  this.validityInMs = validityInMs;
  }

  public String createToken(String username, List<String> roles) {
	  Claims claims = Jwts.claims();
	  claims.setSubject(username);
	  claims.put(ROLES_KEY, roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
	  
	  Date currentDate = new Date();
	  Date validityDate = new Date(currentDate.getTime() + this.validityInMs);
	  
	  return Jwts.builder().setClaims(claims)
			  .setIssuedAt(currentDate)
			  .setExpiration(validityDate)
			  .signWith(SignatureAlgorithm.HS256, this.secretyKey)
			  .compact();
  }

  public boolean isValidToken(String inputToken) {
	  try {
		  Jwts.parser().setSigningKey(this.secretyKey).parseClaimsJws(inputToken).getBody();
		  return true;
	  } catch (Exception ex) {
		  return false;
	  }
  }
  
  public String getUserName(String token) {
	  return Jwts.parser().setSigningKey(this.secretyKey).parseClaimsJws(token).getBody().getSubject();
  }
  
  public List<GrantedAuthority> getRoles(String token) {
	  List<Map<String, String>> roleClaims = Jwts.parser().setSigningKey(this.secretyKey).parseClaimsJws(token).getBody().get(ROLES_KEY, List.class);
	  
	  return roleClaims.stream().map(roleClaim -> new SimpleGrantedAuthority(roleClaim.get("authority"))).collect(Collectors.toList());
  }
}
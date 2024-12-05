package com.sb.rolebased.security.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.sb.rolebased.security.interfacess.UserDetailsImplRole;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtilsRole {
	
		private static final Logger logger = LoggerFactory.getLogger(JwtUtilsRole.class);
		
		@Value("${rolebas.app.jwtSecret}")
		private String jwtSecret;             // static for handling  problems

		@Value("${superadmin.app.jwtExpirationMs}")
		private int jwtExpirationMs;
		       

		@Value("${subadmin.app.jwtExpirationMs}")
		private int jwtSubAdminExpirationMs;
		
		public String generateJwtToken(Authentication authentication) {
				System.out.println("JwtUtilsRole 1");
			UserDetailsImplRole principal = (UserDetailsImplRole) authentication.getPrincipal();
			    System.out.println("JwtUtilsRole 1.1"+ principal);
			    System.out.println("JwtUtilsRole 1.1"+ principal.getUsername());
			
			    String role = null;
			    
			    for (GrantedAuthority authority : principal.getAuthorities()) {
			        if (authority.getAuthority().equals("ROLE_SUPERADMIN")) {
			            role = "SUPERADMIN";
			            break;
			        } else if (authority.getAuthority().equals("ROLE_SUBADMIN")) {
			            role = "SUBADMIN";
			            break;
			        }
			    }
			    
			    switch(role) {
				case "SUPERADMIN": {
					        return Jwts.builder()
							.setSubject((principal.getUsername()))
							.setIssuedAt(new Date())
							.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
							.signWith(SignatureAlgorithm.HS512, jwtSecret)
							.compact();
							
				}
				case "SUBADMIN": {
					        return Jwts.builder()
							.setSubject((principal.getUsername()))
							.setIssuedAt(new Date())
							.setExpiration(new Date((new Date()).getTime() + jwtSubAdminExpirationMs))
							.signWith(SignatureAlgorithm.HS512, jwtSecret)
							.compact();
							
				}
				default:
				{
					System.out.println("JwtUtilsRole exception ");
					throw new IllegalArgumentException("Unexpected value: " + role);
				}
			    }
			    
				
		}
		
		public String getUsernameFromJwtToken(String token) {	
			System.out.println("JwtUtilsRole 2");
			
			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();			
		}
		
		public boolean validateJwtToken(String authToken) {  
			System.out.println("JwtUtilsRole 3");
			try{
				Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
				System.out.println("JwtUtilsRole- validateJwtToken TRUE");
				return true;
				
			}catch (MalformedJwtException e) {
				System.out.println("JwtUtilsRole 3.1");
				logger.error("Invalid JWT token: {}", e.getMessage());
			}catch (ExpiredJwtException e) {
				System.out.println("JwtUtilsRole 3.2");
				logger.error("JWT token is Expired : {}", e.getMessage());
			}catch (UnsupportedJwtException e) {
				System.out.println("JwtUtilsRole 3.3");
				logger.error("JWT token unsupported : {}", e.getMessage());
			}catch (IllegalArgumentException e) {
				System.out.println("JwtUtilsRole 3.4");
				logger.error("JWT claims string is empty : {}", e.getMessage());
			}
			System.out.println("JwtUtilsRole- validateJwtToken FALSE");
			return false;
		}
}

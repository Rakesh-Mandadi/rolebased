package com.sb.rolebased.security.utils;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.sb.rolebased.security.exceptionhandling.InvalidatedTokenException;

@Service
public class JwtTokenService {

  private Set<String> invalidatedTokens = new HashSet<>();

  public void invalidateToken(String token) {
      invalidatedTokens.add(token);
  }

  public boolean isTokenInvalidated(String token) {
	  
	  
     if(invalidatedTokens.contains(token)) {
    	 throw new InvalidatedTokenException("session is invalideted, Login again");
     }
       return false;
  }
}


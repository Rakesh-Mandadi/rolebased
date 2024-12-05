package com.sb.rolebased.security.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sb.rolebased.security.exceptionhandling.InvalidatedTokenException;
import com.sb.rolebased.security.interfacess.UserDetailServiceImplRole;
import com.sb.rolebased.security.interfacess.UserDetailsImplRole;
import com.sb.rolebased.security.utils.JwtTokenService;
import com.sb.rolebased.security.utils.JwtUtilsRole;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtilsRole jwtUtilsRole;
	
	@Autowired
    private JwtTokenService jwtTokenService;

	
	@Autowired
	private UserDetailServiceImplRole userDetailServiceImplRole;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 System.out.println("AuthTokenFilter 1");
	        System.out.println("Request URI: " + request.getRequestURI());
	        System.out.println("Request Headers: ");
	        request.getHeaderNames().asIterator().forEachRemaining(header -> 
	            System.out.println(header + ": " + request.getHeader(header))
	        );
		
		try {
			String jwt = parseJwt(request);
			
			System.out.println("tocken resived .. "+ jwt);
			
			  if (jwt != null && jwtUtilsRole.validateJwtToken(jwt) && !jwtTokenService.isTokenInvalidated(jwt)) {
			//if(jwt != null && jwtUtilsRole.validateJwtToken(jwt)) {  // remember validation method we modified to make static 
				System.out.println("AuthTokenFilter 2");
				
				String usename= jwtUtilsRole.getUsernameFromJwtToken(jwt);
				
				UserDetails userDetails = userDetailServiceImplRole.loadUserByUsername(usename);
				
				System.out.println("AuthTokenFilter 2.1");	
				
				UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					System.out.println("AuthTokenFilter 2.2"+ authentication);	
			}
			  
						
		}catch (InvalidatedTokenException e) {
	        
	        response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
	        logger.error("Invalid token: {}", e.getMessage());
	        
	    } catch (Exception e) {
			
			logger.error("can not set User Authentication : {}", e);
		}
		filterChain.doFilter(request, response);
	}
	
	private String parseJwt(HttpServletRequest request) {
		System.out.println("AuthTokenFilter 3");
		
			String headerAuth = request.getHeader("Authorization");
			 System.out.println("Authorization Header: " + headerAuth);
			
			if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
				System.out.println("AuthTokenFilter 3.1");
				return headerAuth.substring(7, headerAuth.length());
			}
			System.out.println("AuthTokenFilter 3.2");
		return null;
	}

}

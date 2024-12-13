package com.sb.rolebased.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sb.rolebased.security.interfacess.UserDetailServiceImplRole;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigRole {
	
	

	
	@Bean
	public UserDetailsService userDetailsService() {
			System.out.println("SecurityConfigRole 1");
		return new UserDetailServiceImplRole();
	}
	
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		System.out.println("SecurityConfigRole 2");

		return new AuthTokenFilter();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		System.out.println("inside securityConfigRole authenticationManager");
        return config.getAuthenticationManager();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("inside passwordEncoder");
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("In security filter chain....");
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v1/auth/signup","/api/v1/auth/*", "/api/v1/auth/signin", "/api/v1/auth/subAdmin/signin", "/api-docs", "/api/v1/users/*")
                        			.permitAll()
 //                       		.requestMatchers("/api/v1/users/**")
 //                            .authenticated()

                )
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults()).build();
    }
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		System.out.println("SecurityConfigRole 3");

		
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
				authenticationProvider.setUserDetailsService(userDetailsService());
				authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return authenticationProvider;
	}
	
//	@SuppressWarnings("deprecation")
//	@Override
//	public  configure(HttpSecurity http) throws Exception {
//	    http.csrf().disable()
//	        .authorizeRequests()
//	        .requestMatchers("/api/v1/auth/send-otp", "/api/v1/auth/reset-password").permitAll()
//	        .anyRequest().authenticated();
//	}

	
	
	
	
	
}






/*
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigRole {

    @Bean
    public UserDetailsService userDetailsService() {
        System.out.println("SecurityConfigRole 1");
        return new UserDetailServiceImplRole();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        System.out.println("SecurityConfigRole 2");
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        System.out.println("inside securityConfigRole authenticationManager");
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("inside passwordEncoder");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("In security filter chain....");
        http.csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/signin", "/api/v1/auth/subAdmin/signin", "/api-docs").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/v1/users/**").authenticated()
            )
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        System.out.println("SecurityConfigRole 3");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
}
*/





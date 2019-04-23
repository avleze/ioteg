package com.ioteg.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ioteg.services.UserService;

import static com.ioteg.security.SecurityConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public SecurityConfig(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and()
		.csrf()
			.disable().authorizeRequests()
		.antMatchers(HttpMethod.POST, SIGN_UP_URL)
			.permitAll()
		.antMatchers("*", "/h2-console/**")
			.permitAll()
		.antMatchers("*", "/api/**")
			.permitAll()
		.anyRequest()
	   		.permitAll().and()
		.addFilterBefore(new JWTAuthenticationFilter("/api/users/signin", authenticationManager(), userService),
						UsernamePasswordAuthenticationFilter.class)
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), userService))
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.headers().frameOptions().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
				"/swagger-ui.html", "/webjars/**");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConf = new CorsConfiguration().applyPermitDefaultValues();
		corsConf.applyPermitDefaultValues();
		corsConf.addExposedHeader("Authorization");
		corsConf.addAllowedMethod(HttpMethod.OPTIONS);
		corsConf.addAllowedMethod(HttpMethod.PATCH);
		corsConf.addAllowedMethod(HttpMethod.PUT);
		corsConf.addAllowedMethod(HttpMethod.DELETE);
		corsConf.addAllowedMethod(HttpMethod.GET);
		corsConf.addAllowedMethod(HttpMethod.POST);
		source.registerCorsConfiguration("/**", corsConf);

		return source;
	}

}

package com.example.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.example.project.service.UserAuthService;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  ApiAuthenticationEntryPoint entryPoint;
  
  @Autowired
  UserAuthService userAuthService;

  @Override
  public void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests().antMatchers("/register", "/signin", "/h2-console/**").permitAll().anyRequest().authenticated();
	  
//	  http.exceptionHandling()
	  
	  http.addFilterBefore(new JwtAuthenticationFilter(userAuthService), UsernamePasswordAuthenticationFilter.class);
	  
//	  http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  
	  http.csrf().disable();
	  
//	  http.exceptionHandling()
//	    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/register")
    .antMatchers("/signin")
    .antMatchers("/h2-console/**");
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
}
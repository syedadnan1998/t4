package com.example.project.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.project.Model.ApplicationUser;
import com.example.project.repository.ApplicationUserRepository;
import com.example.project.security.JwtUtil;



@Service
public class UserAuthService implements UserDetailsService {
	private static final String DEFAULT_ROLE = "ROLE_USER";
	
	@Autowired
	private JwtUtil jwtProvider;
	
	@Autowired
	PasswordEncoder passwordEncoder;

  @Autowired
  private ApplicationUserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser user = userRepo.findByUserName(username);
//		user.password = passwordEncoder.encode(user.password);
		
		
		List<GrantedAuthority> authorities = Arrays.asList(DEFAULT_ROLE).stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		
		return User.withUsername(username)
				.password(user.password)
				.authorities(authorities)
				.accountExpired(false)
				.accountLocked(false)
				.disabled(false)
				.credentialsExpired(false)
				.build();
	}
	
	public Optional<UserDetails> getUserByJwtToken(String token) {
		if (jwtProvider.isValidToken(token)) {
			return Optional.of(
					User.withUsername(jwtProvider.getUserName(token))
					.password("")
					.authorities(jwtProvider.getRoles(token))
					.accountExpired(false)
					.accountLocked(false)
					.credentialsExpired(false)
					.disabled(false)
					.build()
					);
			
		}
		return Optional.empty();
	}
}
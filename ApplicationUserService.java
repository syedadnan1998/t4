package com.example.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.simple.JSONObject;

import com.example.project.Model.ApplicationUser;
import com.example.project.repository.ApplicationUserRepository;
import com.example.project.security.JwtUtil;

import org.springframework.security.core.AuthenticationException;


@Service
public class ApplicationUserService {
  @Autowired
  AuthenticationManager authManager;

  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  ApplicationUserRepository userRepo;

  public JSONObject sigin(String username, String password) {
    JSONObject map = new JSONObject();
    ApplicationUser user = userRepo.findByUserName(username);
    try {
    	System.out.println(username + "/n" + password);
      authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      String token = jwtUtil.createToken(username, Arrays.asList("ROLE_USER"));
      map.put("token", token);
      map.put("id", user.user_name);
      return map;
    } catch (AuthenticationException ex) {
    	ex.printStackTrace();
      map.clear();
      map.put("message", "Username or Password is Incorrext");
      return (map);
    }
  }

  public ApplicationUser viewProfile(String id) {
    ApplicationUser user = userRepo.findByUserName(id);
    return user;
  }

  public ApplicationUser editProfile(String id) {
    Optional<ApplicationUser> user = userRepo.findById(id);
    if (user.isPresent()) {
      userRepo.save(user.get());
      return user.get();
    }
    return null;
  }

}


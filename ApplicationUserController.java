package com.example.project.controller;


import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.Model.ApplicationUser;
import com.example.project.repository.ApplicationUserRepository;
import com.example.project.service.ApplicationUserService;


@RestController
public class ApplicationUserController {

  @Autowired
  ApplicationUserRepository userRepo;

  @Autowired
  ApplicationUserService userService;
  
  @Autowired
  PasswordEncoder passwordEncoder;

  @PostMapping(value="/register")
  public Map<String, String> registerUser(@RequestBody ApplicationUser userDetails) {
    Map<String , String> map = new HashMap<String, String>();
    String pwd = userDetails.password;
    String encrPwd = passwordEncoder.encode(pwd);
    userDetails.password = encrPwd;
    try {
      userRepo.save(userDetails);
      map.put("message", "Registration successful");
    } catch (Exception ex) {
      map.put("message", "Password or username policy failed");
    }
    return map;
  }

  @PostMapping(value="/signin")
  public JSONObject signin (@RequestBody ApplicationUser userDetails) {
    return userService.sigin(userDetails.user_name, userDetails.password);
  }

  @GetMapping(value = "/viewprofile/{userId}")
  public ApplicationUser viewProfile(@PathVariable("userId") String id) {
    return userService.viewProfile(id);
  }

  @GetMapping(value = "/editprofile/{id}")
  public ApplicationUser editProfile(@PathVariable String id) {
    return userService.editProfile(id);
  }
}

package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.Model.ApplicationUser;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository  extends JpaRepository<ApplicationUser, String>{
  @Query(value="select m from ApplicationUser m where m.user_name=:username")
  public ApplicationUser findByUserName(@Param(value="username")String username);
}

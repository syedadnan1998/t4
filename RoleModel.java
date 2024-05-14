package com.example.project.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RoleModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int roleId;
	public String role;
	

}

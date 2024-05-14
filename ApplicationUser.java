package com.example.project.Model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "application_user")
public class ApplicationUser {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int userId;
    public String user_name;
    public String user_email;
    public String password;
    public String user_mobile;
    public String location;
    @OneToOne
    @JoinColumn(name = "user_role", referencedColumnName = "roleId")
    public RoleModel userRole;

    public ApplicationUser(String user_name, String user_email, String password, String user_mobile, String location) {
        super();
        this.user_name = user_name;
        this.user_email = user_email;
        this.password = password;
        this.user_mobile = user_mobile;
        this.location = location;
    }

    public ApplicationUser() {
        super();
    }

    public ApplicationUser(String user_name, String password) {
        super();
        this.user_name = user_name;
        this.password = password;
    }

    //    public Date user_dob;

}

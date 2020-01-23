package com.onlinemusicstore.app.models;

import java.util.HashSet;
import java.util.Set;



public class User  extends AbstractEntity {

	
	
	private String email;
	

	private String password;
	
	private boolean verified;

	private String  username;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isVerified() {
		return verified;
	}
	
	private Set<Role> roles = new HashSet<>();
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public User() {
		
	}
	
	public User(User user) {


		this.email = user.getEmail();

		this.password = user.getPassword();

		this.roles = user.getRoles();
		
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	
	
	
}	

	
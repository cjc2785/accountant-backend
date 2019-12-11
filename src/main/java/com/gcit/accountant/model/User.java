package com.gcit.accountant.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="employees")
public class User implements Serializable {
	
	static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "employeeId")
	private Integer userId;
	
	@Column(name = "employeeEmail")
	private String email;
	
	@Column
	private String password;
	
	@ManyToMany
	@JoinTable(name = "employeeRoles",
			joinColumns = @JoinColumn(name="employeeId"),
			inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles;
	
	public User() { }
	
	public User(User user) {
		userId = user.userId;
	    email = user.email;
        password = user.password;
        roles = user.roles;
    }

	public User(Integer userId, String email, String password, Set<Role> roles) {
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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
	
    @Override
    public boolean equals(Object o) {
        if(o == null || !(o instanceof User)) {
        	return false;
        }
        User u = (User)o;
        
        return Objects.equals(userId, u.getUserId());
    }
	
	@Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
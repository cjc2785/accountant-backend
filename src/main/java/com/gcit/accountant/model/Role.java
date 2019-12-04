package com.gcit.accountant.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer roleId;
	
	@Column
	private String roleName;
	
	public Role() { }

	public Role(Integer roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
    @Override
    public boolean equals(Object o) {
   
        if (o == null || !(o instanceof Role)) {
        	return false;
        }
        Role role = (Role)o;
        return Objects.deepEquals(roleId, role.getRoleId());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }
}

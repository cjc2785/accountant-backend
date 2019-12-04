package com.gcit.accountant.model;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	private User user;
	private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
    	this.user = user;
    	this.authorities = authorities;
    }
    
    public User getUser() {
    	return user;
    }
  

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
    	
        if(o == null || !(o instanceof UserPrincipal)) {
        	return false;
        }
        UserPrincipal principal = (UserPrincipal)o;
        
        return Objects.equals(user, principal.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}
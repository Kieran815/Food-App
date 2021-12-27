package com.food.foodapp.security;

import com.food.foodapp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

public class MyUserDetails implements UserDetails {

  private User user;
  private String userName;
  private String password;
  private String emailAddress;

  public MyUserDetails(User user) {
    this.user = user;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new HashSet<GrantedAuthority>();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
//    using email, not username
    return user.getEmailAddress();
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

  public User getUser() {
    return user;
  }
}

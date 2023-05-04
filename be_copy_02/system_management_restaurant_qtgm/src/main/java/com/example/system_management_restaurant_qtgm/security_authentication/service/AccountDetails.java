package com.example.system_management_restaurant_qtgm.security_authentication.service;

import com.example.system_management_restaurant_qtgm.model.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AccountDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    private int userId;
    private String username;
    private String name;
    @JsonIgnore
    private String password;
    private List<GrantedAuthority> authorities;

    public AccountDetails(int userId, String name, String username, String password,
                          List<GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

    // This func help you guys get account information to AccountDetailService
    public static AccountDetails build(Account account) {
        List<GrantedAuthority> authorities = account.getAccountRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().getName()))
                .collect(Collectors.toList());
        String name = "";
        int userId = 0;
        try {
            name = account.getEmployee().getName();
            userId = account.getEmployee().getId();
        } catch (Exception e) {
            name = account.getCustomer().getName();
            userId = account.getCustomer().getId();
        }
        return new AccountDetails(
                userId,
                name,
                account.getUsername(),
                account.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AccountDetails account = (AccountDetails) o;
        return Objects.equals(username, account.username);
    }
}

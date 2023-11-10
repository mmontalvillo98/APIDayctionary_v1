package com.tfg.mariomh.v2.myApi.auth;

import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.types.Role;
import com.tfg.mariomh.v2.myApi.utils.TokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final UserDTO userDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return TokenUtils.getAuthorities(userDTO);
    }

    public List<Role> getRoles(){
        return userDTO.getRoles();
    }

    @Override
    public String getPassword() {
        return userDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return userDTO.getId();
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

}

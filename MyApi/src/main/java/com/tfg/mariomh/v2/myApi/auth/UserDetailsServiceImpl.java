package com.tfg.mariomh.v2.myApi.auth;

import com.tfg.mariomh.v2.myApi.services.bbdd.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return new UserDetailsImpl(userService.getByMailCompleteUser(mail));
    }

}

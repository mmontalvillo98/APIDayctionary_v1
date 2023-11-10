package com.tfg.mariomh.v2.myApi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfg.mariomh.v2.myApi.models.dtos.UserDTO;
import com.tfg.mariomh.v2.myApi.utils.TokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserDTO userDTO = new UserDTO();
        try {
            userDTO = new ObjectMapper().readValue(request.getReader(), UserDTO.class);
        } catch (IOException e) {}
        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                userDTO.getMail(),
                userDTO.getPassword(),
                //TokenUtils.getAuthorities(userDTO)
                Collections.emptyList()
        );
        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl userDetails = (UserDetailsImpl)authResult.getPrincipal();
        String token = TokenUtils.createToken(userDetails.getUsername(), userDetails.getRoles());
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, authResult);
    }
}

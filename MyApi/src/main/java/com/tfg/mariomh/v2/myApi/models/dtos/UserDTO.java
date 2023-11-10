package com.tfg.mariomh.v2.myApi.models.dtos;

import com.tfg.mariomh.v2.myApi.types.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String mail;
    private String password;
    private Boolean notifications;
    private Long version;
    private List<Role> roles;

}

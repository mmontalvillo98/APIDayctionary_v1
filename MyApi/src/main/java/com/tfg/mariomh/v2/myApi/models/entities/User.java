package com.tfg.mariomh.v2.myApi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.mariomh.v2.myApi.types.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document("User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class User extends BdObject {
    private String mail;
    private String password;
    private Boolean notifications;
    private List<Role> roles;
    @JsonIgnore
    @DocumentReference(lazy = true)
    private List<Answer> answers;
}

package com.tfg.mariomh.v2.myApi.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynonymSingle implements Word{

    private String word;
    private String synonym;

}

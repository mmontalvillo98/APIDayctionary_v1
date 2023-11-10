package com.tfg.mariomh.v2.myApi.models.entities;

import com.tfg.mariomh.v2.myApi.types.MiniGame;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameAnswer {
    private MiniGame miniGame;
    private String word;
}

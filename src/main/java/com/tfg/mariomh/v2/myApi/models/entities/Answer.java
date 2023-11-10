package com.tfg.mariomh.v2.myApi.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document("Answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Answer extends BdObject {
    @DocumentReference(lazy=true)
    private User user;
    @DocumentReference(lazy=true)
    private Game game;
    private List<GameAnswer> gameAnswers;
}

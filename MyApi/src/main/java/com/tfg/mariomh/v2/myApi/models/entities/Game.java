package com.tfg.mariomh.v2.myApi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.mariomh.v2.myApi.utils.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Document("Game")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Game extends BdObject implements Word {
    @Indexed(name="word_word_game_uk", unique = true)
    private String word;
    @Indexed(name="date_word_game_uk", unique = true)
    private String date;
    private Definition definition;
    private Phonetic phonetic;
    private String synonym;
    private List<DefinitionSingle> similarDefinitions;
    private List<PhoneticSingle> similarPhonetics;
    private List<String> similarSynonyms;
    @JsonIgnore
    @DocumentReference(lazy = true)
    private List<Answer> answers;

    public List<DefinitionSingle> getDefinitionMiniGame(){
        List<DefinitionSingle> definitionMiniGame = new ArrayList<>();
        if(Validator.objectsNotNull(this.definition) && Validator.objectsNotNull(this.similarDefinitions)){
            definitionMiniGame.add(new DefinitionSingle(this.word, this.definition));
            definitionMiniGame.addAll(this.similarDefinitions);
        }
        return definitionMiniGame;
    }

    public List<PhoneticSingle> getPhoneticMiniGame(){
        List<PhoneticSingle> phoneticMiniGame = new ArrayList<>();
        if(Validator.objectsNotNull(this.phonetic) && Validator.objectsNotNull(this.similarPhonetics)){
            phoneticMiniGame.add(new PhoneticSingle(this.word, this.phonetic));
            phoneticMiniGame.addAll(this.similarPhonetics);
        }
        return phoneticMiniGame;
    }

    public List<String> getSynonymMiniGame(){
        List<String> synonymMiniGame = new ArrayList<>();
        if(Validator.objectsNotNull(this.synonym) && Validator.objectsNotNull(this.similarSynonyms)){
            synonymMiniGame.add(this.synonym);
            synonymMiniGame.addAll(this.similarSynonyms);
        }
        return synonymMiniGame;
    }

}

package com.tfg.mariomh.v2.myApi.mappers.interfaces;

import com.tfg.mariomh.v2.myApi.models.entities.DefinitionSingle;
import com.tfg.mariomh.v2.myApi.models.entities.PhoneticSingle;
import com.tfg.mariomh.v2.myApi.models.entities.SynonymSingle;
import com.tfg.mariomh.v2.myApi.models.entities.Game;

import java.util.List;

public interface IMiniGameMapper {

     public List<SynonymSingle> gameToSynonymMiniGame(Game source);
     public List<PhoneticSingle> gameToPhoneticMiniGame(Game source);

     public List<DefinitionSingle> gameToDefinitionMiniGame(Game source);

}

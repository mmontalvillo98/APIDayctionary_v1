package com.tfg.mariomh.v2.myApi.mappers.implementations;

import com.tfg.mariomh.v2.myApi.mappers.interfaces.IMiniGameMapper;
import com.tfg.mariomh.v2.myApi.models.entities.DefinitionSingle;
import com.tfg.mariomh.v2.myApi.models.entities.PhoneticSingle;
import com.tfg.mariomh.v2.myApi.models.entities.SynonymSingle;
import com.tfg.mariomh.v2.myApi.models.entities.Game;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MiniGameMapperImpl implements IMiniGameMapper {

    @Override
    public List<SynonymSingle> gameToSynonymMiniGame(Game source) {
        List<SynonymSingle> list = new ArrayList<>();
        list.add(new SynonymSingle(source.getWord(), source.getSynonym()));
        list.addAll(source.getSimilarSynonyms().stream()
                .map(syn->new SynonymSingle(syn, syn))
                .toList());
        return list;
    }
    @Override
    public List<PhoneticSingle> gameToPhoneticMiniGame(Game source){
        List<PhoneticSingle> target = new ArrayList<>();
        if(source!=null){
            target.add(new PhoneticSingle(source.getWord(), source.getPhonetic()));
            target.addAll(source.getSimilarPhonetics());
        }
        return target;
    }
    @Override
    public List<DefinitionSingle> gameToDefinitionMiniGame(Game source){
        List<DefinitionSingle> target = new ArrayList<>();
        if(source!=null){
            target.add(new DefinitionSingle(source.getWord(), source.getDefinition()));
            target.addAll(source.getSimilarDefinitions());
        }
        return target;
    }

}

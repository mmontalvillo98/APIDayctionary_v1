package com.tfg.mariomh.v2.myApi.models.entities;

import com.tfg.mariomh.v2.myApi.types.PartOfSpeech;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Definition {
    private PartOfSpeech type;
    private String text;
}

package com.tfg.mariomh.v2.myApi.models.entities;

import com.tfg.mariomh.v2.myApi.exceptions.responses.VersionResponseException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class BdObject {
    @Id
    protected String id;
    @Version
    protected Long version;
    protected Boolean deleted = false;
    @CreatedDate
    @Field("created_date")
    protected LocalDateTime createdDate;
    @LastModifiedDate
    @Field("last_modified_date")
    protected LocalDateTime lastModifiedDate;
    public void setVersion(Long version){
        if(this.version != null && !Objects.equals(this.version, version)){
            throw new VersionResponseException();
        }
        this.version = version;
    }
}

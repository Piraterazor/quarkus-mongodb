package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;

/**
 * Entity class representing a Store document from the embedded_Stores collection.
 */
@MongoEntity(collection = "StoreV2")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
//@ToString(of = {"id", "title", "year", "genres"})
public class Store {

    @BsonId
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer storeId;
    private String administrativeLabel;
}

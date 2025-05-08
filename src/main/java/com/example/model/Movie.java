package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Map;

/**
 * Entity class representing a movie document from the embedded_movies collection.
 */
@MongoEntity(collection = "embedded_movies")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"id", "title", "year", "genres"})
public class Movie {

    @BsonId
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String title;
    private Integer year;
    private List<String> cast;
    private List<String> genres;
    private String plot;
    private String fullplot;
    private Map<String, Object> awards;
    private Map<String, Object> imdb;
    private Map<String, Object> tomatoes;

    // Additional fields can be dynamically mapped
    private Map<String, Object> additionalFields;

    // No need for getters, setters, constructors, or toString method
    // They are provided by Lombok annotations
}

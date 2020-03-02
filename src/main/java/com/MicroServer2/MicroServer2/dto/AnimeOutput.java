package com.MicroServer2.MicroServer2.dto;
import lombok.Data;

@Data

public class AnimeOutput {
    private String anime_id;
    private String name;
    private String type;
    private String source;
    private String rating;

    public AnimeOutput(String anime_id, String name, String rating, String type,String source) {
        this.anime_id = anime_id;
        this.name = name;
        this.type = type;
        this.source = source;
        this.rating = rating;
    }

    public String getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(String anime_id) {
        this.anime_id = anime_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "AnimeOutput{" +
                "anime_id='" + anime_id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}

package com.MicroServer2.MicroServer2.dto;


import lombok.Data;

@Data

public class Anime {
    private String _id;
    private int anime_id;
    private String name;
    private String genre;
    private String type;
    private int episodes;
    private double rating;
    private String img;
    private String studios;
    private String source;
    private String main_cast;
    private int c1;
    private int c2;
    private int members;
    public Anime(){

    }
    public Anime(String _id, int anime_id, String name, String genre, String type, int episodes, double rating, String img, String studios, String source, String main_cast, int c1, int c2, int members) {
        this._id = _id;
        this.anime_id = anime_id;
        this.name = name;
        this.genre = genre;
        this.type = type;
        this.episodes = episodes;
        this.rating = rating;
        this.img = img;
        this.studios = studios;
        this.source = source;
        this.main_cast = main_cast;
        this.c1 = c1;
        this.c2 = c2;
        this.members = members;
    }


    @Override
    public String toString() {
        return "Anime{" +
                "_id='" + _id + '\'' +
                ", anime_id=" + anime_id +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", type='" + type + '\'' +
                ", episodes=" + episodes +
                ", rating=" + rating +
                ", img='" + img + '\'' +
                ", studios='" + studios + '\'' +
                ", source='" + source + '\'' +
                ", main_cast='" + main_cast + '\'' +
                ", c1=" + c1 +
                ", c2=" + c2 +
                ", members=" + members +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getAnime_id() {
        return anime_id;
    }

    public void setAnime_id(int anime_id) {
        this.anime_id = anime_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getEpisodes() {
        return episodes;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getMain_cast() {
        return main_cast;
    }

    public void setMain_cast(String main_cast) {
        this.main_cast = main_cast;
    }

    public int getC1() {
        return c1;
    }

    public void setC1(int c1) {
        this.c1 = c1;
    }

    public int getC2() {
        return c2;
    }

    public void setC2(int c2) {
        this.c2 = c2;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }
}
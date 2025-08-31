package com.example.eventsservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class MovieEvent extends BaseEvent {
    @JsonProperty("movieId")
    private String movieId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("action")
    private String action="create";

    public MovieEvent() {
        super("MOVIE_EVENT");
    }

    public MovieEvent(String movieId, String title, String genre, Integer duration, BigDecimal price, String action) {
        super("MOVIE_EVENT");
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.price = price;
        this.action = action;
    }

    // Getters and setters
    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
}

package com.smarthome.temperatureapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/users")
    public List<User> getUsers() {
        // Sample data - replace with actual service call
        return Arrays.asList(
                new User(1L, "John Doe", "john.doe@example.com"),
                new User(2L, "Jane Smith", "jane.smith@example.com"),
                new User(3L, "Bob Johnson", "bob.johnson@example.com")
        );
    }

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        // Sample data - replace with actual service call
        return Arrays.asList(
                new Movie(1L, "The Shawshank Redemption", "Drama", 1994),
                new Movie(2L, "The Godfather", "Crime", 1972),
                new Movie(3L, "The Dark Knight", "Action", 2008)
        );
    }

    // Inner classes for data models
    public static class User {
        private Long id;
        private String name;
        private String email;

        public User() {}

        public User(Long id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class Movie {
        private Long id;
        private String title;
        private String genre;
        private Integer year;

        public Movie() {}

        public Movie(Long id, String title, String genre, Integer year) {
            this.id = id;
            this.title = title;
            this.genre = genre;
            this.year = year;
        }

        // Getters and setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getGenre() { return genre; }
        public void setGenre(String genre) { this.genre = genre; }

        public Integer getYear() { return year; }
        public void setYear(Integer year) { this.year = year; }
    }
}
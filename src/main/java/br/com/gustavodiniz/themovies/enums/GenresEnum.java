package br.com.gustavodiniz.themovies.enums;

public enum GenresEnum {

    ACTION(28L, "Action"),
    ADVENTURE(12L, "Adventure"),
    ANIMATION(16L, "Animation"),
    COMEDY(35L, "Comedy"),
    DRAMA(18L, "Drama"),
    CRIME(80L, "Crime"),
    DOCUMENTARY(99L, "Documentary");

    private Long id;
    private String name;

    GenresEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package org.example.models;

public class Pet {

    private Long id;
    private String nome;
    private Long tutorId;

    public Pet(Long id, String nome, Long tutorId) {
        this.id = id;
        this.nome = nome;
        this.tutorId = tutorId;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Long getTutorId() {
        return tutorId;
    }
}
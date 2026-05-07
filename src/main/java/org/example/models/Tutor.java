package org.example.models;

public class Tutor {

    private Long id;
    private String nome;

    public Tutor(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
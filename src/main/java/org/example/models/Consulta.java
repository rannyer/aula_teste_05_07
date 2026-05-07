package org.example.models;

import java.time.LocalDateTime;

public class Consulta {

    private Long id;
    private Tutor tutor;
    private Pet pet;
    private LocalDateTime dataHora;
    private double valor;
    private static Long idController = 0L;

    public Consulta(
            Long id,
            Tutor tutor,
            Pet pet,
            LocalDateTime dataHora,
            double valor
    ) {
        this.id = idController++;
        this.tutor = tutor;
        this.pet = pet;
        this.dataHora = dataHora;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public Pet getPet() {
        return pet;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public double getValor() {
        return valor;
    }
}
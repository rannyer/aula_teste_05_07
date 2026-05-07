package org.example.repositories.interfaces;

import org.example.models.Tutor;

import java.util.Optional;

public interface TutorRepository {

    Optional<Tutor> buscarPorId(Long id);

    void salvar(Tutor tutor);
}
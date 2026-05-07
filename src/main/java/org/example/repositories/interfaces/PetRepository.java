package org.example.repositories.interfaces;

import org.example.models.Pet;
import org.example.models.Tutor;

import java.util.Optional;

import java.util.Optional;

public interface PetRepository {

    Optional<Pet> buscarPorId(Long id);

    void salvar(Pet pet);
}
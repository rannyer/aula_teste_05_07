package org.example.repositories.fake;

import org.example.models.Pet;
import org.example.repositories.interfaces.PetRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PetRepositoryFake implements PetRepository {

    private Map<Long, Pet> pets =  new HashMap<>();

    @Override
    public Optional<Pet> buscarPorId(Long id) {
        return Optional.ofNullable(pets.get(id));
    }

    @Override
    public void salvar(Pet pet) {
        pets.put(pet.getId(), pet);
    }
}

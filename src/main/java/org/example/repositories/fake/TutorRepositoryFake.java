package org.example.repositories.fake;

import org.example.models.Tutor;
import org.example.repositories.interfaces.TutorRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TutorRepositoryFake implements TutorRepository
{

    private Map<Long, Tutor> tutores =  new HashMap<>();
    @Override
    public void salvar(Tutor tutor) {
        tutores.put(tutor.getId(), tutor);
    }

    @Override
    public java.util.Optional<Tutor> buscarPorId(Long id) {
        return Optional.ofNullable(tutores.get(id));
    }
}

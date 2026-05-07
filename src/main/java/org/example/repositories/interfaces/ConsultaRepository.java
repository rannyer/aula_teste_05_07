package org.example.repositories.interfaces;

import org.example.models.Consulta;
import org.example.models.Tutor;

import java.util.Optional;

import java.util.List;

public interface ConsultaRepository {

    void salvar(Consulta consulta);

    List<Consulta> listarTodas();
}
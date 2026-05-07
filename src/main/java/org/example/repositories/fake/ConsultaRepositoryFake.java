package org.example.repositories.fake;

import org.example.models.Consulta;
import org.example.repositories.interfaces.ConsultaRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultaRepositoryFake implements ConsultaRepository
{
    private List<Consulta> consultas = new ArrayList<>();


    @Override
    public void salvar(Consulta consulta) {

        consultas.add(consulta);
    }

    @Override
    public List<Consulta> listarTodas() {
        return consultas;
    }
}

package org.example.services;

import org.example.models.Tutor;

public interface PagamentoConsultaService {

    boolean aprovarPagamento(Tutor tutor, double valor);
}
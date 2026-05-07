package org.example.services;

import java.time.LocalDateTime;

public interface AgendaVeterinarioService {

    boolean veterinarioDisponivel(LocalDateTime dataHora);
}
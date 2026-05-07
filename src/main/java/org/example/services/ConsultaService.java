package org.example.services;

import org.example.models.Consulta;
import org.example.models.Pet;
import org.example.models.Tutor;
import org.example.repositories.interfaces.ConsultaRepository;
import org.example.repositories.interfaces.PetRepository;
import org.example.repositories.interfaces.TutorRepository;

import java.time.LocalDateTime;

public class ConsultaService {
    private TutorRepository tutorRepository;
    private PetRepository petRepository;
    private ConsultaRepository consultaRepository;

    private AgendaVeterinarioService agendaVeterinarioService;
    private PagamentoConsultaService pagamentoConsultaService;

    public ConsultaService(TutorRepository tutorRepository,
                           PetRepository petRepository,
                           ConsultaRepository consultaRepository,
                           AgendaVeterinarioService agendaVeterinarioService,
                           PagamentoConsultaService pagamentoConsultaService) {
        this.tutorRepository = tutorRepository;
        this.petRepository = petRepository;
        this.consultaRepository = consultaRepository;
        this.agendaVeterinarioService = agendaVeterinarioService;
        this.pagamentoConsultaService = pagamentoConsultaService;
    }

    public void agendarConsulta(
            Long tutorId,
            Long petId,
            LocalDateTime dateTime,
            double valor
    ){
        Tutor tutor =  tutorRepository.buscarPorId(tutorId)
                .orElseThrow(() -> new RuntimeException("Tutor nao encontrado"));

        Pet pet =  petRepository.buscarPorId(tutorId)
                .orElseThrow(() -> new RuntimeException("Pet nao encontrado"));

        if(!pet.getTutorId().equals(tutor.getId())){
            throw new RuntimeException("O pet nao pertence ao tutor");
        }

        boolean veterinarioDisponivel = agendaVeterinarioService
                .veterinarioDisponivel(dateTime);

        if(!veterinarioDisponivel){
            throw new RuntimeException("Veterinario indisponivel");
        }

        boolean pagamentoAprovado =
                pagamentoConsultaService
                        .aprovarPagamento(tutor, valor);

        if(!pagamentoAprovado){
            throw new RuntimeException("Pagamento nao aprovado");
        }

         Consulta consulta = new Consulta(
                 null,
                 tutor,
                 pet,
                 dateTime,
                 valor
         );

        consultaRepository.salvar(consulta);






    }
}

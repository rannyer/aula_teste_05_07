import org.example.models.Pet;
import org.example.models.Tutor;
import org.example.repositories.fake.ConsultaRepositoryFake;
import org.example.repositories.fake.PetRepositoryFake;
import org.example.repositories.fake.TutorRepositoryFake;
import org.example.repositories.interfaces.ConsultaRepository;
import org.example.repositories.interfaces.PetRepository;
import org.example.repositories.interfaces.TutorRepository;
import org.example.services.AgendaVeterinarioService;
import org.example.services.ConsultaService;
import org.example.services.PagamentoConsultaService;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class ConsultaServiceTest {
    private TutorRepository tutorRepository;
    private PetRepository petRepository;
    private ConsultaRepository consultaRepository;

    private AgendaVeterinarioService agendaVeterinarioService;
    private PagamentoConsultaService pagamentoConsultaService;

    private ConsultaService consultaService;


    @BeforeEach
    void setup(){
        tutorRepository  = new TutorRepositoryFake();
        petRepository = new PetRepositoryFake();
        consultaRepository = new ConsultaRepositoryFake();

        agendaVeterinarioService =
                mock(AgendaVeterinarioService.class);
        pagamentoConsultaService =
                mock(PagamentoConsultaService.class);

        consultaService = new ConsultaService(
                tutorRepository,
                petRepository,
                consultaRepository,
                agendaVeterinarioService,
                pagamentoConsultaService
        );
    }

    @Test
    void deveAgendarConsultaComSucesso(){
        Tutor tutor = new Tutor(1L, "Maicon Microsoft");

        Pet pet =  new Pet(
                1L,
                "C-Shark",
                tutor.getId()
        );

        tutorRepository.salvar(tutor);
        petRepository.salvar(pet);

        LocalDateTime dataConsulta = LocalDateTime.of(2026, 05, 20, 14, 0);

        when(
                agendaVeterinarioService
                        .veterinarioDisponivel(dataConsulta)
        ).thenReturn(true);

        when(
                pagamentoConsultaService
                        .aprovarPagamento(tutor, 200.0)
        ).thenReturn(true);

        consultaService.agendarConsulta(
                tutor.getId(),
                pet.getId(),
                dataConsulta,
                200.0
        );

        assertEquals(1, consultaRepository.listarTodas().size());

        verify(agendaVeterinarioService, times(1))
                .veterinarioDisponivel(dataConsulta);

        verify(pagamentoConsultaService, times(1))
                .aprovarPagamento(tutor, 200.0);

    }
    @Test
    void naoDeveAgendarQuandoPagamentoForRecusado(){
        Tutor tutor = new Tutor(1L, "Maicon Microsoft");

        Pet pet =  new Pet(
                1L,
                "C-Shark",
                tutor.getId()
        );

        tutorRepository.salvar(tutor);
        petRepository.salvar(pet);

        LocalDateTime dataConsulta = LocalDateTime.of(2026, 05, 20, 14, 0);

        when(
                agendaVeterinarioService
                        .veterinarioDisponivel(dataConsulta)
        ).thenReturn(true);

        when(
                pagamentoConsultaService
                        .aprovarPagamento(tutor, 200.0)
        ).thenReturn(false);

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> {
                    consultaService.agendarConsulta(
                            tutor.getId(),
                            pet.getId(),
                            dataConsulta,
                            200.0
                    );
                });

        assertEquals("Pagamento nao aprovado", exception.getMessage());

        assertEquals(0, consultaRepository.listarTodas().size());

    }

    @Test
    void naoDeveAgendarQuandoPeteNaoPertenceAoTutor(){
        Tutor tutor =  new Tutor(1L, "Paula Python");

        Pet pet = new Pet(
                1L,
                "Django a tartaruga",
                999L
        );

        tutorRepository.salvar(tutor);
        petRepository.salvar(pet);

        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> {
                    consultaService.agendarConsulta(
                            tutor.getId(),
                            pet.getId(),
                            LocalDateTime.now(),
                            200.0
                    );
                });

        assertEquals("O pet nao pertence ao tutor", exception.getMessage());
        verify(agendaVeterinarioService, never())
                .veterinarioDisponivel(any());

        verify(pagamentoConsultaService, never())
                .aprovarPagamento(any(), anyDouble());

    }

    @Test
    void naoDeveAgendarQaundoTutorNaoExistir(){
        Pet pet =  new Pet(
                1L,
                "C-Shark",
                999L
        );
        petRepository.salvar(pet);
        RuntimeException exception =
                assertThrows(RuntimeException.class, () -> {
                    consultaService.agendarConsulta(
                            999L,
                            1L,
                            LocalDateTime.now(),
                            200.0
                    );
                });

        assertEquals("Tutor nao encontrado", exception.getMessage());

        verify(agendaVeterinarioService, never())
                .veterinarioDisponivel(any());

        verify(pagamentoConsultaService, never())
                .aprovarPagamento(any(), anyDouble());


        assertEquals(0, consultaRepository.listarTodas().size());
    }













}

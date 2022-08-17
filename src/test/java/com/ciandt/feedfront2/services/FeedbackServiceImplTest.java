package com.ciandt.feedfront2.services;

import com.ciandt.feedfront2.exceptions.BusinessException;
import com.ciandt.feedfront2.exceptions.EntidadeNaoEncontradaException;
import com.ciandt.feedfront2.models.Employee;
import com.ciandt.feedfront2.models.Feedback;
import com.ciandt.feedfront2.repositories.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceImplTest {
    private Employee autor;
    private Employee proprietario;
    private Feedback feedback;
    @Mock
    private FeedbackRepository feedbackRepository;
    @InjectMocks
    private FeedbackServiceImpl feedbackService;
    @Mock
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setup() throws BusinessException {

        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(LocalDate.now(), autor, proprietario, "descrição");

        autor.setId(1L);
        feedback.setId(1L);
        proprietario.setId(2L);

        when(employeeService.buscar(proprietario.getId())).thenReturn(proprietario);

        feedbackService.salvar(feedback);
    }

    @Test
    public void listar() {
        when(feedbackRepository.findAll()).thenReturn(List.of(feedback));

        List<Feedback> lista = assertDoesNotThrow(() -> feedbackService.listar());

        assertFalse(lista.isEmpty());
        assertTrue(lista.contains(feedback));
        assertEquals(1, lista.size());
    }

    @Test
    public void buscar() {
        long idInvalido = -1;
        long idValido = feedback.getId();

        when(feedbackRepository.findById(idValido)).thenReturn(Optional.of(feedback));
        when(feedbackRepository.findById(idInvalido)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> feedbackService.buscar(feedback.getId()));
        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> feedbackService.buscar(idInvalido));

        assertEquals("não foi possível encontrar o feedback", exception.getMessage());
    }

    @Test
    public void salvar() throws BusinessException {
        Employee employeeNaoSalvo = new Employee("miguel", "vitor", "m.vitor@email.com");
        employeeNaoSalvo.setId(-1L);

        Feedback feedbackValido1 = new Feedback(LocalDate.now(), autor, proprietario, "descrição");
        Feedback feedbackValido2 = new Feedback(LocalDate.now(), autor, proprietario, "descrição");

        Feedback feedbackInvalido1 = new Feedback(LocalDate.now(), null, null,"feedback sem autor e proprietario");
        Feedback feedbackInvalido2 = new Feedback(LocalDate.now(), null, employeeNaoSalvo,"feedback sem autor e proprietario");

        when(feedbackRepository.save(feedbackValido1)).thenReturn(feedbackValido1);
        when(feedbackRepository.save(feedbackValido2)).thenReturn(feedbackValido2);

        lenient().when(employeeService.buscar(employeeNaoSalvo.getId())).thenThrow(new EntidadeNaoEncontradaException("não foi possível encontrar o employee"));

        assertDoesNotThrow(() -> feedbackService.salvar(feedbackValido1));
        assertDoesNotThrow(() -> feedbackService.salvar(feedbackValido2));

        Exception exception1 = assertThrows(IllegalArgumentException.class,() -> feedbackService.salvar(feedbackInvalido1));
        Exception exception2 = assertThrows(IllegalArgumentException.class,() -> feedbackService.salvar(null));
        Exception exception3 = assertThrows(EntidadeNaoEncontradaException.class,() -> feedbackService.salvar(feedbackInvalido2));

        assertEquals("employee inválido", exception1.getMessage());
        assertEquals("feedback inválido", exception2.getMessage());
        assertEquals("não foi possível encontrar o employee", exception3.getMessage());
    }
}
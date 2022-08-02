package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.FeedbackDAO;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.models.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class FeedbackServiceTest {

    private final LocalDate localDate = LocalDate.now();
    private final String LOREM_IPSUM_FEEDBACK = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";
    private Feedback feedback;

    private Employee autor;

    private Employee proprietario;

    private FeedbackService service;

    private DAO<Feedback> feedbackDAO;

    private  Service<Employee> employeeService;

    @BeforeEach
    public void initEach() throws IOException, BusinessException {
        // Este trecho de código serve somente para limpar o repositório
        Files.walk(Paths.get("src/main/resources/data/feedback/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> {
                    new File(p.toString()).delete();
                });

        service = new FeedbackService();
        feedbackDAO = (DAO<Feedback>) Mockito.mock(DAO.class);
        employeeService = Mockito.mock(EmployeeService.class);
        service.setDAO(feedbackDAO);
        service.setEmployeeService(employeeService);

        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        when(employeeService.buscar(proprietario.getId())).thenReturn(proprietario);

        service.salvar(feedback);
    }

    @Test
    public void listar() throws IOException {
        when(feedbackDAO.listar()).thenReturn(Arrays.asList(feedback));
        List<Feedback> lista = assertDoesNotThrow(() -> service.listar());

        assertFalse(lista.isEmpty());
        assertTrue(lista.contains(feedback));
        assertEquals(1, lista.size());
    }

    @Test
    public void salvar() throws IOException, BusinessException, ComprimentoInvalidoException, EmployeeNaoEncontradoException {
        Employee employeeNaoSalvo = new Employee("miguel", "vitor", "m.vitor@email.com");

        Feedback feedbackValido1 = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);
        Feedback feedbackValido2 = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        Feedback feedbackInvalido1 = new Feedback(localDate, null, null,"feedback sem autor e proprietario");
        Feedback feedbackInvalido2 = new Feedback(localDate, null, employeeNaoSalvo,"feedback sem autor e proprietario");

        assertDoesNotThrow(() -> service.salvar(feedbackValido1));
        assertDoesNotThrow(() -> service.salvar(feedbackValido2));

        when(employeeService.buscar(feedbackInvalido2.getProprietario().getId())).thenReturn(null);

        Exception exception1 = assertThrows(IllegalArgumentException.class,() -> service.salvar(feedbackInvalido1));
        Exception exception2 = assertThrows(IllegalArgumentException.class,() -> service.salvar(null));
        Exception exception3 = assertThrows(EntidadeNaoEncontradaException.class,() -> service.salvar(feedbackInvalido2));

        assertEquals("employee inválido", exception1.getMessage());
        assertEquals("feedback inválido", exception2.getMessage());
        assertEquals("não foi possível encontrar o employee", exception3.getMessage());
    }

    @Test
    public void buscar() throws IOException, EmployeeNaoEncontradoException {
        Feedback feedbackNaoSalvo = new Feedback(localDate, autor, proprietario, "tt");

        when(feedbackDAO.buscar(feedback.getId())).thenReturn(feedback);

        assertDoesNotThrow(() -> service.buscar(feedback.getId()));

        when(feedbackDAO.buscar(feedbackNaoSalvo.getId())).thenThrow(FileNotFoundException.class);

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> service.buscar(feedbackNaoSalvo.getId()));

        assertEquals("não foi possível encontrar o feedback", exception.getMessage());
    }

}

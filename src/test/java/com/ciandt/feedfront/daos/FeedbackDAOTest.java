package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackDAOTest {

    private Feedback feedback;

    private DAO<Feedback> dao;

    private final LocalDate localDate = LocalDate.now();

    private final String LOREM_IPSUM_FEEDBACK = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";

    private Employee autor;

    private Employee proprietario;

    @BeforeEach
    public void initEach() throws IOException, ComprimentoInvalidoException {
        // Este trecho de código serve somente para limpar o repositório
        Files.walk(Paths.get("src/main/resources/data/employee/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> {
                    new File(p.toString()).delete();
                });

        Files.walk(Paths.get("src/main/resources/data/feedback/"))
                .filter(p -> p.toString().endsWith(".byte"))
                .forEach(p -> {
                    new File(p.toString()).delete();
                });

        dao = new FeedbackDAO();

        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        dao.salvar(feedback);
    }

    @Test
    void listar() throws IOException {
        List<Feedback> result = dao.listar();
        assertFalse(result.isEmpty());
    }

    @Test
    void buscar() {
        String idValido = feedback.getId();
        String idInvalido = UUID.randomUUID().toString();

        Feedback feedbackSalvo = assertDoesNotThrow(() -> dao.buscar(idValido));
        assertThrows(IOException.class, () -> dao.buscar(idInvalido));

        assertEquals(feedbackSalvo.getId(), feedback.getId());
    }

    @Test
    void salvar() throws ComprimentoInvalidoException {
        Employee autor = new Employee("João", "Silveira", "j.silveira@email.com");
        Employee proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        Feedback feedbackSemMelhoria = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);
        Feedback feedbackComMelhoria = new Feedback(
                localDate,
                autor, proprietario,
                LOREM_IPSUM_FEEDBACK,
                "pode melhorar isso",
                "melhorar desta forma");

        Feedback feedbackSalvoSemMelhoria = assertDoesNotThrow(() -> dao.salvar(feedbackSemMelhoria));
        Feedback feedbackSalvoComMelhoria = assertDoesNotThrow(() -> dao.salvar(feedbackComMelhoria));

        assertEquals(feedbackSalvoSemMelhoria, feedbackSemMelhoria);
        assertEquals(feedbackSalvoComMelhoria, feedbackComMelhoria);
    }
}
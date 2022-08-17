package com.ciandt.feedfront2.controllers;

import com.ciandt.feedfront2.exceptions.BusinessException;
import com.ciandt.feedfront2.models.Employee;
import com.ciandt.feedfront2.models.Feedback;
import com.ciandt.feedfront2.services.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackControllerTest {
    private Feedback feedback;
    private Employee autor;
    private Employee proprietario;

    @InjectMocks
    private FeedbackController controller;
    @Mock
    private FeedbackService feedbackService;

    @BeforeEach
    public void setup() throws BusinessException {

        autor = new Employee("João", "Silveira", "j.silveira@email.com");
        proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        feedback = new Feedback(LocalDate.now(), autor, proprietario,"Agradeco muito pelo apoio feito pelo colega!");//construtor 1
        feedback.setId(1L);

        when(feedbackService.salvar(feedback)).thenReturn(feedback);
        controller.salvar(feedback);
    }
    @Test
    public void listar(){
        when(feedbackService.listar()).thenReturn(List.of(feedback));

        Collection<Feedback> listaFeedback = assertDoesNotThrow(controller::listar).getBody();
        assertEquals(1, listaFeedback.size());

    }

    @Test
    public void buscar() throws BusinessException {


        long id = feedback.getId();

        when(feedbackService.buscar(id)).thenReturn(feedback);

        Feedback feedbackSalvo = assertDoesNotThrow(() -> controller.buscar(id).getBody());

        assertEquals(feedback, feedbackSalvo);

    }

    @Test
    public void salvar() throws BusinessException {
        Feedback novoFeedback = new Feedback(LocalDate.now(), null, proprietario, "novo");

        when(feedbackService.salvar(novoFeedback)).thenReturn(novoFeedback);

        Feedback feedbackSalvo = controller.salvar(novoFeedback).getBody();

        assertEquals(novoFeedback, feedbackSalvo);
    }

}
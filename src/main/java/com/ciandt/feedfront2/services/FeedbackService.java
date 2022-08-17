package com.ciandt.feedfront2.services;

import com.ciandt.feedfront2.exceptions.BusinessException;
import com.ciandt.feedfront2.models.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> listar();

    Feedback buscar(long id) throws BusinessException;

    Feedback salvar(Feedback e) throws BusinessException, IllegalArgumentException;
}
package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.services.FeedbackService;

import java.util.List;

public class FeedbackController {

    private Service<Feedback> service;

    public FeedbackController() {
        this.service = new FeedbackService();
    }

    public List<Feedback> listar() {
        return service.listar();
    }

    public Feedback buscar(long id) throws BusinessException {
        return service.buscar(id);
    }

    public Feedback salvar(Feedback feedback) throws BusinessException {
        return service.salvar(feedback);
    }

    public Feedback atualizar(Feedback feedback) throws BusinessException {
        throw new UnsupportedOperationException();
    }

    public void apagar(long id) throws BusinessException {
        service.apagar(id);
    }

    public void setService(Service<Feedback> service) {
        this.service = service;
    }

}

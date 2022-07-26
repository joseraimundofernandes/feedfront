package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.models.Feedback;

import java.io.IOException;
import java.util.List;

public class FeedbackService implements Service<Feedback> {
    @Override
    public List<Feedback> listar() throws ArquivoException {
        return null;
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, BusinessException {
        return null;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        return null;
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        return null;
    }

    @Override
    public void apagar(String id) throws IOException, BusinessException, EmployeeNaoEncontradoException {

    }

    @Override
    public void setDAO(DAO<Feedback> dao) {

    }
}

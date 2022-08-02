package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;

import java.io.IOException;
import java.util.List;

public class FeedbackService implements Service<Feedback> {
    private DAO<Feedback> dao;
    private Service<Employee> employeeService;
    public FeedbackService() {
        setDAO(dao);
        setEmployeeService(employeeService);
    }
    @Override
    public List<Feedback> listar() throws ArquivoException {
        List<Feedback> feedbacks;

        try {
            feedbacks = dao.listar();
        } catch (IOException e) {
            throw new ArquivoException("erro ao processar arquivos");
        }

        return feedbacks;
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, BusinessException {
        Feedback feedback;

        try {
            feedback = dao.buscar(id);
        } catch (IOException | EmployeeNaoEncontradoException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o feedback");
        }

        return feedback;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws IOException, BusinessException, IllegalArgumentException {
        Feedback newFeedback;

        try {
            if (feedback == null) {
                throw new IllegalArgumentException("feedback inválido");
            } else if (feedback.getProprietario() == null) {
                throw new IllegalArgumentException("employee inválido");
            } else if (employeeService.buscar(feedback.getProprietario().getId()) == null) {
                throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
            }

            newFeedback = dao.salvar(feedback);

        } catch (IOException e) {
            throw new ArquivoException("erro ao processar arquivos");
        }

        return newFeedback;
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
        this.dao = dao;
    }

    public void setEmployeeService(Service<Employee> employeeService) {
        this.employeeService = employeeService;
    }
}

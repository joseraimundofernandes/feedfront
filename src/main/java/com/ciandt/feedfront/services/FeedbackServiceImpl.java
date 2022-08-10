package com.ciandt.feedfront.services;



import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.exceptions.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

//TODO: IMPLEMENTE AS CLASSES E MAPEIE A CLASSE PARA O SPRINGBOOT
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedBackRepository;
    private final EmployeeService employeeService;
    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedBackRepository, EmployeeService employeeService) {
        this.feedBackRepository = feedBackRepository;
        this.employeeService = employeeService;
    }

    @Override
    public List<Feedback> listar() {
        return feedBackRepository.findAll();
    }

    @Override
    public Feedback buscar(long id) throws BusinessException {

        var result = feedBackRepository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("não foi possível encontrar o feedback"));

        return result;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws BusinessException, IllegalArgumentException {

        if (feedback == null) {
            throw new IllegalArgumentException("feedback inválido");
        } else if (feedback.getProprietario() == null) {
            throw new IllegalArgumentException("employee inválido");
        } else if (employeeService.buscar(feedback.getProprietario().getId()) == null) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        } else {
            return feedBackRepository.save(feedback);
        }
    }

}

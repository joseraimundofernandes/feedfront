package com.ciandt.feedfront2.controllers;

import com.ciandt.feedfront2.exceptions.BusinessException;
import com.ciandt.feedfront2.models.Feedback;
import com.ciandt.feedfront2.services.FeedbackService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//TODO: APLIQUE AS ANOTAÇÕES NECESSÁRIAS PARA QUE O PROGRAMA RECONHEÇA AS DIFERENTES CAMADAS COMO @SERVICE, @RESTCONTROLLER. NÃO ESQUEÇA DAS INJEÇÕES DE DEPENDENCIA COM O @AUTOWIRED
//TODO: APLIQUE AS ANOTAÇÕES DO SWAGGER CONFORME O EXEMPLO @ApiOperation
@RestController
@RequestMapping("/v1/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @ApiOperation(value = "Este retorna todos os feedbacks enviados pelos usuários no banco de dados.")
    @GetMapping
    public ResponseEntity<List<Feedback>> listar() {
        return new ResponseEntity<>(feedbackService.listar(), HttpStatus.OK);
    }

    @ApiOperation(value = "Este busca o feedback pelo id.")
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> buscar(@PathVariable long id) throws BusinessException {
        return new ResponseEntity<>(feedbackService.buscar(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Este salva o feedback.")
    @PostMapping
    public ResponseEntity<Feedback> salvar(@RequestBody Feedback feedback) throws BusinessException {
        return new ResponseEntity<>(feedbackService.salvar(feedback), HttpStatus.CREATED);
    }
}

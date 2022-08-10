package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.services.EmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: APLIQUE AS ANOTAÇÕES NECESSÁRIAS PARA QUE O PROGRAMA RECONHEÇA AS DIFERENTES CAMADAS COMO @SERVICE, @RESTCONTROLLER. NÃO ESQUEÇA DAS INJEÇÕES DE DEPENDENCIA COM O @AUTOWIRED
//TODO: APLIQUE AS ANOTAÇÕES DO SWAGGER CONFORME O EXEMPLO @ApiOperation EM FEEDBACKCONTROLLER.
@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Utilize o exemplo de salvar na classe FeedbackController para implementar os métodos:
    @ApiOperation(value = "Este retorna todos os employees.")
    @GetMapping
    public ResponseEntity<List<Employee>> listar()  {
        return new ResponseEntity<>(employeeService.listar(), HttpStatus.OK);
    }

    @ApiOperation(value = "Este busca o employee pelo id.")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> buscar(long id) throws BusinessException {
        return new ResponseEntity<>(employeeService.buscar(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Este salva o employee.")
    @PostMapping
    public ResponseEntity<Employee> salvar(Employee employee) throws BusinessException {
        return new ResponseEntity<>(employeeService.salvar(employee), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Este deleta o employee pelo id.")
    @DeleteMapping
    public ResponseEntity apagar(long id) throws BusinessException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "Este atualiza o employee.")
    @PutMapping
    public ResponseEntity<Employee> atualizar (Employee employee) throws BusinessException {
        return new ResponseEntity<>(employeeService.salvar(employee), HttpStatus.OK);
    }
}
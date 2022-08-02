package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.services.EmployeeService;

import java.util.List;

public class EmployeeController {
    private Service<Employee> service;

    public EmployeeController() {
        this.service = new EmployeeService();
    }

    public List<Employee> listar() {
        return service.listar();
    }

    public Employee buscar(long id) throws BusinessException {
        return service.buscar(id);
    }

    public Employee salvar(Employee employee) throws BusinessException {
        return service.salvar(employee);
    }

    public Employee atualizar(Employee employee) throws BusinessException {
        return service.atualizar(employee);
    }

    public void apagar(long id) throws BusinessException {
        service.apagar(id);
    }

    public void setService(Service<Employee> service) {
        this.service = service;
    }
}

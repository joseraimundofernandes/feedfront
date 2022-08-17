package com.ciandt.feedfront2.services;

import com.ciandt.feedfront2.exceptions.BusinessException;
import com.ciandt.feedfront2.models.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> listar();

    Employee buscar(long id) throws BusinessException;

    Employee salvar(Employee e) throws BusinessException, IllegalArgumentException;

    Employee atualizar(Employee e) throws BusinessException, IllegalArgumentException;

    void apagar(long id) throws BusinessException;
}

package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;

import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() throws ArquivoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        throw new UnsupportedOperationException();
    }
}

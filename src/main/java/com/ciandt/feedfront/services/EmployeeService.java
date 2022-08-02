package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        this.setDAO(dao);
    }

    @Override
    public List<Employee> listar() throws ArquivoException {
        List<Employee> employees;

        try {
            employees = dao.listar();
        } catch (IOException e) {
            throw new ArquivoException("erro ao processar arquivos");
        }

        return employees;
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, BusinessException {
        Employee employee;

        try {
            employee = dao.buscar(id);
        } catch (IOException | EmployeeNaoEncontradoException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        }

        return employee;
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException {
        Employee newEmployee;

        try {
            boolean isEmailExistente = dao.isEmailExistente(employee);

            if (isEmailExistente) {
                throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
            } else if (employee == null) {
                throw new IllegalArgumentException("employee inválido");
            }

            newEmployee = dao.salvar(employee);

        } catch (IOException e) {
            throw new ArquivoException("erro ao processar arquivos");
        }

        return newEmployee;
    }

    @Override
    public Employee atualizar(Employee employee) throws IOException, BusinessException {
        try {
            buscar(employee.getId());

            boolean isEmailExistente = dao.isEmailExistente(employee);

            if (isEmailExistente) {
                throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
            } else if (employee == null) {
                throw new IllegalArgumentException("employee inválido");
            }
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        }

        return salvar(employee);
    }

    @Override
    public void apagar(String id) throws IOException, BusinessException, EmployeeNaoEncontradoException {
        try {
            dao.apagar(id);

        } catch (FileNotFoundException e) {
            throw new EntidadeNaoEncontradaException("Employee não encontrado");
        }
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}

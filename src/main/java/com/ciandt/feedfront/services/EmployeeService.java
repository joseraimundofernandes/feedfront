package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.BusinessException;

import java.util.List;
import java.util.Optional;

public class EmployeeService implements Service<Employee> {
    private EmployeeDAO dao;

    public EmployeeService() {
        this.dao = new EmployeeDAO();
    }

    @Override
    public List<Employee> listar() {
        return dao.listar();
    }

    @Override
    public Employee buscar(long id) throws BusinessException {

        Optional<Employee> employee = dao.buscar(id);

        if (employee.isPresent()) {
            return employee.get();
        }

        throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
    }

    @Override
    public Employee salvar(Employee employee) throws BusinessException {

        boolean isEmailExistente = dao.checkEmailDuplication(employee.getEmail());

        if (isEmailExistente) {
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        } else if (employee == null) {
            throw new IllegalArgumentException("employee inválido");
        }

        Employee newEmployee = dao.salvar(employee);

        return  newEmployee;
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {

        if (employee == null) {
            throw new IllegalArgumentException("employee inválido");
        } else if (employee.getId() == null)
            throw new IllegalArgumentException("employee inválido: não possui ID");

        String emailAntigo = dao.buscar(employee.getId()).get().getEmail();

        if (!emailAntigo.equals(employee.getEmail())) {

            boolean isEmailExistente = dao.checkEmailDuplication(employee.getEmail());

            if (isEmailExistente) {
                throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
            }
        }

        Employee newEmployee = dao.salvar(employee);

        return  newEmployee;
    }

    @Override
    public void apagar(long id) throws BusinessException {
        boolean isApagado = dao.apagar(id);
        if (!isApagado)
            throw new EntidadeNaoEncontradaException("Employee não encontrado");
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = (EmployeeDAO) dao;
    }
}

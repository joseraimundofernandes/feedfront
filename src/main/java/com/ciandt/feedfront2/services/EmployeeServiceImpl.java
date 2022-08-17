package com.ciandt.feedfront2.services;

import com.ciandt.feedfront2.exceptions.BusinessException;
import com.ciandt.feedfront2.exceptions.EmailInvalidoException;
import com.ciandt.feedfront2.exceptions.EntidadeNaoEncontradaException;
import com.ciandt.feedfront2.models.Employee;
import com.ciandt.feedfront2.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;

//TODO: IMPLEMENTE AS CLASSES E MAPEIE A CLASSE PARA O SPRINGBOOT
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> listar() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee buscar(long id) throws BusinessException {

        var result = employeeRepository.findById(id).
                orElseThrow(() -> new EntidadeNaoEncontradaException("não foi possível encontrar o employee"));

        return result;
    }

    @Override
    public Employee salvar(Employee employee) throws BusinessException {
        Employee employeeWithSameEmail = null;
        
        if (employee != null) {
            employeeWithSameEmail = employeeRepository.findByEmail(employee.getEmail());
        }

        if(employee == null) {
            throw new IllegalArgumentException("employee inválido");
        } else if (employeeWithSameEmail != null) {
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }else {
            try {
                return employeeRepository.save(employee);
            } catch (PersistenceException ex) {
                throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
            }
        }
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {
        String emailAntigo;

        if (employee == null) {
            throw new IllegalArgumentException("employee inválido");
        }

        if (employee.getId() != null) {
            emailAntigo = employeeRepository.findById(employee.getId()).get().getEmail();
        } else {
            throw new IllegalArgumentException("employee inválido: não possui ID");
        }

        if (!emailAntigo.equals(employee.getEmail())) {

            Employee employee2 = employeeRepository.findByEmail(employee.getEmail());

            if (employee2 != null) {
                throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
            }
        }

        buscar(employee.getId());

        return salvar(employee);
    }

    @Override
    public void apagar(long id) throws BusinessException {

        buscar(id);

        employeeRepository.deleteById(id);
    }
}
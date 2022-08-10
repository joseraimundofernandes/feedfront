package com.ciandt.feedfront.services;

import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.exceptions.EmailInvalidoException;
import com.ciandt.feedfront.exceptions.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        var email = employeeRepository.findByEmail(employee.getEmail());

        if (email == null) {
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        } else if(employee == null) {
            throw new IllegalArgumentException("employee inválido");
        } else {
            return employeeRepository.save(employee);
        }
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {

        if (employee == null) {
            throw new IllegalArgumentException("employee inválido");
        }

        String emailAntigo = employeeRepository.findById(employee.getId()).get().getEmail();

        if (!emailAntigo.equals(employee.getEmail())) {

            String email = employeeRepository.findByEmail(employee.getEmail());

            if (email != null) {
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
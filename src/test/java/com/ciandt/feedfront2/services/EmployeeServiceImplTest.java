package com.ciandt.feedfront2.services;

import com.ciandt.feedfront2.exceptions.BusinessException;
import com.ciandt.feedfront2.exceptions.ComprimentoInvalidoException;
import com.ciandt.feedfront2.exceptions.EmailInvalidoException;
import com.ciandt.feedfront2.exceptions.EntidadeNaoEncontradaException;
import com.ciandt.feedfront2.models.Employee;
import com.ciandt.feedfront2.repositories.EmployeeRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// O Service deve ser capaz de trabalhar em conjunto com o DAO para executar as tarefas
// Sempre valendo-se das regras de negócio

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    private Employee employee;
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    // essa exception é apenas um Mock...
    private final ConstraintViolationException constraintViolationException = new ConstraintViolationException("string", new SQLException(), "string");

    @BeforeEach
    public void setup() throws BusinessException {
        employee = new Employee("João", "Silveira", "j.silveira@email.com");
        employee.setId(1L);

        employeeService.salvar(employee);
    }

    @Test
    public void listar() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));

        List<Employee> employees = employeeService.listar();

        assertFalse(employees.isEmpty());
        assertTrue(employees.contains(employee));
        assertEquals(1, employees.size());
    }

    // Nota: esses dois métodos estão testando o "buscar" do service
    // Mas estão separados para reforçar a independência dos testes como manda o padrão FIRST: https://hackernoon.com/test-f-i-r-s-t-65e42f3adc17
    @Test
    public void buscarMalSucedida() {
        long id = -1;

        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntidadeNaoEncontradaException.class, () -> employeeService.buscar(id));
        assertEquals("não foi possível encontrar o employee", exception.getMessage());
    }

    @Test
    public void buscarBemSucedida() {
        long id = 1;

        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeService.buscar(id));

        assertEquals(employeeSalvo, employee);
    }

    @Test
    public void salvar() throws ComprimentoInvalidoException {
        Employee employeeValido = new Employee("João", "Silveira", "joao.silveira@email.com");
        Employee employeeInvalido = new Employee("José", "Silveira", "j.silveira@email.com");

        when(employeeRepository.save(employeeValido)).thenReturn(employeeValido);
        when(employeeRepository.save(employeeInvalido)).thenThrow(new PersistenceException(constraintViolationException));

        assertDoesNotThrow(() -> employeeService.salvar(employeeValido));

        Exception exception1 = assertThrows(EmailInvalidoException.class, () -> employeeService.salvar(employeeInvalido));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> employeeService.salvar(null));

        assertEquals("já existe um employee cadastrado com esse e-mail", exception1.getMessage());
        assertEquals("employee inválido", exception2.getMessage());
    }

    @Test
    public void atualizacaoBemSucedida() {
        employee.setEmail("joao.silveira@email.com");

        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        Employee employeeSalvo = assertDoesNotThrow(() -> employeeService.atualizar(employee));

        assertEquals(employee, employeeSalvo);
    }

    @Test
    public void atualizacaoMalSucedida() throws BusinessException {
        Employee employee2 = new Employee("Bruno", "Silveira", "b.silveira@email.com");
        Employee employee3 = new Employee("Maria", "Silveira", "b.silveira@email.com");

        employee2.setId(10L);

        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeRepository.save(employee2)).thenReturn(employee2);
        when(employeeRepository.findById(employee2.getId())).thenReturn(Optional.of(employee2));

        employeeService.salvar(employee);
        employeeService.salvar(employee2);

        employee.setEmail("j.silveira@email.com");

        when(employeeRepository.save(employee2)).thenThrow(new PersistenceException(constraintViolationException));

        employee2.setNome("Jean");
        employee2.setEmail("joao.silveira@email.com");

        assertThrows(IllegalArgumentException.class, () -> employeeService.atualizar(null));
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> employeeService.atualizar(employee3));
        Exception exception2 = assertThrows(EmailInvalidoException.class, () -> employeeService.atualizar(employee2));

        assertEquals("employee inválido: não possui ID", exception1.getMessage());
        assertEquals("já existe um employee cadastrado com esse e-mail", exception2.getMessage());
    }

    @Test
    public void apagar() {
        long idInvalido = -1;
        long idValido = employee.getId();

        when(employeeRepository.findById(idInvalido)).thenReturn(Optional.empty());
        when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));

        assertDoesNotThrow(() -> employeeService.apagar(idValido));

        assertThrows(EntidadeNaoEncontradaException.class, () -> employeeService.apagar(idInvalido));
    }
}
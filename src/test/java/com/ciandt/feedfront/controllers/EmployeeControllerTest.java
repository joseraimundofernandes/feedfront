//package com.ciandt.feedfront.controllers;
//
//import com.ciandt.feedfront.exceptions.BusinessException;
//import com.ciandt.feedfront.models.Employee;
//import com.ciandt.feedfront.services.EmployeeService;
//import com.ciandt.feedfront.services.EmployeeServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collection;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.lenient;
//import static org.mockito.Mockito.when;
//
//// A função do controller é tratar as solicitações.
//// Ser capaz de levar o "pedido" ao "cozinheiro" e me trazer o "prato"
//@ExtendWith(MockitoExtension.class)
//public class EmployeeControllerTest {
//    private Employee employee;
//    @InjectMocks
//    private EmployeeController employeeController;
//    @Mock
//    private EmployeeServiceImpl employeeService;
//
//    @BeforeEach
//    public void setup() throws BusinessException {
//        employee = new Employee("João", "Silveira", "j.silveira@email.com");
//        employee.setId(1L);
//
//        when(employeeService.salvar(employee)).thenReturn(employee);
//        employeeController.salvar(employee);
//    }
//
//    @Test
//    public void listar() {
//        when(employeeService.listar()).thenReturn(List.of(employee));
//        Collection<Employee> employees = assertDoesNotThrow(employeeController::listar).getBody();
//
//        assertEquals(1, employees.size());
//    }
//
//    @Test
//    public void buscar() throws BusinessException {
//        long id = employee.getId();
//        when(employeeService.buscar(id)).thenReturn(employee);
//
//        Employee employeeSalvo = assertDoesNotThrow(() -> employeeController.buscar(id)).getBody();
//
//        assertEquals(employee, employeeSalvo);
//    }
//
//    @Test
//    public void salvar() throws BusinessException {
//        Employee novoEmployee = new Employee("Cristiano", "Halland", "fifa@email.com");
//
//        when(employeeService.salvar(novoEmployee)).thenReturn(novoEmployee);
//
//        Employee employeeSalvo = assertDoesNotThrow(() -> employeeController.salvar(novoEmployee)).getBody();
//
//        assertEquals(novoEmployee, employeeSalvo);
//    }
//
//    @Test
//    public void atualizar() throws BusinessException {
//        long id = employee.getId();
//        employee.setEmail("joao.silveira@email.com");
//
//        lenient().when(employeeService.buscar(id)).thenReturn(employee);
//        when(employeeService.atualizar(employee)).thenReturn(employee);
//
//        Employee employeeAtualizado = assertDoesNotThrow(() -> employeeController.atualizar(employee)).getBody();
//
//        assertEquals(employee, employeeAtualizado);
//    }
//
//    @Test
//    public void apagar() throws BusinessException {
//        long id = employee.getId();
//        lenient().when(employeeService.buscar(id)).thenReturn(employee);
//
//        assertDoesNotThrow(() -> employeeController.apagar(id));
//    }
//}

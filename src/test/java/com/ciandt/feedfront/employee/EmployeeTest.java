package com.ciandt.feedfront.employee;


import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class EmployeeTest {

    //@Mock private FileRepo fileRepo;
    public Employee employee1;
    public Employee employee2;
    @BeforeEach
    public void initEach() throws ComprimentoInvalidoException {
        employee1 = new Employee("Jose", "Silveira", "j.silveira@email.com");
        employee2 = null;
    }

    @Test
    public void shouldSalvarEmployeeTest() throws EmailInvalidoException, IOException, ComprimentoInvalidoException, ClassNotFoundException {
        employee2 = new Employee("João", "Silveira", "j.silveira@email.com");
        Employee result = Employee.salvarEmployee(employee2);
        //Employee result2 = Employee.salvarEmployee(employee2);
        Employee.listarEmployees();
        assertEquals(employee2, result);
    }

    @Test
    public void salvarEmployeeTest() throws ComprimentoInvalidoException, EmailInvalidoException, IOException, EmployeeNaoEncontradoException {
        employee2 = new Employee("João", "Silveira", "j.silveira@email.com");

        Employee.salvarEmployee(employee1);

        Exception emailException = assertThrows(EmailInvalidoException.class, () -> {
           Employee.salvarEmployee(employee2);
        });

        String mensagemEsperada = "E-mail ja cadastrado no repositorio";
        String mensagemRecebida = emailException.getMessage();

        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    public void listarEmployees() throws ArquivoException {
        List<Employee> employees = Employee.listarEmployees();
        System.out.println(employees);
        assertTrue(employees.isEmpty() == false);
        assertTrue(employees.size() == 1);
    }

    @Test
    public void buscarEmployee() throws ArquivoException, EmployeeNaoEncontradoException{
        //Employee retornoDePesquisa = Employee.buscarEmployee(employee1.getId());
        Employee retornoDePesquisa = Employee.buscarEmployee("67c1cd46-6a76-412f-a899-5edf2f110e24");

        assertEquals(retornoDePesquisa, employee1);

        Exception employeeNaoEncontradoException = assertThrows(EmployeeNaoEncontradoException.class, () -> {
                Employee.buscarEmployee(UUID.randomUUID().toString());
        });

        assertEquals(employeeNaoEncontradoException.getMessage(), "Employee não encontrado");
    }

    @Test
    public void atualizarEmployee() throws ComprimentoInvalidoException, EmployeeNaoEncontradoException, ArquivoException, EmailInvalidoException {
        String sobrenome = "Roberto";
        employee1.setSobrenome(sobrenome);

        String sobrenomeSalvo = Employee.buscarEmployee(employee1.getId()).getSobrenome();
        assertNotEquals(sobrenomeSalvo, sobrenome);

        Employee.atualizarEmployee(employee1);

        sobrenomeSalvo = Employee.buscarEmployee(employee1.getId()).getSobrenome();
        assertEquals(sobrenomeSalvo, sobrenome);
    }

    @Test
    public void apagarEmployee() throws ArquivoException, EmployeeNaoEncontradoException {
        //String id = employee1.getId();
        String id = "67c1cd46-6a76-412f-a899-5edf2f110e24";
        Employee.apagarEmployee(id);

        Exception employeeNaoEncontradoException =  assertThrows(EmployeeNaoEncontradoException.class, () -> {
            Employee.buscarEmployee(id);
        });

        String mensagemRecebida = employeeNaoEncontradoException.getMessage();
        String mensagemEsperada = "Employee não existe no repositório";
        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    public void nomeDeveTerComprimentoMaiorQueDois() {
        Exception comprimentoInvalidoException = assertThrows(ComprimentoInvalidoException.class, () ->
        employee1 = new Employee("Ze", "Juvenil", "z.juvenil@ciandt.com")
        );

        String mensagemEsperada = "Comprimento do nome deve ser maior que 2 caracteres.";
        String mensagemRecebida = comprimentoInvalidoException.getMessage();
        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    public void sobrenomeDeveTerComprimentoMaiorQueDois() {
        Exception comprimentoInvalidoException = assertThrows(ComprimentoInvalidoException.class, () ->
        employee1 = new Employee("Joao", "ao", "z.juvenil@ciandt.com")
        );

        String mensagemEsperada = "Comprimento do sobrenome deve ser maior que 2 caracteres.";
        String mensagemRecebida = comprimentoInvalidoException.getMessage();
        assertEquals(mensagemEsperada, mensagemRecebida);

    }
}

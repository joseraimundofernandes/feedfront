package com.ciandt.feedfront.employee;


import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class EmployeeTest {

    //@Mock private FileRepo fileRepo;
    public static Employee employee1;
    public static Employee employee2;
    @BeforeAll
    public static void init() throws ComprimentoInvalidoException {
        employee1 = new Employee("Jose", "Silveira", "j.silveira@email.com");
        employee2 = null;
    }
    @Test
    @Order(1)
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
    @Order(2)
    public void listarEmployees() throws ArquivoException {
        List<Employee> employees = Employee.listarEmployees();
        System.out.println(employees);
        assertTrue(employees.isEmpty() == false);
        assertTrue(employees.size() == 1);
    }

    @Test
    @Order(3)
    public void buscarEmployee() throws ArquivoException, EmployeeNaoEncontradoException{
        Employee retornoDePesquisa = Employee.buscarEmployee(employee1.getId());

        assertEquals(employee1.getId(), retornoDePesquisa.getId());

        Exception employeeNaoEncontradoException = assertThrows(EmployeeNaoEncontradoException.class, () -> {
                Employee.buscarEmployee(UUID.randomUUID().toString());
        });
        assertEquals(employeeNaoEncontradoException.getMessage(), "Employee não encontrado");
    }

    @Test
    @Order(4)
    public void atualizarEmployee() throws ComprimentoInvalidoException, EmployeeNaoEncontradoException, ArquivoException, EmailInvalidoException {
        String sobrenome = "Roberto";
        employee1.setSobrenome(sobrenome);

        String sobrenomeSalvo = Employee.buscarEmployee(employee1.getId()).getSobrenome();
        assertNotEquals(sobrenomeSalvo, sobrenome);

        Employee.atualizarEmployee(employee1);

        sobrenomeSalvo = Employee.buscarEmployee(employee1.getId()).getSobrenome();
        System.out.println(sobrenomeSalvo);
        assertEquals(sobrenomeSalvo, sobrenome);
    }

    @Test
    @Order(5)
    public void apagarEmployee() throws ArquivoException, EmployeeNaoEncontradoException {
        String id = employee1.getId();
        Employee.apagarEmployee(id);

        Exception employeeNaoEncontradoException =  assertThrows(EmployeeNaoEncontradoException.class, () -> {
            Employee.buscarEmployee(id);
        });

        String mensagemRecebida = employeeNaoEncontradoException.getMessage();
        String mensagemEsperada = "Employee não encontrado";
        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    @Order(6)
    public void nomeDeveTerComprimentoMaiorQueDois() {
        Exception comprimentoInvalidoException = assertThrows(ComprimentoInvalidoException.class, () ->
        employee1 = new Employee("Ze", "Juvenil", "z.juvenil@ciandt.com")
        );

        String mensagemEsperada = "Comprimento do nome deve ser maior que 2 caracteres.";
        String mensagemRecebida = comprimentoInvalidoException.getMessage();
        assertEquals(mensagemEsperada, mensagemRecebida);
    }

    @Test
    @Order(7)
    public void sobrenomeDeveTerComprimentoMaiorQueDois() {
        Exception comprimentoInvalidoException = assertThrows(ComprimentoInvalidoException.class, () ->
        employee1 = new Employee("Joao", "ao", "z.juvenil@ciandt.com")
        );

        String mensagemEsperada = "Comprimento do sobrenome deve ser maior que 2 caracteres.";
        String mensagemRecebida = comprimentoInvalidoException.getMessage();
        assertEquals(mensagemEsperada, mensagemRecebida);

    }
}

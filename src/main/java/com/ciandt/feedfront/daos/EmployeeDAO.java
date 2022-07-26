package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ciandt.feedfront.models.Employee.getInputStream;
import static com.ciandt.feedfront.models.Employee.getOutputStream;

public class EmployeeDAO implements DAO<Employee> {
    private String repositorioPath = Employee.repositorioPath;

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() throws IOException, EntidadeNaoSerializavelException {
        List<Employee> employees = new ArrayList<>();

        try {
            Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());

            for (String file: files) {
                employees.add(buscar(file));
            }

            paths.close();

        } catch (IOException | EmployeeNaoEncontradoException e) {
            throw new ArquivoException("erro ao processar arquivos");
        }

        return employees;
    }

    @Override
    public Employee buscar(String id) throws IOException, EntidadeNaoSerializavelException, EmployeeNaoEncontradoException {
        Employee employee;
        ObjectInputStream inputStream;

        try {
            inputStream = getInputStream(repositorioPath + id + ".byte");
            employee = (Employee) inputStream.readObject();

            inputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("");
        }

        return employee;
    }
    @Override
    public boolean isEmailExistente(Employee employee) throws IOException {
        boolean isEmailExistente = false;
        List<Employee> employees = listar();

        for (Employee employeeSalvo: employees) {
            if (!employeeSalvo.getId().equals(employee.getId()) && employeeSalvo.getEmail().equals(employee.getEmail())) {
                isEmailExistente = true;
                break;
            }
        }

        return isEmailExistente;
    }
    @Override
    public Employee salvar(Employee employee) throws IOException, EntidadeNaoSerializavelException {
        ObjectOutputStream outputStream;

        try {
            outputStream = getOutputStream(employee.getArquivo());
            outputStream.writeObject(employee);

            outputStream.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }

        return employee;
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException, EmployeeNaoEncontradoException {
        Employee employee = buscar(id);
        boolean isApagado;

        if (Objects.isNull(employee)) {
            throw new EmployeeNaoEncontradoException("Employee não encontrado");
        }
        else {
            new File(String.format("%s%s.byte", repositorioPath, id)).delete();
            isApagado = true;
        }

        return isApagado;
    }
}

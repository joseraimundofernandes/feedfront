package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeDAO implements DAO<Employee> {
    private String repositorioPath = "";

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() throws IOException, EntidadeNaoSerializavelException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee buscar(String id) throws IOException, EntidadeNaoSerializavelException{
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee salvar(Employee employee) throws IOException, EntidadeNaoSerializavelException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException {
        throw new UnsupportedOperationException();
    }
}

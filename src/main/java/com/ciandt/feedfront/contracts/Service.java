package com.ciandt.feedfront.contracts;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.models.Employee;

import java.io.IOException;
import java.util.List;

public interface Service<E> {
    List<E> listar() throws ArquivoException;

    E buscar(String id) throws ArquivoException, BusinessException;

    E salvar(E e) throws IOException, BusinessException, IllegalArgumentException;

    E atualizar(E e) throws IOException, BusinessException, IllegalArgumentException;

    void apagar(String id) throws IOException, BusinessException, EmployeeNaoEncontradoException;
    void setDAO(DAO<E> dao);
}

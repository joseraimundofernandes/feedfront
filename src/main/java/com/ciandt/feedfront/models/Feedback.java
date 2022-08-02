package com.ciandt.feedfront.models;

import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String descricao;
    @Column
    private String oQueMelhora;
    @Column
    private String comoMelhora;
    @Column(nullable = false)
    private LocalDate data;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Employee autor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Employee proprietario;

    public Feedback() {
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) throws ComprimentoInvalidoException {
        setData(data);
        setAutor(autor);
        setProprietario(proprietario);
        setDescricao(descricao);
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao, String oQueMelhora, String comoMelhora) throws ComprimentoInvalidoException {
        setData(data);
        setAutor(autor);
        setProprietario(proprietario);
        setDescricao(descricao);
        setoQueMelhora(oQueMelhora);
        setComoMelhora(comoMelhora);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getoQueMelhora() {
        return oQueMelhora;
    }

    public void setoQueMelhora(String oQueMelhora) {
        this.oQueMelhora = oQueMelhora;
    }

    public String getComoMelhora() {
        return comoMelhora;
    }

    public void setComoMelhora(String comoMelhora) {
        this.comoMelhora = comoMelhora;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Employee getAutor() {
        return autor;
    }

    public void setAutor(Employee autor) {
        this.autor = autor;
    }

    public Employee getProprietario() {
        return proprietario;
    }

    public void setProprietario(Employee proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", oQueMelhora='" + oQueMelhora + '\'' +
                ", comoMelhora='" + comoMelhora + '\'' +
                ", data=" + data +
                ", autor=" + autor +
                ", proprietario=" + proprietario +
                '}';
    }
}

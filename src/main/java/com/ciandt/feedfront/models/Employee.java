package com.ciandt.feedfront.models;

import com.ciandt.feedfront.exceptions.ComprimentoInvalidoException;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min = 3, message = "O nome deve ter mais de 2 caracteres")
    @Column(nullable = false)
    private String nome;

    @Length(min = 3, message = "O sobrenome deve ter mais de 2 caracteres")
    @Column(nullable = false)
    private String sobrenome;

    @Column(unique = true, nullable = false)
    @Email(message = "Já existe um cadastro com esse email")
    private String email;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbackFeitos;

    @OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbackRecebidos;

    public Employee() {}

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        setNome(nome);
        setSobrenome(sobrenome);
        setEmail(email);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addFeedbackFeitos(Feedback feedback) {
        if (feedback != null) {
            if (feedback == null) {
                feedbackFeitos = new ArrayList<>();
            }
            feedback.setAutor(this);
            feedbackFeitos.add(feedback);
        }
    }

    public void addFeedbackRecebidos(Feedback feedback) {
        if (feedback != null) {
            if (feedback == null) {
                feedbackRecebidos = new ArrayList<>();
            }
            feedback.setProprietario(this);
            feedbackRecebidos.add(feedback);
        }
    }

}
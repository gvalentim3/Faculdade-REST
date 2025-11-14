package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Professores")
public class Professor {

    @Id
    @Column (name = "codigo")
    private int codigo;

    @Column (name = "nome")
    private String nome;

    @Column (name = "dt_nascimento")
    private LocalDate dataNascimento;

    @Column (name = "cpf")
    private String cpf;

    @OneToMany(mappedBy = "professor")
    private List<Turma> turmasLecionadas = new ArrayList<>();
}

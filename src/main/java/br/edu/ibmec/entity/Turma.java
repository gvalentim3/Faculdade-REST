package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Turmas")
public class Turma {

    @Id
    @Column (name = "codigo")
    private int codigo;

    @Column (name = "semestre")
    private String semestre;

    @Column (name = "turno")
    private String turno;

    @ManyToOne
    @JoinColumn (name = "disciplina_id")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn (name = "professor_id")
    private Professor professor;

    @OneToMany(mappedBy = "turma")
    private List<Inscricao> inscricoes = new ArrayList<>();
}

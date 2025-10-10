package br.edu.ibmec.repository;

import br.edu.ibmec.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository <Aluno, String> {
}

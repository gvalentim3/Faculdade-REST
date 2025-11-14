package br.edu.ibmec.repository;

import br.edu.ibmec.entity.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, Integer> {
    List<Inscricao> findByAlunoMatricula(String matriculaAluno);
}

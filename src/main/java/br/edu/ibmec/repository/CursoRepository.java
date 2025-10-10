package br.edu.ibmec.repository;

import br.edu.ibmec.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository <Curso, Integer>{
}

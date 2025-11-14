package br.edu.ibmec.service.validacoesStrategy.aluno;

import br.edu.ibmec.dto.AlunoRequestDTO;
import br.edu.ibmec.exception.RegraDeNegocioException;
import br.edu.ibmec.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoCursoExistente implements ValidacaoAluno {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public void validar(AlunoRequestDTO dto) {
        if (!cursoRepository.existsById(dto.getCurso())) {
            throw new RegraDeNegocioException("O curso com código " + dto.getCurso() + " não foi encontrado.");
        }
    }
}
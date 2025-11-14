package br.edu.ibmec.service.validacoesStrategy.inscricao;

import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.exception.EntidadeNaoEncontradaException;
import br.edu.ibmec.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoAlunoExistente implements ValidacaoInscricao{

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public void validar(InscricaoDTO inscricaoDTO) {
        if( !alunoRepository.existsById(inscricaoDTO.getMatriculaAluno())) {
            throw new EntidadeNaoEncontradaException("Aluno com matrícula " + inscricaoDTO.getMatriculaAluno() + " não encontrado.");
        }
    }
}

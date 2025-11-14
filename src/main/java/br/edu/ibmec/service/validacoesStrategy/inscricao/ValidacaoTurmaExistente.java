package br.edu.ibmec.service.validacoesStrategy.inscricao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.exception.EntidadeNaoEncontradaException;
import br.edu.ibmec.repository.TurmaRepository;


@Component
public class ValidacaoTurmaExistente implements ValidacaoInscricao {

    @Autowired
    private TurmaRepository turmaRepository;

    @Override
    public void validar(InscricaoDTO inscricaoDTO) {
        if (!turmaRepository.existsById(inscricaoDTO.getTurmaId())) {
            throw new EntidadeNaoEncontradaException("Turma com código " + inscricaoDTO.getTurmaId() + " não encontrada.");
        }
    }
}
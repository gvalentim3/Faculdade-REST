package br.edu.ibmec.service.validacoesStrategy.inscricao;

import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.exception.RegraDeNegocioException;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoNumeroFaltas implements ValidacaoInscricao {
    @Override
    public void validar(InscricaoDTO inscricaoDTO) {
        if (inscricaoDTO.getNumFaltas() < 0) {
            throw new RegraDeNegocioException("O número de faltas não pode ser negativo.");
        }
    }
}
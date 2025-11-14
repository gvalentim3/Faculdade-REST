package br.edu.ibmec.service.validacoesStrategy.inscricao;

import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.exception.RegraDeNegocioException;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoCodigoInscricao implements ValidacaoInscricao {
    @Override
    public void validar(InscricaoDTO inscricaoDTO) {
        if (inscricaoDTO.getCodigo() <= 0) {
            throw new RegraDeNegocioException("O código da inscrição deve ser um número positivo.");
        }
    }
}

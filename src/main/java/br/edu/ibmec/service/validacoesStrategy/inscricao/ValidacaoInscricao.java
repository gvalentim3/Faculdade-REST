package br.edu.ibmec.service.validacoesStrategy.inscricao;

import br.edu.ibmec.dto.InscricaoDTO;

public interface ValidacaoInscricao {
    void validar(InscricaoDTO inscricaoDTO);
}
package br.edu.ibmec.service.validacoesStrategy.aluno;

import org.springframework.stereotype.Component;
import br.edu.ibmec.dto.AlunoRequestDTO;
import br.edu.ibmec.exception.RegraDeNegocioException;

@Component
public class ValidacaoNomeAluno implements ValidacaoAluno {
    @Override
    public void validar(AlunoRequestDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().length() < 1 || dto.getNome().length() > 20) {
            throw new RegraDeNegocioException("O nome do aluno é obrigatório e deve ter entre 1 e 20 caracteres.");
        }
    }
}
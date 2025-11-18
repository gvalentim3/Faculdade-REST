package br.edu.ibmec.service.validacoesStrategy.aluno;

import org.springframework.stereotype.Component;
import br.edu.ibmec.dto.AlunoRequestDTO;
import br.edu.ibmec.exception.RegraDeNegocioException;

@Component
public class ValidacaoNomeAluno implements ValidacaoAluno {
    @Override
    public void validar(AlunoRequestDTO dto) {
        if (dto.getNome() == null) {
            throw new RegraDeNegocioException("O nome do aluno é obrigatório.");
        }

        if (dto.getNome().trim().isEmpty()) {
            throw new RegraDeNegocioException("O nome do aluno deve ter pelo menos um caractere.");
        }

        if (dto.getNome().length() > 20) {
            throw new RegraDeNegocioException("O nome do aluno deve ter menos de 20 caracteres.");
        }
    }
}
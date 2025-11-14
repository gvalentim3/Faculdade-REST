package br.edu.ibmec.service.validacoesStrategy.aluno;

import br.edu.ibmec.dto.AlunoRequestDTO;
import br.edu.ibmec.exception.RegraDeNegocioException;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoDataNascimento implements ValidacaoAluno {
    @Override
    public void validar(AlunoRequestDTO dto) {
        if (dto.getDataNascimento() == null) {
            throw new RegraDeNegocioException("A data de nascimento é obrigatória.");
        }
    }
}
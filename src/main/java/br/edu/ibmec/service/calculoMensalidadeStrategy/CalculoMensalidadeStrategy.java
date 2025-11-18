package br.edu.ibmec.service.calculoMensalidadeStrategy;

import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.TipoCalculoMensalidade;

public interface CalculoMensalidadeStrategy {
    double calcular(double valorBruto, Aluno aluno);
    TipoCalculoMensalidade getTipo();
}

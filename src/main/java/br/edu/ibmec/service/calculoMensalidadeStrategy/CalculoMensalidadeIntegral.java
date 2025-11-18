package br.edu.ibmec.service.calculoMensalidadeStrategy;

import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.TipoCalculoMensalidade;
import org.springframework.stereotype.Component;

@Component
public class CalculoMensalidadeIntegral implements CalculoMensalidadeStrategy {
    @Override
    public double calcular(double valorBruto, Aluno aluno) {
        return valorBruto;
    }

    @Override
    public TipoCalculoMensalidade getTipo() {
        return TipoCalculoMensalidade.INTEGRAL;
    }
}

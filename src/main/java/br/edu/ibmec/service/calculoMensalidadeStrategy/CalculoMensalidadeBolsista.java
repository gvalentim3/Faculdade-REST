package br.edu.ibmec.service.calculoMensalidadeStrategy;

import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.TipoCalculoMensalidade;
import org.springframework.stereotype.Component;

@Component
public class CalculoMensalidadeBolsista implements CalculoMensalidadeStrategy {
    @Override
    public double calcular(double valorBruto, Aluno aluno) {
        double percentualBolsa = aluno.getBolsa();
        double fatorDesconto = (100.0 - percentualBolsa) / 100.0;

        return valorBruto * fatorDesconto;
    }

    @Override
    public TipoCalculoMensalidade getTipo() {
        return TipoCalculoMensalidade.BOLSISTA;
    }
}
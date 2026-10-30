package com.jadert.panther;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Retorno extends AppCompatActivity {
    // --- CONSTANTES ---
    private static final double VALOR_VT = 0.1533333333333333;

    private static final String[][] ENGRENAGENS_1 = {
            {"14", "15"}, {"14", "17"}, {"14", "19"}, {"14", "21"}, {"14", "23"},
            {"16", "15"}, {"16", "17"}, {"16", "19"}, {"16", "21"}, {"16", "23"},
            {"18", "15"}, {"18", "17"}, {"18", "19"}, {"18", "21"}, {"18", "23"},
            {"20", "15"}, {"20", "17"}, {"20", "19"}, {"20", "21"}, {"20", "23"},
            {"24", "15"}, {"24", "17"}, {"24", "19"}, {"24", "21"}, {"24", "23"}
    };

    private static final String[][] ENGRENAGENS_2 = {
            {"24", "21"}, {"24", "14"}, {"14", "21"}, {"14", "14"}
    };

    private static final double[] RELACAO_1 = {
            0.933333, 0.823529, 0.736842, 0.666667, 0.608696,
            1.066667, 0.941176, 0.842105, 0.761905, 0.695652,
            1.2, 1.058824, 0.947368, 0.857143, 0.782609,
            1.333333, 1.176471, 1.052632, 0.952381, 0.869565,
            1.6, 1.411765, 1.263158, 1.142857, 1.043478
    };

    private static final double[] RELACAO_2 = {1.142857, 1.714286, 0.666667, 1};

    private static final String ENGRENAGEM_FACIL_C = "14";
    private static final String ENGRENAGEM_FACIL_D = "14";

    // --- Classe interna para organizar os dados ---
    private static class Combinacao {
        final double sementesPorMetro;
        final String eixoA, eixoB, eixoC, eixoD;

        Combinacao(double sementes, String a, String b, String c, String d) {
            this.sementesPorMetro = sementes;
            this.eixoA = a;
            this.eixoB = b;
            this.eixoC = c;
            this.eixoD = d;
        }
    }

    // --- Variáveis de UI ---
    private TextView resultadoSementesTextView;
    private TextView txtAlvoSolicitado, txtDiferenca;
    private TextView resultadoEixoA, resultadoEixoB, resultadoEixoC, resultadoEixoD;
    private TextView badgeEixoCFixo, badgeEixoDFixo;
    private MaterialButtonToggleGroup toggleAbaixoAcima, toggleFacilProximo;

    // --- Variáveis para armazenar os 4 resultados principais ---
    private Combinacao resultadoAbaixoFacil, resultadoAcimaFacil;
    private Combinacao resultadoAbaixoProximo, resultadoAcimaProximo;
    private int sementesAlvo = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retorno);

        inicializarUI();
        configurarNavegacao();
        configurarListeners();

        // Obter dados da Intent com segurança
        Intent intent = getIntent();
        sementesAlvo = obterSementesAlvo(intent);
        int furos = obterFuros(intent);

        calcularResultados(sementesAlvo, furos);
        updateUI();
    }

    private void inicializarUI() {
        resultadoSementesTextView = findViewById(R.id.resultadoSementes);
        txtAlvoSolicitado = findViewById(R.id.txtAlvoSolicitado);
        txtDiferenca = findViewById(R.id.txtDiferenca);

        resultadoEixoA = findViewById(R.id.resultadoEixoA);
        resultadoEixoB = findViewById(R.id.resultadoEixoB);
        resultadoEixoC = findViewById(R.id.resultadoEixoC);
        resultadoEixoD = findViewById(R.id.resultadoEixoD);

        badgeEixoCFixo = findViewById(R.id.badgeEixoCFixo);
        badgeEixoDFixo = findViewById(R.id.badgeEixoDFixo);

        toggleAbaixoAcima = findViewById(R.id.toggleAbaixoAcima);
        toggleFacilProximo = findViewById(R.id.toggleFacilProximo);

        // Seleções padrão
        toggleAbaixoAcima.check(R.id.btnAbaixo);
        toggleFacilProximo.check(R.id.btnFacil);
    }

    private void configurarNavegacao() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarRetorno);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void configurarListeners() {
        toggleAbaixoAcima.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) updateUI();
        });

        toggleFacilProximo.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) updateUI();
        });
    }

    private int obterSementesAlvo(Intent intent) {
        try {
            String val = intent.getStringExtra("sementes");
            return val != null ? Integer.parseInt(val) : 12;
        } catch (NumberFormatException e) {
            return 12;
        }
    }

    private int obterFuros(Intent intent) {
        try {
            String val = intent.getStringExtra("furos");
            return val != null ? Integer.parseInt(val) : 28;
        } catch (NumberFormatException e) {
            return 28;
        }
    }

    private void calcularResultados(int sementesAlvo, int furos) {
        List<Combinacao> todasCombinacoes = gerarTodasCombinacoes(furos);
        List<Combinacao> combinacoesFaceis = filtrarCombinacoesFaceis(todasCombinacoes);

        encontrarMelhoresCombinacoes(sementesAlvo, todasCombinacoes, combinacoesFaceis);
    }

    private List<Combinacao> gerarTodasCombinacoes(int furos) {
        List<Combinacao> todasCombinacoes = new ArrayList<>();

        for (int i = 0; i < RELACAO_1.length; i++) {
            for (int j = 0; j < RELACAO_2.length; j++) {
                double sementesCalc = calcularSementes(furos, RELACAO_1[i], RELACAO_2[j]);

                Combinacao c = new Combinacao(sementesCalc,
                        ENGRENAGENS_1[i][0], ENGRENAGENS_1[i][1],
                        ENGRENAGENS_2[j][0], ENGRENAGENS_2[j][1]);

                todasCombinacoes.add(c);
            }
        }

        return todasCombinacoes;
    }

    private double calcularSementes(int furos, double relacao1, double relacao2) {
        return furos * (relacao1 * relacao2) * VALOR_VT;
    }

    private List<Combinacao> filtrarCombinacoesFaceis(List<Combinacao> todasCombinacoes) {
        List<Combinacao> combinacoesFaceis = new ArrayList<>();

        for (Combinacao c : todasCombinacoes) {
            if (isCombinacaoFacil(c)) {
                combinacoesFaceis.add(c);
            }
        }

        return combinacoesFaceis;
    }

    private boolean isCombinacaoFacil(Combinacao combinacao) {
        return Objects.equals(combinacao.eixoC, ENGRENAGEM_FACIL_C) &&
                Objects.equals(combinacao.eixoD, ENGRENAGEM_FACIL_D);
    }

    private void encontrarMelhoresCombinacoes(int sementesAlvo, List<Combinacao> todasCombinacoes,
                                              List<Combinacao> combinacoesFaceis) {
        encontrarMelhoresCombinacoesFaceis(sementesAlvo, combinacoesFaceis);
        encontrarMelhoresCombinacoesTodas(sementesAlvo, todasCombinacoes);
    }

    private void encontrarMelhoresCombinacoesFaceis(int sementesAlvo, List<Combinacao> combinacoesFaceis) {
        double maxAbaixoFacil = -1;
        double minAcimaFacil = Double.MAX_VALUE;

        for (Combinacao c : combinacoesFaceis) {
            if (c.sementesPorMetro <= sementesAlvo && c.sementesPorMetro > maxAbaixoFacil) {
                maxAbaixoFacil = c.sementesPorMetro;
                resultadoAbaixoFacil = c;
            }
            if (c.sementesPorMetro >= sementesAlvo && c.sementesPorMetro < minAcimaFacil) {
                minAcimaFacil = c.sementesPorMetro;
                resultadoAcimaFacil = c;
            }
        }

        if (resultadoAbaixoFacil == null) resultadoAbaixoFacil = resultadoAcimaFacil;
        if (resultadoAcimaFacil == null) resultadoAcimaFacil = resultadoAbaixoFacil;
    }

    private void encontrarMelhoresCombinacoesTodas(int sementesAlvo, List<Combinacao> todasCombinacoes) {
        double maxAbaixoProximo = -1;
        double minAcimaProximo = Double.MAX_VALUE;

        for (Combinacao c : todasCombinacoes) {
            if (c.sementesPorMetro <= sementesAlvo && c.sementesPorMetro > maxAbaixoProximo) {
                maxAbaixoProximo = c.sementesPorMetro;
                resultadoAbaixoProximo = c;
            }
            if (c.sementesPorMetro >= sementesAlvo && c.sementesPorMetro < minAcimaProximo) {
                minAcimaProximo = c.sementesPorMetro;
                resultadoAcimaProximo = c;
            }
        }

        if (resultadoAbaixoProximo == null) resultadoAbaixoProximo = resultadoAcimaProximo;
        if (resultadoAcimaProximo == null) resultadoAcimaProximo = resultadoAbaixoProximo;
    }

    private void updateUI() {
        Combinacao resultadoFinal = getCombinacao();
        if (resultadoFinal != null) {
            exibirResultadoEncontrado(resultadoFinal);
        }
    }

    private void exibirResultadoEncontrado(Combinacao resultado) {
        resultadoSementesTextView.setText(getString(R.string.resultado_final_sementes, resultado.sementesPorMetro));
        resultadoEixoA.setText(resultado.eixoA);
        resultadoEixoB.setText(resultado.eixoB);
        resultadoEixoC.setText(resultado.eixoC);
        resultadoEixoD.setText(resultado.eixoD);

        // Atualizar Comparativo Alvo vs Real
        txtAlvoSolicitado.setText(getString(R.string.alvo_solicitado, sementesAlvo));
        double diff = resultado.sementesPorMetro - sementesAlvo;
        if (Math.abs(diff) < 0.005) {
            txtDiferenca.setText(getString(R.string.diferenca_exata));
        } else {
            double percent = (diff / (double) sementesAlvo) * 100.0;
            txtDiferenca.setText(getString(R.string.diferenca_formato, diff, percent));
        }

        // Rótulos de eixos fixos no modo fácil
        boolean isFacil = toggleFacilProximo.getCheckedButtonId() == R.id.btnFacil;
        if (isFacil) {
            badgeEixoCFixo.setText(getString(R.string.fixo_rotulo));
            badgeEixoDFixo.setText(getString(R.string.fixo_rotulo));
        } else {
            badgeEixoCFixo.setText(getString(R.string.eixo_c_desc));
            badgeEixoDFixo.setText(getString(R.string.eixo_d_desc));
        }
    }

    private Combinacao getCombinacao() {
        boolean isAcima = toggleAbaixoAcima.getCheckedButtonId() == R.id.btnAcima;
        boolean isProximo = toggleFacilProximo.getCheckedButtonId() == R.id.btnProximo;

        if (!isAcima && !isProximo) return resultadoAbaixoFacil;  // Abaixo + Fácil
        if (!isAcima) return resultadoAbaixoProximo;              // Abaixo + Próximo
        if (!isProximo) return resultadoAcimaFacil;               // Acima + Fácil
        return resultadoAcimaProximo;                             // Acima + Próximo
    }
}

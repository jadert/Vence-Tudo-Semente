package com.jadert.panther;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private TextView resultadoEixoA, resultadoEixoB, resultadoEixoC, resultadoEixoD;
    private SwitchCompat switchAcimaAbaixo, switchFacilProximo;

    // --- Variáveis para armazenar os 4 resultados principais ---
    private Combinacao resultadoAbaixoFacil, resultadoAcimaFacil;
    private Combinacao resultadoAbaixoProximo, resultadoAcimaProximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_retorno);

        inicializarUI();
        configurarListeners();

        // Obter dados da Intent
        Intent intent = getIntent();
        int sementesAlvo = obterSementesAlvo(intent);
        int furos = obterFuros(intent);

        calcularResultados(sementesAlvo, furos);
        updateUI();
    }

    private void inicializarUI() {
        resultadoSementesTextView = findViewById(R.id.resultadoSementes);
        resultadoEixoA = findViewById(R.id.resultadoEixoA);
        resultadoEixoB = findViewById(R.id.resultadoEixoB);
        resultadoEixoC = findViewById(R.id.resultadoEixoC);
        resultadoEixoD = findViewById(R.id.resultadoEixoD);
        switchAcimaAbaixo = findViewById(R.id.switchAcimaAbaixo);
        switchFacilProximo = findViewById(R.id.switchFacilProximo);
    }

    private void configurarListeners() {
        switchAcimaAbaixo.setOnCheckedChangeListener((buttonView, isChecked) -> updateUI());
        switchFacilProximo.setOnCheckedChangeListener((buttonView, isChecked) -> updateUI());

        FloatingActionButton voltar = findViewById(R.id.voltar);
        voltar.setOnClickListener(view -> openActivity());
    }

    private int obterSementesAlvo(Intent intent) {
        return Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("sementes")));
    }

    private int obterFuros(Intent intent) {
        return Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("furos")));
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
            if (isCombinacaoAbaixoAlvo(c, sementesAlvo) && c.sementesPorMetro > maxAbaixoFacil) {
                maxAbaixoFacil = c.sementesPorMetro;
                resultadoAbaixoFacil = c;
            }
            if (isCombinacaoAcimaAlvo(c, sementesAlvo) && c.sementesPorMetro < minAcimaFacil) {
                minAcimaFacil = c.sementesPorMetro;
                resultadoAcimaFacil = c;
            }
        }
    }

    private void encontrarMelhoresCombinacoesTodas(int sementesAlvo, List<Combinacao> todasCombinacoes) {
        double maxAbaixoProximo = -1;
        double minAcimaProximo = Double.MAX_VALUE;

        for (Combinacao c : todasCombinacoes) {
            if (isCombinacaoAbaixoAlvo(c, sementesAlvo) && c.sementesPorMetro > maxAbaixoProximo) {
                maxAbaixoProximo = c.sementesPorMetro;
                resultadoAbaixoProximo = c;
            }
            if (isCombinacaoAcimaAlvo(c, sementesAlvo) && c.sementesPorMetro < minAcimaProximo) {
                minAcimaProximo = c.sementesPorMetro;
                resultadoAcimaProximo = c;
            }
        }
    }

    private boolean isCombinacaoAbaixoAlvo(Combinacao combinacao, int sementesAlvo) {
        return combinacao.sementesPorMetro < sementesAlvo;
    }

    private boolean isCombinacaoAcimaAlvo(Combinacao combinacao, int sementesAlvo) {
        return combinacao.sementesPorMetro > sementesAlvo;
    }

    private void updateUI() {
        Combinacao resultadoFinal = getCombinacao();
        exibirResultadoEncontrado(resultadoFinal);
    }

    private void exibirResultadoEncontrado(Combinacao resultado) {
        resultadoSementesTextView.setText(getString(R.string.resultado_final_sementes, resultado.sementesPorMetro));
        resultadoEixoA.setText(resultado.eixoA);
        resultadoEixoB.setText(resultado.eixoB);
        resultadoEixoC.setText(resultado.eixoC);
        resultadoEixoD.setText(resultado.eixoD);
    }

    private Combinacao getCombinacao() {
        boolean isAcima = switchAcimaAbaixo.isChecked();
        boolean isProximo = switchFacilProximo.isChecked();

        if (!isAcima && !isProximo) return resultadoAbaixoFacil;  // Abaixo + Fácil
        if (!isAcima) return resultadoAbaixoProximo;              // Abaixo + Próximo
        if (!isProximo) return resultadoAcimaFacil;               // Acima + Fácil
        return resultadoAcimaProximo;                             // Acima + Próximo
    }

    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
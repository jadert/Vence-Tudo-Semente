package com.jadert.panther;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private EditText sementesEditText;
    private TextInputLayout sementesInputLayout;
    private EditText furosEditText;
    private TextInputLayout furosInputLayout;

    private static final int SEMENTES_MIN = 1;
    private static final int SEMENTES_MAX = 100;
    private static final int FUROS_MIN = 1;
    private static final int FUROS_MAX = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initViews();
        setupListeners();
    }

    private void initViews() {
        sementesEditText = findViewById(R.id.sementesEditText);
        sementesInputLayout = findViewById(R.id.sementesInputLayout);
        furosEditText = findViewById(R.id.furosEditText);
        furosInputLayout = findViewById(R.id.furosInputLayout);
    }

    private void setupListeners() {
        MaterialButton info = findViewById(R.id.mostraInfo);
        MaterialButton button = findViewById(R.id.button);
        MaterialButton btnMenos = findViewById(R.id.btnMenosSementes);
        MaterialButton btnMais = findViewById(R.id.btnMaisSementes);

        MaterialButton chip28 = findViewById(R.id.chipFuros28);
        MaterialButton chip45 = findViewById(R.id.chipFuros45);
        MaterialButton chip90 = findViewById(R.id.chipFuros90);

        info.setOnClickListener(view -> openAbout());

        btnMenos.setOnClickListener(v -> alterarSementes(-1));
        btnMais.setOnClickListener(v -> alterarSementes(1));

        chip28.setOnClickListener(v -> setFurosRapido("28"));
        chip45.setOnClickListener(v -> setFurosRapido("45"));
        chip90.setOnClickListener(v -> setFurosRapido("90"));

        button.setOnClickListener(view -> validarECalcular());
    }

    private void setFurosRapido(String valor) {
        furosEditText.setText(valor);
        furosEditText.setSelection(valor.length());
        furosInputLayout.setError(null);
    }

    private void alterarSementes(int delta) {
        int atual = obterValorSementesAtual();
        int novo = Math.max(SEMENTES_MIN, Math.min(SEMENTES_MAX, atual + delta));
        sementesEditText.setText(String.valueOf(novo));
        sementesEditText.setSelection(sementesEditText.getText().length());
        sementesInputLayout.setError(null);
    }

    private int obterValorSementesAtual() {
        try {
            String str = sementesEditText.getText().toString().trim();
            if (str.isEmpty()) return 12;
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 12;
        }
    }

    private void validarECalcular() {
        sementesInputLayout.setError(null);
        furosInputLayout.setError(null);

        String sementesStr = sementesEditText.getText().toString().trim();
        if (sementesStr.isEmpty()) {
            sementesInputLayout.setError(getString(R.string.erro_campo_obrigatorio));
            return;
        }

        int sementes;
        try {
            sementes = Integer.parseInt(sementesStr);
            if (sementes < SEMENTES_MIN || sementes > SEMENTES_MAX) {
                sementesInputLayout.setError(getString(R.string.erro_intervalo_sementes));
                return;
            }
        } catch (NumberFormatException e) {
            sementesInputLayout.setError(getString(R.string.erro_numero_invalido));
            return;
        }

        String furosStr = furosEditText.getText().toString().trim();
        if (furosStr.isEmpty()) {
            furosInputLayout.setError(getString(R.string.erro_campo_obrigatorio));
            return;
        }

        int furos;
        try {
            furos = Integer.parseInt(furosStr);
            if (furos < FUROS_MIN || furos > FUROS_MAX) {
                furosInputLayout.setError(getString(R.string.erro_intervalo_furos));
                return;
            }
        } catch (NumberFormatException e) {
            furosInputLayout.setError(getString(R.string.erro_numero_invalido));
            return;
        }

        openActivity(sementes, furos);
    }

    private void openActivity(int sementes, int furos) {
        Intent intent = new Intent(this, Retorno.class);
        intent.putExtra("sementes", String.valueOf(sementes));
        intent.putExtra("furos", String.valueOf(furos));
        startActivity(intent);
    }

    private void openAbout() {
        Intent intentAbout = new Intent(this, About.class);
        startActivity(intentAbout);
    }
}

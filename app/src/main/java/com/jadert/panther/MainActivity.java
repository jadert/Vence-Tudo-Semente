package com.jadert.panther;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
// Importe a classe TextInputLayout
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    private NumberPicker numberPicker;
    // Mude de EditText para TextInputEditText para mais clareza
    private EditText furosEditText;
    // Adicione a referência para o TextInputLayout
    private TextInputLayout furosInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        numberPicker = findViewById(R.id.numberPicker);
        // Atualize as referências para os novos IDs
        furosEditText = findViewById(R.id.furosEditText);
        furosInputLayout = findViewById(R.id.furosInputLayout);
        FloatingActionButton info = findViewById(R.id.mostraInfo);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(40);
        numberPicker.setValue(12);

        info.setOnClickListener(view -> openAbout());

        button.setOnClickListener(view -> {
            // Limpa o erro anterior antes de tentar de novo
            furosInputLayout.setError(null);

            int sementes = numberPicker.getValue();
            try {
                // Verificação para garantir que o campo não está vazio
                String furosStr = furosEditText.getText().toString();
                if (furosStr.isEmpty()) {
                    // Exibe o erro diretamente no campo de texto
                    furosInputLayout.setError(getString(R.string.erro_campo_obrigatorio));
                    return;
                }

                int furos =  Integer.parseInt(furosStr);
                if (furos > 200) {
                    furos = 200;
                } else if (furos < 1) {
                    furos = 1;
                }
                openActivity(sementes, furos);
            } catch (NumberFormatException e) {
                // Esta exceção é menos provável com inputType="number", mas é bom manter
                furosInputLayout.setError(getString(R.string.erro_numero_invalido));
            }
        });
    }

    public void openActivity(int sementes, int furos) {
        Intent intent = new Intent(this, Retorno.class);
        intent.putExtra("sementes",String.valueOf(sementes));
        intent.putExtra("furos",String.valueOf(furos));
        startActivity(intent);
    }

    public void openAbout() {
        Intent intentAbout = new Intent(this, About.class);
        startActivity(intentAbout);
    }
}
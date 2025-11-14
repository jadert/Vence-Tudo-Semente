package com.jadert.panther;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.button.MaterialButton;
import androidx.activity.EdgeToEdge;

public class About extends AppCompatActivity {

    private ImageView tutorialImage;
    private TextView tutorialText;
    private MaterialButton btnPrevious, btnNext;
    private int currentPage = 0;

    private final int[] tutorialImages = {
            R.drawable.tutorial_1,
            R.drawable.tutorial_2,
            R.drawable.tutorial_3,
            R.drawable.tutorial_4,
            R.drawable.tutorial_5
    };

    private final String[] tutorialTexts = {
            "O primeiro passo é inserir a quantidade de sementes por metro desejadas e a quantidade de furos no disco utilizado, feito isso clique em calcular.",
            "Se o valor exato de sementes não for encontrado, será buscado o mais próximo acima ou abaixo do valor desejado. Você pode selecionar qual deseja",
            "Também existe a opção de regulagem mais fácil onde você vai mudar apenas os eixos A e B, ou pode buscar a configuração mais próxima possível do valor desejado",
            "Aqui é mostrada qual engrenagem deve ser colocada em cada eixo bem como a quantidade real de sementes, você pode alterar as opções de ajuste livremente.",
            "Esse é um diagrama dos eixos na plantadeira e com seu respectivo nome, sendo eles: Eixo A, Eixo B, Eixo C e Eixo D."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        initViews();
        setupNavigationButtons();
        setupFloatingActionButton();
        updateTutorialContent();
    }

    private void initViews() {
        tutorialImage = findViewById(R.id.tutorialImage);
        tutorialText = findViewById(R.id.tutorialText);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setupNavigationButtons() {
        btnPrevious.setOnClickListener(v -> {
            if (currentPage > 0) {
                currentPage--;
                updateTutorialContent();
            }
        });

        btnNext.setOnClickListener(v -> {
            if (currentPage < tutorialImages.length - 1) {
                currentPage++;
                updateTutorialContent();
            }
        });
    }

    private void updateTutorialContent() {
        tutorialText.setText(tutorialTexts[currentPage]);
        tutorialImage.setImageResource(tutorialImages[currentPage]);
        updateNavigationButtons();
    }

    private void updateNavigationButtons() {
        btnPrevious.setEnabled(currentPage > 0);
        btnNext.setEnabled(currentPage < tutorialImages.length - 1);

        // Opcional: Mudar a opacidade dos botões desabilitados
        btnPrevious.setAlpha(currentPage > 0 ? 1.0f : 0.5f);
        btnNext.setAlpha(currentPage < tutorialImages.length - 1 ? 1.0f : 0.5f);
    }

    private void setupFloatingActionButton() {
        FloatingActionButton voltar = findViewById(R.id.voltar);
        voltar.setOnClickListener(view -> openActivity());
    }

    public void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
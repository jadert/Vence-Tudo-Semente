package com.jadert.panther;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class About extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView txtIndicadorPasso;
    private MaterialButton btnPrevious, btnNext;

    private static final String KEY_CURRENT_PAGE = "key_current_page";
    private int savedPageIndex = 0;

    private final int[] tutorialImages = {
            R.drawable.tutorial_1,
            R.drawable.tutorial_2,
            R.drawable.tutorial_3,
            R.drawable.tutorial_4,
            R.drawable.tutorial_5
    };

    private final int[] tutorialTextsRes = {
            R.string.tutorial_passo_1,
            R.string.tutorial_passo_2,
            R.string.tutorial_passo_3,
            R.string.tutorial_passo_4,
            R.string.tutorial_passo_5
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        if (savedInstanceState != null) {
            savedPageIndex = savedInstanceState.getInt(KEY_CURRENT_PAGE, 0);
        }

        initViews();
        setupNavigationToolbar();
        setupViewPager();
        setupNavigationButtons();
        setupWindowInsets();
    }

    private void setupWindowInsets() {
        View copyrightContainer = findViewById(R.id.copyrightContainer);
        if (copyrightContainer != null) {
            ViewCompat.setOnApplyWindowInsetsListener(copyrightContainer, (v, windowInsets) -> {
                Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                int baseBottom = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics());
                v.setPadding(
                        v.getPaddingLeft(),
                        v.getPaddingTop(),
                        v.getPaddingRight(),
                        insets.bottom + baseBottom
                );
                return windowInsets;
            });
        }
    }

    private void initViews() {
        viewPager = findViewById(R.id.viewPagerTutorial);
        txtIndicadorPasso = findViewById(R.id.txtIndicadorPasso);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    private void setupNavigationToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarAbout);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void setupViewPager() {
        TutorialAdapter adapter = new TutorialAdapter(tutorialImages, tutorialTextsRes);
        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                atualizarControles(position);
            }
        });

        if (savedPageIndex >= 0 && savedPageIndex < tutorialImages.length) {
            viewPager.setCurrentItem(savedPageIndex, false);
        }
    }

    private void setupNavigationButtons() {
        btnPrevious.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (current > 0) {
                viewPager.setCurrentItem(current - 1, true);
            }
        });

        btnNext.setOnClickListener(v -> {
            int current = viewPager.getCurrentItem();
            if (current < tutorialImages.length - 1) {
                viewPager.setCurrentItem(current + 1, true);
            } else {
                finish();
            }
        });
    }

    private void atualizarControles(int position) {
        int total = tutorialImages.length;
        txtIndicadorPasso.setText(getString(R.string.tutorial_passo, position + 1, total));

        btnPrevious.setEnabled(position > 0);
        btnPrevious.setAlpha(position > 0 ? 1.0f : 0.4f);

        if (position == total - 1) {
            btnNext.setText(R.string.concluir);
            btnNext.setIcon(null);
        } else {
            btnNext.setText(R.string.proximo);
            btnNext.setIconResource(R.drawable.ic_arrow_forward);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewPager != null) {
            outState.putInt(KEY_CURRENT_PAGE, viewPager.getCurrentItem());
        }
    }

    // --- Adaptador do ViewPager2 ---
    private static class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {
        private final int[] images;
        private final int[] texts;

        TutorialAdapter(int[] images, int[] texts) {
            this.images = images;
            this.texts = texts;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_tutorial_slide, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.text.setText(texts[position]);
            holder.image.setImageResource(images[position]);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            final TextView text;
            final ImageView image;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                text = itemView.findViewById(R.id.slideText);
                image = itemView.findViewById(R.id.slideImage);
            }
        }
    }
}

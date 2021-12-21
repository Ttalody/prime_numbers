package com.example.lab7;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText searchPrimesSeries;
    private Button showPrimesSeriesButton;
    private TextInputLayout searchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchPrimesSeries = this.findViewById(R.id.searchPrimes);
        showPrimesSeriesButton = this.findViewById(R.id.okButton);
        searchLayout = this.findViewById(R.id.searchInput);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    searchPrimesSeries.setText("");
                });
        createOkButtonListener(this, ShowPrimesSeriesActivity.class, launcher);
    }
    private void createOkButtonListener(AppCompatActivity from, Class to, ActivityResultLauncher<Intent> launcher){
        showPrimesSeriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int number = Integer.parseInt(searchPrimesSeries.getText().toString());
                    searchLayout.setHint(getResources().getString(R.string.number));
                    Intent intent = new Intent(from, to);
                    intent.putExtra("number", number);
                    launcher.launch(intent);
                } catch (NumberFormatException e){
                    searchLayout.setHint(getResources().getString(R.string.not_valid));
                }
            }
        });
    }
}

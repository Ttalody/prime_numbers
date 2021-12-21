package com.example.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class ShowPrimesSeriesActivity extends AppCompatActivity {

    private TextView primesSeriesTextView;
    private Button backButton;
    private PrimeNumbersCalculation primeNumbersCalculation;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_primes_series);

        Intent intent = getIntent();
        int numberBefore = intent.getIntExtra("number",0);
        primesSeriesTextView = this.findViewById(R.id.primesSeriesTextView);
        primesSeriesTextView.setMovementMethod(new ScrollingMovementMethod());
        backButton = this.findViewById(R.id.backButton);

        createBackButtonListener(this);

        handler = new Handler(getBaseContext().getMainLooper());
        if(numberBefore <= 1){
            primesSeriesTextView.setText("No prime numbers");
        } else {
            primeNumbersCalculation = new PrimeNumbersCalculation(numberBefore);
            Thread calculation = new Thread(primeNumbersCalculation);
            calculation.start();
        }
    }

    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    class PrimeNumbersCalculation implements Runnable {
        private int before;

        PrimeNumbersCalculation(int before){
            this.before = before;
        }

        @Override
        public void run() {
            List<Integer> primeNumbers = getPrimeNumbersBeforeGiven();
            setText(primesSeriesTextView, primeNumbers.toString());
        }
        private List<Integer> getPrimeNumbersBeforeGiven(){
            List<Integer> primeNumbers = new ArrayList<>();
            boolean[] checked = new boolean[before];
            for(int number=2; number<before; number++){
                if(!checked[number]) {
                    primeNumbers.add(number);
                    for (int multiple = number; multiple < before; multiple += number) {
                        checked[multiple] = true;
                    }
                }
            }
            return primeNumbers;
        }

    }

    private void createBackButtonListener(AppCompatActivity activity){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                activity.setResult(Activity.RESULT_OK, returnIntent);
                activity.finish();
            }
        });
    }
}
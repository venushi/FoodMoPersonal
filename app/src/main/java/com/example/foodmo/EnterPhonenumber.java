package com.example.foodmo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EnterPhonenumber extends AppCompatActivity {

    private Spinner spinnerCountries;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phonenumber);

        spinnerCountries = findViewById(R.id.spinnerCountries);
        spinnerCountries.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextTextPersonNamee1);

        findViewById(R.id.con).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinnerCountries.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 9) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = "+" + code + number;

                Intent intentec = new Intent(EnterPhonenumber.this, Entercode.class);
                intentec.putExtra("phonenumber", phoneNumber);
                startActivity(intentec);

            }
        });
    }

    public void skip(View view) {
        Intent intentskip = new Intent(EnterPhonenumber.this, SignIn.class);

        startActivity(intentskip);

    }
}
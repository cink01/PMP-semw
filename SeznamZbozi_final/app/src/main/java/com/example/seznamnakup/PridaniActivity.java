package com.example.seznamnakup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PridaniActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridani);
        editText = (EditText) findViewById(R.id.editText_nazev_pridani);
        myDB = new DatabaseHelper(this);
    }

    EditText editText;
    DatabaseHelper myDB;

    public void onClickProvedPridani(View view) {

        try {
            String newEntry = editText.getText().toString();
            if(editText.length()!=0)
            {
                AddDat(newEntry);
                editText.setText("");
            }
            else
            {
                Toast.makeText(this,"Musis neco zadat",Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex)
        {
            Toast.makeText(this, "Položky nesmí být prázdné", Toast.LENGTH_LONG).show();
        }
    }

    public void AddDat(String newEntry){
        boolean insertdata = myDB.AddData(newEntry);
        if(insertdata==true)
        {
            Toast.makeText(this,"FINITO",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Chyba zadaní",Toast.LENGTH_LONG).show();
        }
    }

    public void onClickUkonci(View view){
        finish();
        super.onBackPressed();
    }
}
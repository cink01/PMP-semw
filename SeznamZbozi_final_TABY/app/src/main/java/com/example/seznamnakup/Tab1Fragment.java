package com.example.seznamnakup;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/28/2017.
 */

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    EditText editText, CRUD;
    DatabaseHelper myDB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        return view;

    }

    /*
    public void onClickProvedPridani(View view) {

        try {
            String newEntry = editText.getText().toString();
            if (editText.length() != 0) {
                AddDat(newEntry);
                editText.setText("");
                PleniSpinn();
            } else {
              //  Toast.makeText(this, "Musis neco zadat", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
           // Toast.makeText(this, "Položky nesmí být prázdné", Toast.LENGTH_LONG).show();
        }
    }

    public void AddDat(String newEntry) {
        boolean insertdata = myDB.AddData(newEntry);
        if (insertdata == true) {
           // Toast.makeText(Tab1Fragment.this, "FINITO", Toast.LENGTH_LONG).show();
        } else {
           // Toast.makeText(this, "Chyba zadaní", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickUkonci(View view) {
       finish();
        super.onBackPressed();
    }

    Cursor data;
    List<Item> listik;
    int poziceSp;
    Spinner spinner;
    ListView lv;

    public void PleniSpinn() {
        listik = new ArrayList<>();
        myDB = new DatabaseHelper(this);
        data = myDB.getListContents();
        lv = (ListView)findViewById(R.id.lv_CRUD);

        if (data.getCount() == 0) {
            Toast.makeText(this, "prazdna db", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                listik.add(new Item(data.getInt(0), data.getString(1)));
                spinner = (Spinner) findViewById(R.id.spinner_CRUD);
                ArrayAdapter<Item> spinAda = new ArrayAdapter<Item>(PridaniActivity.this, android.R.layout.simple_list_item_1, listik);

                spinAda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinAda);
                lv.setAdapter(spinAda);
            }
        }
    }

    public void PleniSpinn(Item najdi) {
        listik = new ArrayList<>();
        myDB = new DatabaseHelper(this);
        data = myDB.getListContents();
        lv = (ListView) findViewById(R.id.lv_CRUD);

        if (data.getCount() == 0) {
            Toast.makeText(this, "prazdna db", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                listik.add(new Item(data.getInt(0), data.getString(1)));
                spinner = (Spinner) findViewById(R.id.spinner_CRUD);
                ArrayAdapter<Item> spinAda = new ArrayAdapter<Item>(PridaniActivity.this, android.R.layout.simple_list_item_1, listik);

                lv.setAdapter(spinAda);
                spinAda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinAda);

                if (najdi != null) {
                    int spinnerPosition = poziceSp = spinAda.getPosition(najdi);
                    spinner.setSelection(spinnerPosition);
                }
            }
        }
    }*/
}

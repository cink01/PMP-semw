package com.example.seznamnakup;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PridaniActivity extends AppCompatActivity {

    ImageButton uprav, smaz;
    Item z = new Item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pridani);
        PleniSpinn();
        editText = (EditText) findViewById(R.id.editText_nazev_pridani);
        myDB = new DatabaseHelper(this);
        uprav = (ImageButton) findViewById(R.id.bt_edit);
        smaz = (ImageButton) findViewById(R.id.bt_del);
        CRUD = (EditText) findViewById(R.id.et_CRUD);

        uprav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (z.getId() != -1) {
                    boolean test = myDB.updateZ(new Item(z.getId(), CRUD.getText().toString()));
                    if (test == true) {
                        Toast.makeText(PridaniActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        CRUD.setText("");
                        z = new Item();
                        PleniSpinn();
                    } else
                        Toast.makeText(PridaniActivity.this, "Neprovedl se update", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PridaniActivity.this, "Položka neexistuje(nebyla vybraná žádná položka). Nelze upravit.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        smaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (z.getId() != -1) {
                    myDB.deleteZ(z);
                    Toast.makeText(PridaniActivity.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    CRUD.setText("");
                    z = new Item();
                    PleniSpinn();
                } else {
                    Toast.makeText(PridaniActivity.this, "Položka neexistuje(nebyla vybraná žádná položka). Nelze smazat.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner_CRUD);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Item tmp = (Item) spinner.getSelectedItem();
                for (Item i : listik) {
                    if (i.getNazev() == tmp.getNazev()) {
                        z.setId(i.getId());
                        z.setNazev(i.getNazev());
                        CRUD.setText(z.toString());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    EditText editText, CRUD;
    DatabaseHelper myDB;

    public void onClickProvedPridani(View view) {

        try {
            String newEntry = editText.getText().toString();
            if (editText.length() != 0) {
                AddDat(newEntry);
                editText.setText("");
                PleniSpinn();
            } else {
                Toast.makeText(this, "Musis neco zadat", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Položky nesmí být prázdné", Toast.LENGTH_LONG).show();
        }
    }

    public void AddDat(String newEntry) {
        boolean insertdata = myDB.AddData(newEntry);
        if (insertdata == true) {
            Toast.makeText(this, "FINITO", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Chyba zadaní", Toast.LENGTH_LONG).show();
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
        lv = (ListView) findViewById(R.id.lv_CRUD);

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
    }
}
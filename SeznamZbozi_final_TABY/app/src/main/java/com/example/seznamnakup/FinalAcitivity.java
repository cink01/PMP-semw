package com.example.seznamnakup;

import android.database.Cursor;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FinalAcitivity extends AppCompatActivity {

    SectionsPageAdapter mSectionsPageAdapter;
    ViewPager mViewPager;
    ImageButton uprav, smaz;
    Item z = new Item();
    EditText editText, CRUD;
    DatabaseHelper myDB;
    Cursor data;
    List<Item> listik;
    int poziceSp;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_acitivity);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        editText = (EditText) findViewById(R.id.editText_nazev_pridaniT);
        myDB = new DatabaseHelper(this);
        uprav = (ImageButton) findViewById(R.id.imageButton_edit);
        smaz = (ImageButton) findViewById(R.id.imageButton_delete);
        CRUD = (EditText) findViewById(R.id.et_CRUD);
/*
            uprav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (z.getId() != -1) {
                        boolean test = myDB.updateZ(new Item(z.getId(), CRUD.getText().toString()));
                        if (test == true) {
                            Toast.makeText(FinalAcitivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                            CRUD.setText("");
                            z = new Item();
                            PleniSpinn();
                        } else
                            Toast.makeText(FinalAcitivity.this, "Neprovedl se update", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FinalAcitivity.this, "Položka neexistuje(nebyla vybraná žádná položka). Nelze upravit.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            smaz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (z.getId() != -1) {
                        myDB.deleteZ(z);
                        Toast.makeText(FinalAcitivity.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        CRUD.setText("");
                        z = new Item();
                        PleniSpinn();
                    } else {
                        Toast.makeText(FinalAcitivity.this, "Položka neexistuje(nebyla vybraná žádná položka). Nelze smazat.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        PleniSpinn();
        try {
            spinner = (Spinner) findViewById(R.id.spinner_CRUD);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        catch(Exception e){}*/
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Přidat položku");
        adapter.addFragment(new Tab2Fragment(), "Upravit | smazat položku");
        viewPager.setAdapter(adapter);
    }

    public void onClickProvedPridaniT(View view) {
        try {
            editText = (EditText) findViewById(R.id.editText_nazev_pridaniT);
            String newEntry = editText.getText().toString();
            if (editText.length() != 0) {
                AddDat(newEntry);
                editText.setText("");
                PleniSpinn();
            } else {
                Toast.makeText(FinalAcitivity.this, "Musis neco zadat", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(FinalAcitivity.this, "Položky nesmí být prázdné", Toast.LENGTH_LONG).show();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner = (Spinner) findViewById(R.id.spinner_CRUD);
        Item tmp = (Item) spinner.getSelectedItem();
        for (Item i : listik) {
            if (i.getNazev() == tmp.getNazev()) {
                z.setId(i.getId());
                z.setNazev(i.getNazev());
                CRUD.setText(z.toString());
            }
        }
    }

    public void onClickEdit(View view) {
        if (z.getId() != -1) {
            boolean test = myDB.updateZ(new Item(z.getId(), CRUD.getText().toString()));
            if (test == true) {
                Toast.makeText(FinalAcitivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                CRUD.setText("");
                z = new Item();
                PleniSpinn();
            } else
                Toast.makeText(FinalAcitivity.this, "Neprovedl se update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(FinalAcitivity.this, "Položka neexistuje(nebyla vybraná žádná položka). Nelze upravit.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickSmaz(View view) {

    }

    public void AddDat(String newEntry) {
        boolean insertdata = myDB.AddData(newEntry);
        if (insertdata == true) {
            Toast.makeText(FinalAcitivity.this, "FINITO", Toast.LENGTH_LONG).show();
            CRUD = findViewById(R.id.et_CRUD);
            CRUD.setText("");
            PleniSpinn();
        } else {
            Toast.makeText(FinalAcitivity.this, "Chyba zadaní", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickUkonci(View view) {
        finish();
        super.onBackPressed();
    }


    public void PleniSpinn() {
        listik = new ArrayList<>();
        myDB = new DatabaseHelper(this);
        data = myDB.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(FinalAcitivity.this, "prazdna db", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                listik.add(new Item(data.getInt(0), data.getString(1)));
                spinner = (Spinner) findViewById(R.id.spinner_CRUD);
                ArrayAdapter<Item> spinAda = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, listik);

                spinAda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinAda);
            }
        }
    }

    public void PleniSpinn(Item najdi) {
        listik = new ArrayList<>();
        myDB = new DatabaseHelper(this);
        data = myDB.getListContents();

        if (data.getCount() == 0) {
            Toast.makeText(this, "prazdna db", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                listik.add(new Item(data.getInt(0), data.getString(1)));
                spinner = (Spinner) findViewById(R.id.spinner_CRUD);
                ArrayAdapter<Item> spinAda = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, listik);

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

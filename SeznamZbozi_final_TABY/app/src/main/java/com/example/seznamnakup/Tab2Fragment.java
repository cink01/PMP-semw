package com.example.seznamnakup;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Tab2Fragment extends Fragment {
    private static final String TAG = "Tab2Fragment";

    ImageButton uprav, smaz;
    Item z = new Item();
    EditText editText, CRUD;
    DatabaseHelper myDB;
    Cursor data;
    List<Item> listik;
    Spinner spinner;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_fragment, container, false);
        z = new Item();
        editText = (EditText) view.findViewById(R.id.editText_nazev_pridaniT);
        myDB = new DatabaseHelper(getActivity());
        uprav = (ImageButton) view.findViewById(R.id.imageButton_edit);
        smaz = (ImageButton) view.findViewById(R.id.imageButton_delete);
        CRUD = (EditText) view.findViewById(R.id.et_CRUD);

        uprav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (z.getId() != -1) {
                    boolean test = myDB.updateZ(new Item(z.getId(), CRUD.getText().toString()));
                    if (test == true) {
                        Toast.makeText(getActivity(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        CRUD.setText("");
                        z = new Item();
                        PleniSpinn();
                    } else
                        Toast.makeText(getActivity(), "Neprovedl se update", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Položka neexistuje(nebyla vybraná žádná položka). Nelze upravit.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        smaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (z.getId() != -1) {
                    myDB.deleteZ(z);
                    Toast.makeText(getActivity(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                    CRUD.setText("");
                    z = new Item();
                    PleniSpinn();
                } else {
                    Toast.makeText(getActivity(), "Položka neexistuje(nebyla vybraná žádná položka). Nelze smazat.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        spinner = (Spinner) view.findViewById(R.id.spinner_CRUD);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Item tmp = (Item) spinner.getSelectedItem();
                for (Item i : listik) {
                    if (i.getNazev() == tmp.getNazev()) {
                        z.setId(i.getId());
                        z.setNazev(i.getNazev());
                        CRUD.setText(z.toString());
                        return;
                    }
                }
                PleniSpinn();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                PleniSpinn();
            }
        });
        PleniSpinn();
        return view;
    }

    public void PleniSpinn() {
        listik = new ArrayList<>();
        myDB = new DatabaseHelper(getActivity());
        data = myDB.getListContents();
        if (data.getCount() == 0) {
            Toast.makeText(getActivity(), "prazdna db", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                listik.add(new Item(data.getInt(0), data.getString(1)));
                spinner = (Spinner) view.findViewById(R.id.spinner_CRUD);
                ArrayAdapter<Item> spinAda = new ArrayAdapter<Item>(getActivity(), android.R.layout.simple_list_item_1, listik);

                spinAda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinAda);
            }
        }
    }
}

package com.example.seznamnakup;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    Switch s;
    public List<Zbozi> zbozis = Singleton.getInstance().zbozis;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PleniSpinn();
        lv = (ListView) findViewById(R.id.listview_seznam);
        parseXML();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    s = (Switch) findViewById(R.id.switch_us);
                    if (s.isChecked() == true) {
                        zbozis.remove(position);
                    } else {

                    }
                    Refresh();
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_items, menu);
        } catch (Exception ex) {
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_pridat) {
            Intent intent = new Intent(this, PridaniActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            PleniSpinn();
            Refresh();
        }
        catch (Exception ex) {}
    }

    //vložení položky do seznamu
    public void onClickPridej(View view) {
        try {
            Spinner spinner = (Spinner) findViewById(R.id.spinner_items);
            String nazev = spinner.getSelectedItem().toString();
            EditText pocet = findViewById(R.id.editText_pocet);
            EditText cena = findViewById(R.id.editText_cena);
            if (nazev.length() != 0 && Float.parseFloat(pocet.getText().toString()) > 0 && Float.parseFloat(cena.getText().toString()) > 0) {
                Zbozi tmp = new Zbozi(nazev, Float.parseFloat(cena.getText().toString()), Float.parseFloat(pocet.getText().toString()));
                ListView lv = findViewById(R.id.listview_seznam);
                zbozis.add(tmp);
                Refresh();
            } else {
                Toast.makeText(this, "Všechny políčka musí být vyplněna a počty s cenou nenulové", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Nastala chyba při vkládání", Toast.LENGTH_LONG).show();
        }
    }

    //obnovení seznamu a celkové ceny
    private void Refresh() {
        lv = findViewById(R.id.listview_seznam);
        ArrayAdapter<Zbozi> arrayAdapter =
                new ArrayAdapter<Zbozi>(this, android.R.layout.simple_list_item_1, zbozis);
        lv.setAdapter(arrayAdapter);

        TextView celkem = (TextView) findViewById(R.id.tv_CELKEM);
        float tmp = (float) 0.0;
        //Float.parseFloat(celkem.getText().toString());
        for (Zbozi z : zbozis) {
            tmp += z.getCelkem();
        }
        celkem.setText("Celková cena nákupu je " + String.valueOf(tmp) + " Kč");
    }

    //Vložení dat do spinneru
    public void PleniSpinn() {
        List<String> listik = new ArrayList<>();
        myDB = new DatabaseHelper(this);
        Cursor data = myDB.getListContents();

        if (data.getCount() == 0) {
            Toast.makeText(this, "prazdna db", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                listik.add(data.getString(1));
                Spinner spinner = (Spinner) findViewById(R.id.spinner_items);
                ArrayAdapter<String> spinAda = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listik);
                spinAda.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinAda);
            }
        }
        Spinner spinner = (Spinner) findViewById(R.id.spinner_items);
        ArrayAdapter<String> spinAda = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listik);
    }

    //XML SOUBOR
    private void parseXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = this.getResources().openRawResource(R.raw.zbozi);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            processParsing(parser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processParsing(XmlPullParser parser) throws IOException, XmlPullParserException {
        int eventType = parser.getEventType();
        Zbozi curentZbozi = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String eltName = null;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if ("zbozi".equals(eltName)) {
                        curentZbozi = new Zbozi("-", ((float)(-1.1)), ((float)(-1.1)));
                        zbozis.add(curentZbozi);
                    } else if (curentZbozi != null) {
                        if ("nazev".equals(eltName)) {
                            curentZbozi.setNazev(parser.nextText());
                        } else if ("cena".equals(eltName)) {
                            curentZbozi.setCena(Float.parseFloat(parser.nextText()));
                        } else if ("pocet".equals(eltName)) {
                            curentZbozi.setPocet(Float.parseFloat(parser.nextText()));
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        Refresh();
    }
}
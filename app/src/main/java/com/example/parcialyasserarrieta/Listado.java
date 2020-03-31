package com.example.parcialyasserarrieta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Listado extends AppCompatActivity {

    ListView listView;
    ArrayList<String> listado;
    SearchView mySearchView;
    ArrayAdapter<String>adapter;


    Button btnOrdennombre, btnOrdengrupo, btnEliminar, btnInvertir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        btnOrdennombre = (Button)findViewById(R.id.btnordenarnombre);
        btnOrdengrupo = (Button)findViewById(R.id.btnordenargrupo);
        btnEliminar = (Button)findViewById(R.id.btneliminar);
        btnInvertir = (Button)findViewById(R.id.btninvertir);

        mySearchView = (SearchView)findViewById(R.id.searchView);

        listView = (ListView) findViewById(R.id.listView);

        listado = ListaPersonas();
        adapter = new ArrayAdapter<String>(Listado.this,android.R.layout.simple_list_item_1,listado);
        listView.setAdapter(adapter);

        mySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s){
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s){
                adapter.getFilter().filter(s);

                return false;

            }

        });

        btnOrdennombre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listado = ListaPersonasON();
                adapter = new ArrayAdapter<String>(Listado.this,android.R.layout.simple_list_item_1,listado);
                listView.setAdapter(adapter);
                Toast.makeText(Listado.this,"Ordenado por nombre", Toast.LENGTH_SHORT).show();

            }
        });
        btnOrdengrupo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                listado = ListaPersonasOG();
                adapter = new ArrayAdapter<String>(Listado.this,android.R.layout.simple_list_item_1,listado);
                listView.setAdapter(adapter);
                Toast.makeText(Listado.this,"Ordenado por grupo", Toast.LENGTH_SHORT).show();

            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EliminaarBD();
                startActivity(new Intent(Listado.this, MainActivity.class));

            }
        });
        btnInvertir.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            if(listado.isEmpty()){
                Toast.makeText(Listado.this,"Listado vacio", Toast.LENGTH_SHORT).show();
            }else{

                Collections.reverse(listado);
                adapter = new ArrayAdapter<String>(Listado.this,android.R.layout.simple_list_item_1,listado);
                listView.setAdapter(adapter);
                Toast.makeText(Listado.this,"Listado invertido", Toast.LENGTH_SHORT).show();
            }
            }
        });

        

    }



    private void EliminaarBD(){
        BaseHelper helper = new BaseHelper(this, "Demo", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from personas";
        db.execSQL(sql);
        Toast.makeText(this,"Registros eliminados", Toast.LENGTH_SHORT).show();

    }


    private ArrayList<String> ListaPersonas(){
        ArrayList<String> datos= new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this,"Demo", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select id, nombre, telefono, grupo from Personas";
        Cursor c= db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{
                String linea=c.getString(1)+"\nTelefono: "+c.getString(2)+"\n"+c.getString(3);
                datos.add(linea);
            }while(c.moveToNext());

        }
        db.close();
        return datos;
    }

    private ArrayList<String> ListaPersonasON(){
        ArrayList<String> datos= new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this,"Demo", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select id, nombre, telefono, grupo from Personas order by nombre";
        Cursor c= db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{
                String linea=c.getString(1)+"\nTelefono: "+c.getString(2)+"\n"+c.getString(3);
                datos.add(linea);
            }while(c.moveToNext());

        }
        db.close();
        return datos;
    }

    private ArrayList<String> ListaPersonasOG(){
        ArrayList<String> datos= new ArrayList<String>();
        BaseHelper helper = new BaseHelper(this,"Demo", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "select id, nombre, telefono, grupo from Personas order by grupo";
        Cursor c= db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{
                String linea=c.getString(1)+"\nTelefono: "+c.getString(2)+"\n"+c.getString(3);
                datos.add(linea);
            }while(c.moveToNext());

        }
        db.close();
        return datos;
    }



}

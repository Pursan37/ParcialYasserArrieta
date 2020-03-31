package com.example.parcialyasserarrieta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    EditText edi_nombre,edi_telefono;
    Spinner spinner_selecion_grupo;
    Button btnGuardar, btnListar, btnLimpiar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edi_nombre = (EditText) findViewById(R.id.edi_nombre);
        edi_telefono = (EditText) findViewById(R.id.edi_telefono);

        spinner_selecion_grupo = (Spinner) findViewById(R.id.spinner_selecion_grupo);

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnListar = (Button) findViewById(R.id.btnListar);
        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);


        registerForContextMenu(edi_nombre);
        registerForContextMenu(edi_telefono);

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(validar()){
                    guardar(edi_nombre.getText().toString(),edi_telefono.getText().toString(),spinner_selecion_grupo.getSelectedItem().toString());
                    startActivity(new Intent(MainActivity.this, Listado.class));
                }
            }
        });

        btnListar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, Listado.class));
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                edi_nombre.setText("");
                edi_telefono.setText("");
            }
        });

    }

    @Override
    public  void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        int id=v.getId();
        MenuInflater inflater=getMenuInflater();
        switch (id){
            case R.id.edi_nombre:
                inflater.inflate(R.menu.menu_contexto, menu);
                break;

            case R.id.edi_telefono:
                inflater.inflate(R.menu.menu_contexto_listview, menu);
                break;

        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.action_mayus:
                String aux = edi_nombre.getText().toString();
                aux=aux.toUpperCase();
                edi_nombre.setText(aux);
                return true;
            case R.id.action_minus:
                String aux2 = edi_nombre.getText().toString();
                aux2=aux2.toLowerCase();
                edi_nombre.setText(aux2);
                return true;
            case R.id.action_telefono:

                Random random = new Random();

                int a1=3;
                int a2=random.nextInt(3);
                int a3=0;
                int a4=random.nextInt(10);
                int a5=random.nextInt(10);
                int a6=random.nextInt(10);
                int a7=random.nextInt(10);
                int a8=random.nextInt(10);
                int a9=random.nextInt(10);
                int a10=random.nextInt(10);

                String aux3=a1+""+a2+""+a3+""+a4+""+a5+""+a6+""+a7+""+a8+""+a9+""+a10+"";
                edi_telefono.setText(aux3);



                return true;
            default:
                    return super.onContextItemSelected(item);
        }

    }


    @Override
    public void onBackPressed(){
        Toast.makeText(MainActivity.this,"NO ES POSIBLE REGRESAR", Toast.LENGTH_SHORT).show();
    }

    private void guardar(String Nombre, String Telefono, String Grupo){
        BaseHelper helper = new BaseHelper(this,"Demo", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues c= new ContentValues();
            c.put("Nombre",Nombre);
            c.put("Telefono",Telefono);
            c.put("Grupo",Grupo);
            db.insert("PERSONAS",null, c);
            db.close();
            Toast.makeText(this,"Registro insertado", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this,"Error:"+e.getMessage(),  Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validar(){
        boolean retorno=true;
        String c1=edi_nombre.getText().toString();
        String c2=edi_telefono.getText().toString();
        if(c1.isEmpty()){
            edi_nombre.setError("ESTE CAMPO NO PUEDE ESTAR VACIO");
            retorno=false;

        }
        if(c2.isEmpty()){
            edi_telefono.setError("ESTE CAMPO NO PUEDE ESTAR VACIO");
            retorno=false;
        }
        return retorno;
    }


}

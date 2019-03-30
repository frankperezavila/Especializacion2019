package edu.cecar.controladores;

import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText teNombres;
    private TextInputEditText teNacionalidad;
    private TextInputEditText teSexo;
    private TextInputEditText teCategoria;
    private ListView listViewLInvestigacion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        teNombres = findViewById(R.id.teNombres);
        teNacionalidad = findViewById(R.id.teNacionalidad);
        teSexo = findViewById(R.id.teSexo);
        teCategoria = findViewById(R.id.teCategorizado);
        listViewLInvestigacion = findViewById(R.id.listViewLInvestigacion);

        Button btObtenerDatosCVLac = findViewById(R.id.btObtenerDatos);
        btObtenerDatosCVLac.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                extraerDatosCVLAC();

            }
        });
    }

    public void extraerDatosCVLAC() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://scienti.colciencias.gov.co:8081/cvlac/visualizador/generarCurriculoCv.do?cod_rh=0000733180";
                Investigador investigador = ExtraerDatoCVLAC.getDatos(url);
                ArrayList lista = ExtraerDatoCVLAC.getLineasInvestigacion(url);
                adicionarDatosCasillasTexto(investigador);
                adicionarDatosLista(lista);

            }

        }).start();

    }

    public void adicionarDatosCasillasTexto(final Investigador investigador) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                teNombres.setText(investigador.getNombres());
                teNacionalidad.setText(investigador.getNacionalidad());
                teSexo.setText(investigador.getSexo());
                teCategoria.setText(investigador.isCategorizado() ? "Si" : "No");

            }
        });

    }

    public void adicionarDatosLista(final ArrayList<String> lista) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, lista);
                listViewLInvestigacion.setAdapter(adapter);
            }
        });
    }

}

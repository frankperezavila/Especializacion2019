package edu.cecar.controladores;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtraerDatoCVLAC {


    public static Investigador  getDatos(String url) {

        Investigador investigador = null;

        try {


            Document documentoHTML = Jsoup.connect(url).get();

            Element tDatosPersonales = documentoHTML.select("table").get(1);
            Elements filasTabla = tDatosPersonales.select("tr"); //

            int  filaNombre = 0;
            int filaNacionalidad = 2;
            int filaSexo = 3;

            if(filasTabla.size() > 5) {
                filaNombre = 2;
                filaNacionalidad = 4;
                filaSexo = 5;
            }
            //Se obtienen las columnas para cada atributo del invstigador
            String nombre = filasTabla.get(filaNombre).select("td").get(1).text();
            String nacionalidad = filasTabla.get(filaNacionalidad).select("td").get(1).text();
            String sexo = filasTabla.get(filaSexo).select("td").get(1).text();

            //Se crea el objeto investigador
            investigador = new Investigador(nombre, nacionalidad,sexo,true);


        } catch (IOException e) {

            e.printStackTrace();

        }

        return investigador;

    }

    public static ArrayList<String> getLineasInvestigacion(String url){
        ArrayList<String> lineaInvestingacion = new ArrayList<>();
        Document documentoHTML = null;
        Elements listaTablas;
        try {
            documentoHTML = Jsoup.connect(url).get();
            listaTablas = documentoHTML.select("table");
            for (int i = 2; i < listaTablas.size(); i++) {
                Element tr = listaTablas.get(i).select("tr").first();
                if (tr!=null){
                    if(tr.text().equalsIgnoreCase("Líneas de investigación")){
                        Elements listas = listaTablas.get(i).select("li");
                        for (Element element : listas) {
                            lineaInvestingacion.add(element.text());
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineaInvestingacion;
    }
}

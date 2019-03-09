package edu.cecar.controladores;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class ExtraerDatoCVLAC {

    public static Investigador  getDatos(String url) {

        Investigador investigador = null;

        try {

            //Se obtiene el documento HTML
            Document documentoHTML = Jsoup.connect(url).get();

            Element tablas = documentoHTML.select("table").get(1); //Se obtiene la segunda tabla
            Elements filasTabla = tablas.select("tr"); // Se obtienen las filas de la tabla

            //Se obtienen las columnas para cada atributo del invstigador
            String nombre = filasTabla.get(0).select("td").get(1).text();
            String nacionalidad = filasTabla.get(2).select("td").get(1).text();
            String sexo = filasTabla.get(3).select("td").get(1).text();

            //Se crea el objeto investigador
            investigador = new Investigador(nombre, nacionalidad,sexo,true);

        } catch (IOException e) {

            e.printStackTrace();

        }

        return investigador;

    }
}

package com.porfirioads.fractapp.dominio.html;

import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;

import java.util.ArrayList;

/**
 * Esta clase representa un documento html, que es donde se muestran las
 * operaciones, resultados y explicaciones.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class Documento {
    // Inicio de las constantes de generacion de documentos
    public static final String mathAbre = "<math display=\"block\">";
    public static final String mathCierra = "</math>";
    public static final String rowAbre = "<mrow>";
    public static final String rowCierra = "</mrow>";
    public static final String fracAbre = "<mfrac>";
    public static final String fracCierra = "</mfrac>";
    public static final String numAbre = "<mn>";
    public static final String numCierra = "</mn>";
    public static final String opAbre = "<mo>";
    public static final String opCierra = "</mo>";
    public static final String space = "<mspace width=\"5px\" />";
    public static final String tableAbre = "<mtable>";
    public static final String tableCierra = "</mtable>";
    public static final String rAbre = "<mtr>";
    public static final String rCierra = "</mtr>";
    public static final String dAbre = "<mtd>";
    public static final String dCierra = "</mtd>";
    public static final String pAbre = "<p>";
    public static final String pCierra = "</p>";
    // Fin de las constantes de generacion de documentos

    /**
     * Es la cadena correspondiente al body del documento.
     */
    private String body;
    /**
     * Es la cadena correspondiente al head del documento.
     */
    private String head;
    /**
     * Es el tipo de documento.
     */
    private TipoDocumento tipo;
    /**
     * Es la cadena que corresponde al contenido del documento.
     */
    private String contenido;

    /**
     * Constructor que recibe un parametro.
     *
     * @param tipo Es el tipo de documento.
     */
    public Documento(TipoDocumento tipo) {
        this.tipo = tipo;
    }

    /**
     * Establece el head del documento.
     */
    public void setHead() {
        head = "";

        if (tipo != TipoDocumento.RESULTADO_FRASE) {
            head = "<link rel=\"stylesheet\" href=\"mathscribe/jqmath-0.4.3" +
                    ".css\">" +
                    "<script src='mathscribe/jquery-1.4.3.min.js'></script>" +
                    "<script src='mathscribe/jqmath-etc-0.4.3.min.js' " +
                    "charset='utf-8'></script>";
            if (tipo == TipoDocumento.RESULTADO_EXPRESION) {
                head += "<script src=\"mathscribe/jquery.textfill.min" +
                        ".js\"></script>" +
                        "<script type=\"text/javascript\">" +
                        "$(document).ready(function() {" +
                        "document.getElementById('fraction').innerHTML = " +
                        "\"1<br>1\";" +

                        "$('.no-redimensionable').textfill({" +
                        "maxFontPixels: 500" +
                        "});" +
                        "document.getElementById('fraction').innerHTML = '" +
                        contenido + "';" +
                        "M.parseMath(document.body);" +
                        "});" +
                        "</script>" +
                        "<style type=\"text/css\">" +
                        "html, body{" +
                        "height: 90%;" +
                        "}" +
                        ".no-redimensionable" +
                        "{" +
                        "color: black;" +
                        "height: 100%;" +
                        "padding-bottom: 15px;" +
                        "/*overflow-x: scroll;" +
                        "overflow-y: hidden;*/" +
                        "white-space:nowrap;" +
                        "}" +
                        ".flecha {" +
                        "width: 10%;" +
                        "backgroun-color: blue;" +
                        "color: white" +
                        "}" +
                        "</style>";
            }
        }

        head = "<head>" + head + "</head>";
    }

    /**
     * Establece el body del documento.
     */
    public void setBody() {
        body = "";

        if (tipo == TipoDocumento.RESULTADO_EXPRESION) {
            body += "<div class=\"no-redimensionable\">" +
                    "<span id=\"fraction\" class=\"dyntextval\"></span>" +
                    "</div>";
        } else {
            body += contenido;
        }

        body = "<body>" + body + "</body>";
    }

    /**
     * Establece el contenido del documento.
     *
     * @param paso Es el paso donde se encuentra el contenido a colocar.
     */
    public void setContenido(Paso paso) {
        contenido = paso.getContenido();
    }

    /**
     * Establece el contenido del documento.
     *
     * @param pasos Es una lista de pasos que se concatenan para formar el
     *              contenido del documento.
     */
    public void setContenido(ArrayList<Paso> pasos) {
        contenido = "";

        if (tipo == TipoDocumento.PROCEDIMIENTO) {
            for (Paso paso : pasos) {
                if (paso.getTipo() == TipoPaso.expresion) {
                    contenido += paso.getContenido();
                } else {
                    contenido += Documento.pAbre + paso.getContenido() +
                            Documento.pCierra;
                }
            }
        }
    }

    /**
     * Establece el tipo de documento.
     *
     * @param tipo Es el tipo a establecer.
     */
    public void setTipo(TipoDocumento tipo) {
        this.tipo = tipo;
        setHead();
        setBody();
    }

    @Override
    public String toString() {
        setHead();
        setBody();
        return "<!DOCTYPE html>" + "<html>" + head + body + "</html>";
    }

    /**
     * Clona la instancia actual del documento.
     *
     * @return Un documento igual al actual.
     */
    public Documento clone() {
        Documento copia = new Documento(tipo);
        copia.contenido = contenido;
        return copia;
    }

    /**
     * Enumerado que contiene los tipos de documento disponibles.
     */
    public enum TipoDocumento {
        RESULTADO_EXPRESION,
        RESULTADO_FRASE,
        PROCEDIMIENTO
    }
}

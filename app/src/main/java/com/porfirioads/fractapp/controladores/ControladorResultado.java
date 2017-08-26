package com.porfirioads.fractapp.controladores;

import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.utils.GuiUtils;

import java.util.ArrayList;

/**
 * Esta clase se usa como controlador para colocar el resultado y los pasos
 * de la solucion de algun calculo.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class ControladorResultado {
    /**
     * Es la instancia del documento donde se encuentra el resultado.
     */
    public static Documento docResultado;
    /**
     * Es la instancia del documento donde se encuentran los pasos.
     */
    public static Documento docPasos;
    /**
     * Es la webView del resultado.
     */
    public static WebView webViewResultado;
    /**
     * Es la webView de los pasos.
     */
    public static WebView webViewPasos;
    /**
     * Valor booleano que indica si el calculo esta resuelto o no.
     */
    public static boolean resuelto;

    /**
     * Actualiza la vista de la formulacion de una operacion o calculo.
     *
     * @param paso Es el paso que contiene la formulacion actualizada.
     */
    public static void actualizarFormulacion(Paso paso) {
        docResultado.setTipo(Documento.TipoDocumento.RESULTADO_EXPRESION);
        docResultado.setContenido(paso);
        GuiUtils.loadHtml(webViewResultado, docResultado.toString());
    }

    /**
     * Coloca el resultado y pasos de la solucion en los lugares
     * correspondientes.
     */
    public static void colocarResultadoYPasos() {
        Paso resultado = Procedimiento.getResultado();
        ArrayList<Paso> pasos = Procedimiento.getPasos();

        Documento.TipoDocumento tipo;

        if (resultado.getTipo() == TipoPaso.expresion) {
            docResultado.setTipo(Documento.TipoDocumento.RESULTADO_EXPRESION);
        } else {
            docResultado.setTipo(Documento.TipoDocumento.RESULTADO_FRASE);
        }

        docPasos.setContenido(pasos);
        docResultado.setContenido(resultado);
        GuiUtils.loadHtml(webViewPasos, docPasos.toString());
        GuiUtils.loadHtml(webViewResultado, docResultado.toString());

        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, 9.0f);
        webViewResultado.setLayoutParams(param);
        Procedimiento.iniciar();

        resuelto = true;
    }
}

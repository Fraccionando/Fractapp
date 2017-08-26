package com.porfirioads.fractapp.vistas.utils;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.porfirioads.fractapp.vistas.componentes.AutoResizeTextButton;

/**
 * Esta es una clase que contiene utilidades para las GUI.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class GuiUtils {
    /**
     * Constructor vacio para que no se puedan hacer instancias de la clase
     * con 'new'.
     */
    private GuiUtils() {

    }

    /**
     * Carga un documento html en un webView.
     *
     * @param webView Es el webView donde se va a cargar el documento.
     * @param html    Es el documento html a cargar.
     */
    public static void loadHtml(WebView webView, String html) {
        webView.loadDataWithBaseURL("file:///android_asset/", html, "text/html",
                "UTF-8", "");
    }

    /**
     * Redimiensiona el texto de los botones dentro un viewGroup dado.
     *
     * @param parent Es un contenedor donde se buscaran los botones para
     *               redimensionar su texto.
     */
    public static void redimensionarTextoBotones(ViewGroup parent) {
        int hijos = parent.getChildCount();

        for (int i = 0; i < hijos; i++) {
            View hijo = parent.getChildAt(i);

            if (hijo instanceof ViewGroup) {
                redimensionarTextoBotones((ViewGroup) hijo);
            } else if (hijo instanceof AutoResizeTextButton) {
                ((AutoResizeTextButton) hijo).setMaxLines(1);
                ((AutoResizeTextButton) hijo).enableSizeCache(false);
                ((AutoResizeTextButton) hijo).setMinTextSize(1f);
            }
        }
    }
}

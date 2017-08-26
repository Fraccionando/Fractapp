package com.porfirioads.fractapp.vistas.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Es una clase utilitaria usada para mostrar distintos tipos de mensajes en
 * la aplicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class Mensajes {
    /**
     * Constructor privado para que no se puedan hacer instancias de la clase
     * con 'new'.
     */
    private Mensajes() {

    }

    /**
     * Muestra un mensaje por medio de un Toast.
     *
     * @param context Es el contexto donde se mostrara el mensaje.
     * @param texto   Es el texto del mensaje a mostrar.
     */
    public static void mostrar(Context context, String texto) {
        Toast.makeText(context, texto, Toast.LENGTH_LONG).show();
    }

    /**
     * Muestra un mensaje por medio de un Log.
     *
     * @param objeto Es el objeto del cual se mostrara el mensaje por medio
     *               de su metodo toString.
     */
    public static void log(Object objeto) {
        if (objeto == null) {
            Log.i("FRACC", "null");
        } else {
            Log.i("FRACC", objeto.toString());
        }

    }
}

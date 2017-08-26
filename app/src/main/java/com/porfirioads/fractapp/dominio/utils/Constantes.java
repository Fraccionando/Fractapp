package com.porfirioads.fractapp.dominio.utils;

import android.content.res.Resources;

import java.util.Locale;

/**
 * Esta es una clase utilitaria que solo contiene atributos, los cuales son
 * constantes que son accedidas desde varias ubicaciones.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class Constantes {
    // Inicio de declaracion de constantes
    public static final String espacioHtml = "&nbsp;";
    public static Locale LOCALE_ES = new Locale("es", "MX");
    public static Locale LOCALE_EN = new Locale("en", "US");
    public static Resources resources;
    // Fin de declaracion de constantes

    /**
     * Constructor privado para que no se puedan hacer instancias de la clase
     * con 'new'.
     */
    private Constantes() {
    }
}

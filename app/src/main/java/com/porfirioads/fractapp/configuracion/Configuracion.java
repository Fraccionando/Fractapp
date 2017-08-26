package com.porfirioads.fractapp.configuracion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.porfirioads.fractapp.dominio.utils.Constantes;
import com.porfirioads.fractapp.vistas.activities.SplashActivity;

import java.util.Locale;

/**
 * Esta clase contiene aspectos de configuracion cargados de las preferencias
 * de la aplicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class Configuracion {
    /**
     * Es el valor que define si las explicaciones son detalladas.
     */
    public static boolean detallada;

    /**
     * Determina si es las operaciones seran detalladas o no.
     *
     * @return true si las operaciones seran detalladas o false si no lo son.
     */
    public static boolean isDetallada() {
        return detallada;
    }

    public static void refreshLocale(Context context) {
        Locale locale = getLocale(context);
        Locale.setDefault(locale);
        final Resources resources = context.getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        final Configuration configuration = new Configuration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static Locale getLocale(Context context) {
        String idioma = PreferenceManager.getDefaultSharedPreferences
                (context).getString("idioma", "ninguno");

        Locale locale;

        if (idioma.equals("ES")) {
            locale = Constantes.LOCALE_ES;
        } else if (idioma.equals("EN")) {
            locale = Constantes.LOCALE_EN;
        } else {
            locale = Locale.getDefault();
        }

        return locale;
    }

    /**
     * Establece el estado de una activity para determinar en los callbacks
     * al estar en foreground si es que viene de que se minimizo la
     * aplicacion, o bien, que otra app se puso en primer plano y luego se
     * volvio hacia esta.
     *
     * @param context
     * @param property
     * @param value
     */
    public static void setWorkspaceActivityState(Context context, String
            property, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(property, value).commit();
    }

    public static boolean mustResetApp(Context context, String property) {
        String value = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(property, "none");
        return value.equals("none");
    }

}

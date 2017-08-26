package com.porfirioads.fractapp.vistas.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.utils.Constantes;
import com.porfirioads.fractapp.vistas.R;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Es la activity splash que se muestra al iniciar la aplicacion, mientras se
 * cargan la configuracion y datos iniciales de la misma.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarPreferencias();
        setContentView(R.layout.activity_splash);
        lanzarSegundaActivity(PreferenceManager.getDefaultSharedPreferences(this));

        Configuracion.setWorkspaceActivityState(this, "DialogConversion",
                "none");
        Configuracion.setWorkspaceActivityState(this,
                "WorkspaceFragmentActivity", "none");
        Configuracion.setWorkspaceActivityState(this, "DialogComparacion",
                "none");
        Configuracion.setWorkspaceActivityState(this, "DialogNumeros", "none");
        Configuracion.setWorkspaceActivityState(this, "FirstStepsActivity",
                "none");
        Configuracion.setWorkspaceActivityState(this, "ConfiguracionActivity",
                "none");
        Configuracion.setWorkspaceActivityState(this, "SplashActivity",
                "none");
    }

    /**
     * Carga las preferencias de la aplicacion.
     */
    private void cargarPreferencias() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        establecerIdioma(sp);
        establecerExplicacion(sp);

        Field[] fields = R.string.class.getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            try {
                if (!fields[i].getName().equals("$change")) {
                    Integer id = fields[i].getInt(null);
                    Strings.addString(id, getString(id));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Establece el nivel de explicacion de las soluciones.
     *
     * @param sp Son las sharedPreferences donde esta guardado el nivel de
     *           explicacion que se va a establecer.
     */
    private void establecerExplicacion(SharedPreferences sp) {
        String explicacion = sp.getString("explicacion", "DETALLADA");

        if (explicacion.equals("DETALLADA")) {
            Configuracion.detallada = true;
        } else {
            Configuracion.detallada = false;
        }
    }

    /**
     * Establece el idioma de la explicacion.
     *
     * @param sp Son las sharedPreferences donde esta guardado el idioma que
     *           se va a establecer.
     */
    private void establecerIdioma(SharedPreferences sp) {
        Configuracion.refreshLocale(getBaseContext());
    }

    /**
     * Lanza la segunda activity correspondiente, despues de mostrar la
     * SplashActivity.
     */
    private void lanzarSegundaActivity(SharedPreferences sp) {
        final Intent intent;

        String primerUso = sp.getString("primerUso", "SI");
        boolean firstSteps;

        if (primerUso.equals("SI")) {
            firstSteps = true;
        } else {
            firstSteps = false;
        }

        sp.edit().putString("primerUso", "NO").commit();

        if (firstSteps) {
            intent = new Intent(this, FirstStepsActivity.class);
        } else {
            intent = new Intent(this, WorkspaceFragmentActivity.class);
        }

        // Se el lanzamiento de la segunda activity se realiza dentro de un
        // hilo.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                finish();
                startActivity(intent);
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Configuracion.setWorkspaceActivityState(this,
                "SplashActivity", "none");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Configuracion.setWorkspaceActivityState(this, "SplashActivity",
                "destroyed");
    }
}

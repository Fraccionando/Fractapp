package com.porfirioads.fractapp.vistas.activities;

import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.controladores.ControladorOperacion;
import com.porfirioads.fractapp.vistas.utils.Mensajes;
import com.porfirioads.fractapp.vistas.R;

/**
 * Esta es la activity donde se muestra la configuracion de la aplicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class ConfiguracionActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuracion.refreshLocale(getApplicationContext());
        getFragmentManager().beginTransaction().replace(android.R.id.content,
                new PrefFrag()).commit();
        PreferenceManager.setDefaultValues(ConfiguracionActivity.this, R.xml
                .preferences, false);
    }

    /**
     * Este es el fragment donde se encuentran las preferencias.
     */
    public static class PrefFrag extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
//            Log.v("PREFERENCES", getPreferenceScreen().getSharedPreferences()
//                    .getAll().toString());

            Preference idiomaPreference = findPreference("idioma");
            Preference explicacionPreference = findPreference("explicacion");

            idiomaPreference.setOnPreferenceChangeListener(new Preference
                    .OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference,
                                                  Object o) {
                    Mensajes.mostrar(getActivity(), getString(R.string
                            .mensaje_cambio_idioma));
                    return true;
                }
            });

            explicacionPreference.setOnPreferenceChangeListener(new Preference
                    .OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference,
                                                  Object o) {
                    Mensajes.mostrar(getActivity(), getString(R.string
                            .mensaje_cambio_explicacion));
                    Configuracion.detallada = o.toString().equals("DETALLADA") ?
                            true : false;
                    ControladorOperacion.operacion.setDetallada(Configuracion
                            .detallada);
                    return true;
                }
            });
        }
    }
}

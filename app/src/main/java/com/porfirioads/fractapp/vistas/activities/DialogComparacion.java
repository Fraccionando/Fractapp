package com.porfirioads.fractapp.vistas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.controladores.ControladorComparacion;
import com.porfirioads.fractapp.controladores.ControladorResultado;
import com.porfirioads.fractapp.controladores.CurrentState;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.vistas.utils.GuiUtils;
import com.porfirioads.fractapp.vistas.utils.Mensajes;
import com.porfirioads.fractapp.vistas.R;
import com.porfirioads.fractapp.vistas.componentes.AutoResizeTextButton;
import com.porfirioads.fractapp.vistas.componentes.GeneradorFraccion;
import com.porfirioads.fractapp.vistas.listeners.FraccionListener;

import java.util.ArrayList;

/**
 * Este es el cuadro de dialogo empleado para ingresar comparaciones de
 * fracciones, como determinar equivalentes, reciprocas y mayor.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class DialogComparacion extends Activity {
    /**
     * Es la constante que define el resultado que devuelve la activity
     * cuando las acciones se llevan a cabo como es esperado.
     */
    public static final int RESULTADO_COMPLETADO = 0;
    /**
     * Es la constante que define el resultado que devuelve la activity
     * cuando las acciones se cancelaron.
     */
    public static final int RESULTADO_CANCELADO = 1;
    /**
     * Constante para comparacion de fracciones equivalentes.
     */
    public static final String EQUIVALENTES = "equivalentes";
    /**
     * Constante para comparacion de fracciones reciprocas.
     */
    public static final String RECIPROCAS = "reciprocas";
    /**
     * Constante para comparacion de mayor de dos fracciones.
     */
    public static final String MAYOR = "mayor";
    /**
     * Es una lista que contiene las dos fracciones que se van a comparar.
     */
    private ArrayList<Fraccion> fracciones;
    /**
     * Es la instancia del componente que genera las fracciones.
     */
    private GeneradorFraccion generadorFraccion;
    /**
     * Es la WebView donde se muestran las fracciones que se estan formulando.
     */
    private WebView webViewComparacion;
    /**
     * Es la pagina html que se carga en webViewFormulacion.
     */
    private Documento docFormulacion;
    /**
     * Es el boton de aceptar.
     */
    private AutoResizeTextButton botonAceptar;
    /**
     * Es el boton de cancelar.
     */
    private AutoResizeTextButton botonCancelar;
    /**
     * Es el boton de agregar fraccion.
     */
    private AutoResizeTextButton botonAgregarFraccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (Configuracion.mustResetApp(this, "DialogComparacion")) {
                this.finish();
                Intent intent = new Intent(this, SplashActivity.class);
                this.startActivity(intent);
                savedInstanceState = null;
            }
        }

        Configuracion.refreshLocale(getApplicationContext());
        setContentView(R.layout.activity_dialog_comparacion);

        ControladorResultado.resuelto = false;

        webViewComparacion = (WebView) findViewById(R.id.webViewComparacion);
        webViewComparacion.setVerticalScrollBarEnabled(false);
        WebSettings settings = webViewComparacion.getSettings();
        settings.setJavaScriptEnabled(true);

        final String tipo = getIntent().getExtras().getString("TIPO", "");

        generadorFraccion = (GeneradorFraccion) findViewById(R.id
                .generadorFraccion);

        if (savedInstanceState != null) {
            fracciones = CurrentState.fraccionesComparacion;
            docFormulacion = CurrentState.docOperacionDialog;
            generadorFraccion.setParteFraccion(CurrentState.parteFraccion);

            Mensajes.log(fracciones);
            Mensajes.log(docFormulacion);

            GuiUtils.loadHtml(webViewComparacion, docFormulacion.toString());
        } else {
            fracciones = new ArrayList<>();

            docFormulacion = new Documento(Documento.TipoDocumento
                    .RESULTADO_EXPRESION);
        }

        switch (tipo) {
            case DialogComparacion.EQUIVALENTES:
                setTitle(R.string.titulo_equivalentes);
                break;
            case DialogComparacion.RECIPROCAS:
                setTitle(R.string.titulo_reciprocas);
                break;
            case DialogComparacion.MAYOR:
                setTitle(R.string.titulo_mayor);
                break;
        }

        botonAceptar = (AutoResizeTextButton) findViewById(R.id.botonAceptar);
        botonCancelar = (AutoResizeTextButton) findViewById(R.id.botonCancelar);

        generadorFraccion.setBotonRemoverVisible(true);
        generadorFraccion.setFracciones(fracciones);

        generadorFraccion.setPersonalFraccionListener(new FraccionListener() {
            @Override
            public void onFraccionUpdated() {
                ControladorComparacion.updateGUI();
            }

            @Override
            public void onFraccionRemoved() {
                ControladorComparacion.updateGUI();
            }

            @Override
            public void onFraccionReseted() {
                ControladorComparacion.updateGUI();
            }
        });

        botonAgregarFraccion = (AutoResizeTextButton) findViewById(R.id
                .botonAgregarFraccion);
        botonAgregarFraccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladorComparacion.agregarFraccion();
            }
        });

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladorComparacion.resolver(tipo, DialogComparacion.this);
                setResult(RESULTADO_COMPLETADO);
            }
        });

        botonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULTADO_CANCELADO);
                finish();
            }
        });

        ControladorComparacion.generadorFraccion = generadorFraccion;
        ControladorComparacion.docFormulacion = docFormulacion;
        ControladorComparacion.botonAceptar = botonAceptar;
        ControladorComparacion.botonAgregarFraccion = botonAgregarFraccion;
        ControladorComparacion.fracciones = fracciones;
        ControladorComparacion.webViewComparacion = webViewComparacion;

        if (savedInstanceState == null) {
            ControladorComparacion.iniciar();
        } else {
            ControladorComparacion.updateGUI();
        }

        GuiUtils.redimensionarTextoBotones((ViewGroup) findViewById(R.id
                .layoutRoot));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        CurrentState.docOperacionDialog = docFormulacion;
        CurrentState.parteFraccion = generadorFraccion.getParteFraccion();
        CurrentState.fraccionesComparacion = fracciones;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Configuracion.setWorkspaceActivityState(this, "DialogComparacion",
                "none");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Configuracion.setWorkspaceActivityState(this, "DialogComparacion",
                "destroyed");
    }
}

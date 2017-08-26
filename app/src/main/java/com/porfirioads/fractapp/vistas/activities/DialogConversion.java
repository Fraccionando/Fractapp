package com.porfirioads.fractapp.vistas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.controladores.ControladorConversion;
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
 * Este es el cuadro de dialogo empleado para ingresar conversiones de
 * fracciones, como simplificar, convertir a mixta y convertir a impropia.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class DialogConversion extends Activity {
    /**
     * Es la constante que define el resultado que devuelve la activity
     * cuando las acciones se llevan a cabo como es esperado.
     */
    public static final int RESULTADO_COMPLETADO = 2;
    /**
     * Es la constante que define el resultado que devuelve la activity
     * cuando las acciones se cancelaron.
     */
    public static final int RESULTADO_CANCELADO = 3;
    /**
     * Constante para conversion de simplificar fraccion.
     */
    public static final String SIMPLIFICAR = "simplificar";
    /**
     * Constante para conversion de convertir a fraccion mixta.
     */
    public static final String CAM = "cam";
    /**
     * Constante para conversion de convertir a impropia.
     */
    public static final String CAI = "cai";
    /**
     * Es una lista que contiene las fraccion que se va a convertir.
     */
    private ArrayList<Fraccion> fracciones;
    /**
     * Es la instancia del componente que genera las fracciones.
     */
    private GeneradorFraccion generadorFraccion;
    /**
     * Es la WebView donde se muestran la fraccion que se esta formulando.
     */
    private WebView webViewConversion;
    /**
     * Es la pagina html que se carga en webViewConversion.
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (Configuracion.mustResetApp(this, "DialogConversion")) {
                this.finish();
                Intent intent = new Intent(this, SplashActivity.class);
                this.startActivity(intent);
                savedInstanceState = null;
            }
        }

        Configuracion.refreshLocale(getApplicationContext());
        setContentView(R.layout.activity_dialog_conversion);

        ControladorResultado.resuelto = false;

        webViewConversion = (WebView) findViewById(R.id.webViewConversion);
        webViewConversion.setVerticalScrollBarEnabled(false);
        WebSettings settings = webViewConversion.getSettings();
        settings.setJavaScriptEnabled(true);

        final String tipo = getIntent().getExtras().getString("TIPO", "");

        generadorFraccion = (GeneradorFraccion) findViewById(R.id
                .generadorFraccion);

        if (savedInstanceState != null) {
            fracciones = new ArrayList<>();
            fracciones.add(CurrentState.fraccionConversion);
            docFormulacion = CurrentState.docOperacionDialog;
            generadorFraccion.setParteFraccion(CurrentState.parteFraccion);

            Mensajes.log(fracciones);
            Mensajes.log(docFormulacion);

            GuiUtils.loadHtml(webViewConversion, docFormulacion.toString());
        } else {
            fracciones = new ArrayList<>();

            docFormulacion = new Documento(Documento.TipoDocumento
                    .RESULTADO_EXPRESION);
        }

        switch (tipo) {
            case DialogConversion.SIMPLIFICAR:
                setTitle(R.string.titulo_simplificar);
                break;
            case DialogConversion.CAI:
                setTitle(R.string.titulo_cai);
                break;
            case DialogConversion.CAM:
                setTitle(R.string.titulo_cam);
                break;
        }

        botonAceptar = (AutoResizeTextButton) findViewById(R.id.botonAceptar);
        botonCancelar = (AutoResizeTextButton) findViewById(R.id.botonCancelar);

        generadorFraccion.setBotonRemoverVisible(false);
        generadorFraccion.setFracciones(fracciones);

        generadorFraccion.setPersonalFraccionListener(new FraccionListener() {
            @Override
            public void onFraccionUpdated() {
                ControladorConversion.updateGUI();
            }

            @Override
            public void onFraccionRemoved() {
                ControladorConversion.updateGUI();
            }

            @Override
            public void onFraccionReseted() {
                ControladorConversion.updateGUI();
            }
        });

        Mensajes.log("TIPO: " + tipo);

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladorConversion.resolver(tipo, DialogConversion.this);
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

        ControladorConversion.generadorFraccion = generadorFraccion;
        ControladorConversion.docFormulacion = docFormulacion;
        ControladorConversion.botonAceptar = botonAceptar;
        ControladorConversion.fracciones = fracciones;
        ControladorConversion.webViewConversion = webViewConversion;

        if (savedInstanceState == null) {
            ControladorConversion.iniciar();
        } else {
            ControladorConversion.updateGUI();
        }

        GuiUtils.redimensionarTextoBotones((ViewGroup) findViewById(R.id
                .layoutRoot));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        CurrentState.docOperacionDialog = docFormulacion;
        CurrentState.parteFraccion = generadorFraccion.getParteFraccion();
        CurrentState.fraccionConversion = fracciones.get(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Configuracion.setWorkspaceActivityState(this, "DialogConversion",
                "none");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Configuracion.setWorkspaceActivityState(this, "DialogConversion",
                "destroyed");
    }
}

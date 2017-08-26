package com.porfirioads.fractapp.vistas.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.controladores.ControladorNumeros;
import com.porfirioads.fractapp.controladores.CurrentState;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.vistas.utils.GuiUtils;
import com.porfirioads.fractapp.vistas.utils.Mensajes;
import com.porfirioads.fractapp.vistas.R;
import com.porfirioads.fractapp.vistas.componentes.AutoResizeTextButton;

import java.util.Arrays;

/**
 * Este es el cuadro de dialogo empleado para ingresar calculos con numeros,
 * como minimo comun multiplo y maximo comun divisor.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class DialogNumeros extends Activity {
    /**
     * Es la constante que define el resultado que devuelve la activity
     * cuando las acciones se llevan a cabo como es esperado.
     */
    public static final int RESULTADO_COMPLETADO = 4;
    /**
     * Es la constante que define el resultado que devuelve la activity
     * cuando las acciones se cancelaron.
     */
    public static final int RESULTADO_CANCELADO = 5;
    /**
     * Constante para indicar calculo de mcm.
     */
    public static final String MCM = "mcm";
    /**
     * Constante para indicar calculo de mcm.
     */
    public static final String MCD = "mcd";
    /**
     * Aqui se concatena la entrada de datos.
     */
    public StringBuilder numeros;
    /**
     * Es el documento donde se coloca la formulacion.
     */
    public Documento docFormulacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            if (Configuracion.mustResetApp(this, "DialogNumeros")) {
                this.finish();
                Intent intent = new Intent(this, SplashActivity.class);
                this.startActivity(intent);
                savedInstanceState = null;
            }
        }

        Configuracion.refreshLocale(getApplicationContext());
        setContentView(R.layout.activity_dialog_numeros);

        final String tipo = getIntent().getExtras().getString("TIPO", "");

        int[] ids = {R.id.boton0, R.id.boton1, R.id.boton2, R.id.boton3,
                R.id.boton4, R.id.boton5, R.id.boton6, R.id.boton7,
                R.id.boton8, R.id.boton9};
        AutoResizeTextButton[] botonesNumeros = new AutoResizeTextButton[ids
                .length];

        Mensajes.log("BOTONES ANTES INICIALIZAR: " + Arrays.toString
                (botonesNumeros));

        EscuchadorNumero escuchadorNumero = new EscuchadorNumero();

        if (tipo.equals(DialogNumeros.MCM)) {
            setTitle(R.string.titulo_mcm);
        } else if (tipo.equals(DialogNumeros.MCD)) {
            setTitle(R.string.titulo_mcd);
        }

        for (int i = 0; i < botonesNumeros.length; i++) {
            Mensajes.log("Componente con id " + ids[i] + ": " + findViewById
                    (ids[i]));
            botonesNumeros[i] = (AutoResizeTextButton) findViewById(ids[i]);
            botonesNumeros[i].setOnClickListener(escuchadorNumero);
            Mensajes.log("BOTON NUMEROS: " + (i + 1));
        }

        AutoResizeTextButton botonAc = (AutoResizeTextButton) findViewById(R
                .id.botonAc);
        AutoResizeTextButton botonDel = (AutoResizeTextButton) findViewById(R
                .id.botonDel);
        AutoResizeTextButton botonReiniciarNumeros = (AutoResizeTextButton)
                findViewById(R.id
                        .botonReiniciarNumeros);
        AutoResizeTextButton botonRem = (AutoResizeTextButton) findViewById(R
                .id.botonRem);
        AutoResizeTextButton botonAgregarNumero = (AutoResizeTextButton)
                findViewById(R.id
                        .botonAgregarNumero);

        EscuchadorEdicionNumero een = new EscuchadorEdicionNumero();
        botonAc.setOnClickListener(een);
        botonDel.setOnClickListener(een);
        botonReiniciarNumeros.setOnClickListener(een);
        botonRem.setOnClickListener(een);
        botonAgregarNumero.setOnClickListener(een);

        AutoResizeTextButton botonAceptar = (AutoResizeTextButton)
                findViewById(R.id.botonAceptar);
        AutoResizeTextButton botonCancelar = (AutoResizeTextButton)
                findViewById(R.id.botonCancelar);

        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladorNumeros.resolver(tipo, DialogNumeros.this);
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

        WebView webViewNumeros = (WebView) findViewById(R.id.webViewNumeros);
        webViewNumeros.getSettings().setJavaScriptEnabled(true);

        if (savedInstanceState != null) {
            numeros = CurrentState.numerosCalculo;
            docFormulacion = CurrentState.docOperacionDialog;
        } else {
            numeros = new StringBuilder();
            docFormulacion = new Documento(Documento.TipoDocumento
                    .RESULTADO_EXPRESION);
        }

        ControladorNumeros.numeros = numeros;
        ControladorNumeros.botonAceptar = botonAceptar;
        ControladorNumeros.botonReiniciarNumeros = botonReiniciarNumeros;
        ControladorNumeros.webViewNumeros = webViewNumeros;
        ControladorNumeros.docFormulacion = docFormulacion;
        ControladorNumeros.botonesAcDelRem = new
                AutoResizeTextButton[]{botonAc, botonDel,
                botonRem};
        ControladorNumeros.botonAgregarNumero = botonAgregarNumero;
        ControladorNumeros.iniciar();

        GuiUtils.redimensionarTextoBotones((ViewGroup) findViewById(R.id
                .layoutRoot));
    }

    /**
     * Es un escuchador para cuando se hace click en un boton de numero.
     */
    private class EscuchadorNumero implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int numero = 0;

            switch (view.getId()) {
                case R.id.boton0:
                    numero = 0;
                    break;
                case R.id.boton1:
                    numero = 1;
                    break;
                case R.id.boton2:
                    numero = 2;
                    break;
                case R.id.boton3:
                    numero = 3;
                    break;
                case R.id.boton4:
                    numero = 4;
                    break;
                case R.id.boton5:
                    numero = 5;
                    break;
                case R.id.boton6:
                    numero = 6;
                    break;
                case R.id.boton7:
                    numero = 7;
                    break;
                case R.id.boton8:
                    numero = 8;
                    break;
                case R.id.boton9:
                    numero = 9;
                    break;
            }

            ControladorNumeros.concatenarNumero(numero);
        }
    }

    /**
     * Es un escuchador para cuando se hace click en un boton del editor, que
     * no es de un numero.
     */
    private class EscuchadorEdicionNumero implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.botonAc) {
                ControladorNumeros.reiniciarUltimoNumero();
            } else if (view.getId() == R.id.botonDel) {
                ControladorNumeros.eliminarUltimoCaracter();
            } else if (view.getId() == R.id.botonReiniciarNumeros) {
                ControladorNumeros.reiniciarNumeros();
            } else if (view.getId() == R.id.botonRem) {
                ControladorNumeros.removerUltimoNumero();
            } else if (view.getId() == R.id.botonAgregarNumero) {
                ControladorNumeros.agregarNumero();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CurrentState.docOperacionDialog = docFormulacion;
        CurrentState.numerosCalculo = numeros;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Configuracion.setWorkspaceActivityState(this, "DialogNumeros",
                "none");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Configuracion.setWorkspaceActivityState(this, "DialogNumeros",
                "destroyed");
    }
}

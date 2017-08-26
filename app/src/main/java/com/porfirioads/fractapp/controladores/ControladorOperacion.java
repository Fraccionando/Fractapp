package com.porfirioads.fractapp.controladores;

import android.util.Log;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.logica.fracciones.FraccionSimple;
import com.porfirioads.fractapp.dominio.logica.operaciones.Operacion;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.componentes.CustomViewPager;
import com.porfirioads.fractapp.vistas.utils.Mensajes;
import com.porfirioads.fractapp.vistas.componentes.AutoResizeTextButton;
import com.porfirioads.fractapp.vistas.componentes.GeneradorFraccion;

import java.util.ArrayList;

/**
 * Esta clase es el controlador para las operaciones basicas (suma, resta,
 * multiplicacion y division) de la aplicacion, manda resolver las
 * operaciones, genera los documentos html que contienen las formulaciones y
 * pasos de las operaciones y une los datos de los calculos con la GUI de la
 * aplicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class ControladorOperacion {
    /**
     * Es la operacion que se ingresa y resuelve.
     */
    public static Operacion operacion;
    /**
     * Es la instancia del boton igual.
     */
    public static AutoResizeTextButton botonIgual;
    /**
     * Es la instancia del generador de fracciones.
     */
    public static GeneradorFraccion generadorFraccion;
    /**
     * Es un arreglo que contiene las instancias de los botones de las
     * operaciones basicas.
     */
    public static AutoResizeTextButton[] botonesOperacionesBasicas;

    public static WebView webViewResultado;

    public static CustomViewPager viewPager;

    /**
     * Este metodo se ejecuta para iniciar o reiniciar la operacion ingresada.
     */
    public static void iniciarOperacion() {
        Procedimiento.iniciar();
        boolean detallada = Configuracion.isDetallada();

//        Log.d(":::::::::::::", "Detallada: " + detallada);
        operacion = new Operacion(new ArrayList<Fraccion>(),
                new ArrayList<Character>(), detallada);
        generadorFraccion.setFracciones(operacion.getFracciones());
        generadorFraccion.setOperadores(operacion.getOperadores());
        generadorFraccion.updateGUI();
        updateGUI(true);
        CurrentState.resuelto = false;
//        webViewResultado.setLayoutParams(n);
    }

    /**
     * Agrega una nueva fraccion a la operacion que se esta ingresando.
     *
     * @param c Es el operador del nuevo operando.
     */
    public static void agregarFraccion(char c) {
        operacion.getOperadores().add(c);
        operacion.getFracciones().add(new FraccionSimple());
        generadorFraccion.updateGUI();
        generadorFraccion.reiniciarBotonesParte();
        updateGUI(false);
    }

    /**
     * Resuelve la operacion ingresada.
     */
    public static void resolverOperacion() {
        CurrentState.viewPager.setPagingEnabled(true);
        operacion.calcularResultado(true);
        ControladorResultado.colocarResultadoYPasos();
        generadorFraccion.reiniciarBotonesParte();
        iniciarOperacion();
        CurrentState.resuelto = true;
    }

    /**
     * Actualiza la interfaz de usuario conforme a la operacion.
     *
     * @param resuelta Determina si la llamada al metodo corresponde al momento
     *                 en que la operacion ha sido resuelta.
     */
    public static void updateGUI(boolean resuelta) {
        boolean operacionesHabilitados = generadorFraccion.isCompleta();

        for (AutoResizeTextButton boton : botonesOperacionesBasicas) {
            boton.setEnabled(operacionesHabilitados);
        }

        if (operacion.getFracciones().size() > 1) {
            if (generadorFraccion.isCompleta()) {
                botonIgual.setEnabled(true);
            } else {
                botonIgual.setEnabled(false);
            }
        } else {
            botonIgual.setEnabled(false);
        }

        if (!resuelta) {
            ControladorResultado.actualizarFormulacion(new Paso(operacion
                    .toMathDisplay(true), TipoPaso.expresion));
        }

        LinearLayout.LayoutParams param = null;

        if (!viewPager.isPagingEnabled()) {
            param = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, 10.0f);
            webViewResultado.setLayoutParams(param);
        }

    }
}
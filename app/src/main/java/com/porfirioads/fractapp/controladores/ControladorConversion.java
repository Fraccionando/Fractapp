package com.porfirioads.fractapp.controladores;

import android.app.Activity;
import android.webkit.WebView;

import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.utils.GuiUtils;
import com.porfirioads.fractapp.vistas.utils.Mensajes;
import com.porfirioads.fractapp.vistas.activities.DialogConversion;
import com.porfirioads.fractapp.vistas.componentes.AutoResizeTextButton;
import com.porfirioads.fractapp.vistas.componentes.GeneradorFraccion;

import java.util.ArrayList;

/**
 * Esta clase sirve como controlador para las operaciones de conversion de
 * fracciones (simplificar, convertir a mixta y convertir a impropia).
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class ControladorConversion {
    /**
     * Es una lista que contiene las fraccion que se va a convertir.
     */
    public static ArrayList<Fraccion> fracciones;
    /**
     * Es la instancia del componente que genera la fraccion.
     */
    public static GeneradorFraccion generadorFraccion;
    /**
     * Es la WebView donde se muestra la fraccion que se esta formulando.
     */
    public static WebView webViewConversion;
    /**
     * Es la pagina html que se carga en webViewFormulacion.
     */
    public static Documento docFormulacion;
    /**
     * Es el boton de aceptar.
     */
    public static AutoResizeTextButton botonAceptar;
    /**
     * Es la lista de operadores, que aunque no se usan, el componente
     * GeneradorFraccion debe contenerla.
     */
    private static ArrayList<Character> operadores;

    /**
     * Inicializa el controlador para la comparacion.
     */
    public static void iniciar() {
        Procedimiento.iniciar();
        operadores = new ArrayList<>();
        generadorFraccion.setOperadores(operadores);
        fracciones.clear();
        generadorFraccion.updateGUI();
        updateGUI();
    }

    /**
     * Resuelve la comparacion de fracciones.
     *
     * @param tipo     Es el tipo de comparacion a realizar.
     * @param activity Es la activity donde se estaba formulando la comparacion.
     */
    public static void resolver(String tipo, Activity activity) {
        if (tipo.equals(DialogConversion.SIMPLIFICAR)) {
            fracciones.get(0).toFraccionDetallada().simplificar();
        } else if (tipo.equals(DialogConversion.CAM)) {
            fracciones.get(0).toFraccionDetallada().convertirAMixta();
        } else if (tipo.equals(DialogConversion.CAI)) {
            fracciones.get(0).toFraccionDetallada().convertirAImpropia();
        }

        Mensajes.log(Procedimiento.getPasos());

        activity.finish();
    }

    /**
     * Actualiza la vista despues de la interaccion durante la formulacion de
     * la comparacion.
     */
    public static void updateGUI() {
        boolean terminado = generadorFraccion.isCompleta();

        botonAceptar.setEnabled(terminado);

        String math = fracciones.get(0).toMathDisplay(true) + "";

        docFormulacion.setContenido(new Paso(math, TipoPaso.expresion));
        GuiUtils.loadHtml(webViewConversion, docFormulacion.toString());
    }
}

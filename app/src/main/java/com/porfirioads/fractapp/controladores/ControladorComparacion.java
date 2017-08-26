package com.porfirioads.fractapp.controladores;

import android.app.Activity;
import android.webkit.WebView;

import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.logica.comparaciones.Comparaciones;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.logica.fracciones.FraccionSimple;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.utils.GuiUtils;
import com.porfirioads.fractapp.vistas.activities.DialogComparacion;
import com.porfirioads.fractapp.vistas.componentes.AutoResizeTextButton;
import com.porfirioads.fractapp.vistas.componentes.GeneradorFraccion;

import java.util.ArrayList;

/**
 * Esta clase sirve como controlador para las operaciones de comparacion de
 * fracciones (determinar equivalentes, determinar reciprocas y determinar
 * mayor).
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class ControladorComparacion {
    /**
     * Es una lista que contiene las dos fracciones que se van a comparar.
     */
    public static ArrayList<Fraccion> fracciones;
    /**
     * Es la instancia del componente que genera las fracciones.
     */
    public static GeneradorFraccion generadorFraccion;
    /**
     * Es la WebView donde se muestran las fracciones que se estan formulando.
     */
    public static WebView webViewComparacion;
    /**
     * Es la pagina html que se carga en webViewFormulacion.
     */
    public static Documento docFormulacion;
    /**
     * Es el boton de aceptar.
     */
    public static AutoResizeTextButton botonAceptar;
    /**
     * Es el boton de agregar fraccion.
     */
    public static AutoResizeTextButton botonAgregarFraccion;
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
        Fraccion f1 = fracciones.get(0);
        Fraccion f2 = fracciones.get(1);

        if (tipo.equals(DialogComparacion.EQUIVALENTES)) {
            Comparaciones.determinarEquivalentes(f1, f2);
        } else if (tipo.equals(DialogComparacion.RECIPROCAS)) {
            Comparaciones.determinarReciprocas(f1, f2);
        } else if (tipo.equals(DialogComparacion.MAYOR)) {
            Comparaciones.determinarMayor(f1, f2);
        }

        activity.finish();
    }

    /**
     * Agrega la segunda fraccion a la comparacion.
     */
    public static void agregarFraccion() {
        fracciones.add(new FraccionSimple());
        generadorFraccion.reiniciarBotonesParte();
        operadores.add(',');
        updateGUI();
    }

    /**
     * Actualiza la vista despues de la interaccion durante la formulacion de
     * la comparacion.
     */
    public static void updateGUI() {
        boolean todasFracciones = fracciones.size() == 2;
        boolean terminado = todasFracciones && generadorFraccion
                .isCompleta();
        boolean unaTerminada = !todasFracciones && generadorFraccion
                .isCompleta();

        botonAceptar.setEnabled(terminado);
        botonAgregarFraccion.setEnabled(unaTerminada);

        String math = fracciones.get(0).toMathDisplay(true) + Documento
                .opAbre + "," + Documento.opCierra;

        if (fracciones.size() == 2) {
            math += fracciones.get(1).toMathDisplay(true);
        }

        docFormulacion.setContenido(new Paso(math, TipoPaso.expresion));
        GuiUtils.loadHtml(webViewComparacion, docFormulacion.toString());
    }
}

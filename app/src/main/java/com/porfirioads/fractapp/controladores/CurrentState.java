package com.porfirioads.fractapp.controladores;

import android.webkit.WebView;

import com.porfirioads.fractapp.dominio.enumerados.ParteFraccion;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.logica.operaciones.Operacion;
import com.porfirioads.fractapp.vistas.componentes.CustomViewPager;

import java.util.ArrayList;

/**
 * Esta clase tiene campos que se actualizan cada vez que se realiza alguna
 * accion con la aplicacion, para guardar el estado de las actividades.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class CurrentState {
    /**
     * Es la webView donde se muestran las operaciones y resultados en el
     * fragment de calculadora.
     */
    public static WebView webViewOperacion;
    /**
     * Es la webView donde se muestran los datos a ingresar en alguna de las
     * operaciones que usan un dialog.
     */
    public static WebView webViewOperacionDialog;
    /**
     * Es la webView donde se muestran los pasos de la solucion de las
     * operaciones.
     */
    public static WebView webViewPasos;
    /**
     * Es el documento html mostrado en webViewOperacionDialog.
     */
    public static Documento docOperacionDialog;
    /**
     * Es el documento html mostrado en webViewPasos.
     */
    public static Documento docPasos;
    /**
     * Es el documento html mostrado en webViewOperacion.
     */
    public static Documento docOperacion;
    /**
     * Es la operacion basica que se esta ingresando, si no tiene fracciones
     * ni operadores significa que aun no se ingresa nada, o que lo que se
     * esta resolviendo es una funcion.
     */
    public static Operacion operacion;
    /**
     * Es la parte de la fraccion que se esta ingresando en la operacion actual.
     */
    public static ParteFraccion parteFraccion;
    /**
     * Bandera que indica si la operacion, comparacion, conversion o calculo
     * ya fue resuelto.
     */
    public static boolean resuelto;
    /**
     * Lista que contiene las fracciones que se estan ingresando para una
     * comparacion.
     */
    public static ArrayList<Fraccion> fraccionesComparacion;
    /**
     * Fraccion que se esta ingresando para una conversion.
     */
    public static Fraccion fraccionConversion;
    /**
     * Cadena con los numeros que se estan ingresando para el calculo del mcm
     * o del mcd.
     */
    public static StringBuilder numerosCalculo;
    /**
     * Es el viewPager actual, que define si la paginacion esta habilitada o no.
     */
    public static CustomViewPager viewPager;

}

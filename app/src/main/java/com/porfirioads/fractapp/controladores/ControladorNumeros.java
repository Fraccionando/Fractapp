package com.porfirioads.fractapp.controladores;

import android.util.Log;
import android.webkit.WebView;

import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.logica.calculos.Calculos;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.vistas.utils.GuiUtils;
import com.porfirioads.fractapp.vistas.activities.DialogNumeros;
import com.porfirioads.fractapp.vistas.componentes.AutoResizeTextButton;

/**
 * Esta clase sirve como controlador para los calculos auxiliares con numeros
 * (minimo comun multiplo y maximo comun divisor).
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class ControladorNumeros {
    public static StringBuilder numeros;
    public static AutoResizeTextButton botonAgregarNumero;
    public static AutoResizeTextButton botonReiniciarNumeros;
    public static AutoResizeTextButton botonAceptar;
    public static AutoResizeTextButton[] botonesAcDelRem;
    public static WebView webViewNumeros;
    public static Documento docFormulacion;
    /**
     * Es el indice del numero que actualmente se esta ingresando.
     */
    private static int indiceActual;

    /**
     * Inicia el controlador.
     */
    public static void iniciar() {
        indiceActual = 0;
        updateGUI();
    }

    /**
     * Concatena un digito a la entrada de datos.
     *
     * @param digito Es el digito a concatenar.
     */
    public static void concatenarNumero(int digito) {
        numeros.append(digito);
        updateGUI();
    }

    /**
     * Reinicia el ultimo numero que se ingreso.
     */
    public static void reiniciarUltimoNumero() {
        int ultimaComa = numeros.lastIndexOf(",");
        String actualizado = numeros.substring(0, ultimaComa + 1);
        numeros.setLength(0);
        numeros.append(actualizado);
        updateGUI();
    }

    /**
     * Remueve el ultimo caracter de la entrada ya sea digito o coma.
     */
    public static void eliminarUltimoCaracter() {
        String actualizado = numeros.substring(0, numeros.length() - 1);
        numeros.setLength(0);
        numeros.append(actualizado);
        updateGUI();
    }

    /**
     * Reinicia toda la entrada de datos.
     */
    public static void reiniciarNumeros() {
        numeros.setLength(0);
        updateGUI();
    }

    /**
     * Remueve el ultimo numero de la entrada (de la ultima coma en delante o
     * el numero entero si es el primero).
     */
    public static void removerUltimoNumero() {
        int ultimaComa = numeros.lastIndexOf(",");
        String actualizado;

        if (ultimaComa != -1) {
            actualizado = numeros.substring(0, ultimaComa);
        } else {
            actualizado = "";
        }

        numeros.setLength(0);
        numeros.append(actualizado);
        updateGUI();
    }

    /**
     * Agrega un nuevo numero a la entrada (le antepone una coma).
     */
    public static void agregarNumero() {
        numeros.append(",");
        updateGUI();
    }

    /**
     * Resuelve el calculo de numeros.
     *
     * @param tipo          Es el tipo de calculo ya sea mcm o mcd.
     * @param dialogNumeros Es el dialogo que debe cerrar al completar el
     *                      calculo.
     */
    public static void resolver(String tipo, DialogNumeros dialogNumeros) {
        if (tipo.equals(DialogNumeros.MCM)) {
            Calculos.mcmDetallado(getNumeros());
        } else if (tipo.equals(DialogNumeros.MCD)) {
            Calculos.mcdDetallado(getNumeros());
        }

        dialogNumeros.finish();
    }

    /**
     * Obtiene los numeros ingresados.
     *
     * @return Arreglo de longs con todos los numeros.
     */
    public static long[] getNumeros() {
        String numerosString = "";
        if (numeros != null) {
            numerosString = numeros.toString();
        }

        if (numerosString.isEmpty()) {
            return null;
        } else {
            String[] numerosStrings = numerosString.split(",");
            long[] numerosLong = new long[numerosStrings.length];

            for (int i = 0; i < numerosLong.length; i++) {
                numerosLong[i] = Long.parseLong(numerosStrings[i]);
            }

            return numerosLong;
        }
    }

    /**
     * Devuelve la cadena con la representacion matematica de los numeros
     * para mostrar en los documentos html.
     *
     * @return codigo mathMl de los numeros.
     */
    public static String getNumerosMathDisplay() {
        StringBuilder math = new StringBuilder();
        long[] listaNumeros = getNumeros();

        if (listaNumeros == null) {
            return "";
        } else {
            math.append(listaNumeros[0]);

            for (int i = 1; i < listaNumeros.length; i++) {
                math.append(Documento.opAbre + "," + Documento.opCierra +
                        listaNumeros[i]);
            }

            if (numeros.charAt(numeros.length() - 1) == ',') {
                math.append(Documento.opAbre + "," + Documento.opCierra);
            }

            return math.toString();
        }
    }

    /**
     * Actualiza la GUI del dialogo de numeros.
     */
    public static void updateGUI() {
        boolean puedeAgregarNumero = numeros != null && numeros.length() > 0 &&
                !numeros.toString().endsWith(",");
//        boolean puedeAgregarNumero = numeros.length() > 0 &&
//                !numeros.toString().endsWith(",");
        boolean completo = getNumeros() != null && getNumeros().length >= 2;
//        boolean completo = getNumeros() != null && getNumeros().length >= 2;
        boolean noVacio = numeros != null && numeros.length() > 0;
//        boolean noVacio = numeros.length() > 0;

        botonAgregarNumero.setEnabled(puedeAgregarNumero);
        botonAceptar.setEnabled(completo);

        for (AutoResizeTextButton AutoResizeTextButton : botonesAcDelRem) {
            AutoResizeTextButton.setEnabled(noVacio);
        }

        botonReiniciarNumeros.setEnabled(noVacio);
        docFormulacion.setContenido(new Paso(getNumerosMathDisplay(),
                TipoPaso.expresion));
        GuiUtils.loadHtml(webViewNumeros, docFormulacion.toString());
    }
}

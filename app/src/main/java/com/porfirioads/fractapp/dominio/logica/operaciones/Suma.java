package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.logica.fracciones.FraccionSimple;
import com.porfirioads.fractapp.dominio.procedimiento.FraccionPaso;

import java.util.ArrayList;

/**
 * Esta clase abstracta es la base para una operacion de suma o resta, en sus
 * modalidades que generan explicaciones ya sean detalladas o simples.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public abstract class Suma extends Operacion {

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public Suma(ArrayList<Fraccion> fracciones,
                ArrayList<Character> operadores) {
        super(fracciones, operadores);
    }

    @Override
    protected abstract void convertirMixtasAImpropias();

    /**
     * Determina si los denominadores de las fracciones son iguales.
     *
     * @return true si los denominadores son iguales o false si son diferentes.
     */
    protected boolean sonIgualesLosDenominadores() {

        for (int i = 1; i < fracciones.size(); i++) {
            if (fracciones.get(i).getDenominador()
                    != fracciones.get(0).getDenominador()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Devuelve los denominadores de las fracciones.
     *
     * @return Un arreglo con los denominadores.
     */
    protected long[] getDenominadores() {
        long[] denominadores = new long[fracciones.size()];

        for (int i = 0; i < fracciones.size(); i++) {
            denominadores[i] = fracciones.get(i).getDenominador();
        }

        return denominadores;
    }

    /**
     * Devuelve la formulacion latex del paso donde se suman los numeradores de
     * las fracciones.
     *
     * @return Una cadena con el codigo latex correspondiente.
     */
    protected String latexSumaNumeradores() {
        String latex;
        String numerador = "";
        String denominador;

        for (int i = 0; i < fracciones.size(); i++) {
            numerador += fracciones.get(i).getNumerador() + "";

            if (i < fracciones.size() - 1) {
                numerador += " " + operadores.get(i) + " ";
            }
        }

        denominador = fracciones.get(0).getDenominador() + "";

        latex = new FraccionPaso(numerador, denominador).toMathDisplay();

        return latex;
    }

    /**
     * Devuelve el resultado de la suma de los numeradores.
     *
     * @return Una Fraccion con el resultado.
     */
    protected Fraccion calcularResultadoSumaNumeradores() {
        long sumaNumeradores = fracciones.get(0).getNumerador();

        for (int i = 1; i < fracciones.size(); i++) {
            if (operadores.get(i - 1) == '+') {
                sumaNumeradores += fracciones.get(i).getNumerador();
            } else {
                sumaNumeradores -= fracciones.get(i).getNumerador();
            }
        }

        return new FraccionSimple(sumaNumeradores, fracciones.get(0)
                .getDenominador());
    }

    /**
     * Convierte las fracciones a equivalentes por medio del mcm.
     *
     * @param mcm Es el mcm auxiliar para la conversion.
     */
    protected void convertirAEquivalentesMcm(long mcm) {
        long numerador;
        long denominador;

        for (int i = 0; i < fracciones.size(); i++) {
            numerador = mcm / fracciones.get(i).getDenominador()
                    * fracciones.get(i).getNumerador();
            denominador = mcm;

            fracciones.get(i).setNumerador(numerador);
            fracciones.get(i).setDenominador(denominador);
        }
    }

}

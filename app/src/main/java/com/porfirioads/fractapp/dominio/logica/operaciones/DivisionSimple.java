package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.logica.fracciones.FraccionSimple;
import com.porfirioads.fractapp.dominio.procedimiento.FraccionPaso;

import java.util.ArrayList;

/**
 * Esta clase abstracta es la base para una operacion de division de dos
 * fracciones, en sus modalidades que generan explicaciones ya sean detalladas o
 * simples.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public abstract class DivisionSimple extends Operacion {

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public DivisionSimple(ArrayList<Fraccion> fracciones,
                          ArrayList<Character> operadores) {
        super(fracciones, operadores);
    }

    @Override
    protected abstract void convertirMixtasAImpropias();

    /**
     * Devuelve el codigo latex correspondiente al paso donde se indican las
     * multiplicaciones cruzadas que se hacen para resolver la division.
     *
     * @return Cadena con el codigo latex.
     */
    protected String latexMultiplicacionesCruzadas() {
        String latex;
        long n1 = fracciones.get(0).getNumerador();
        long d1 = fracciones.get(0).getDenominador();
        long n2 = fracciones.get(1).getNumerador();
        long d2 = fracciones.get(1).getDenominador();

        String numerador = n1 + " * " + d2;
        String denominador = d1 + " * " + n2;

        latex = new FraccionPaso(numerador, denominador).toMathDisplay();

        return latex;
    }

    /**
     * Devuelve el resultado de las multiplicaciones cruzadas.
     *
     * @return Una Fraccion con el resultado de las multiplicaciones.
     */
    protected Fraccion resultadoMultiplicacionCruzada() {
        long n1 = fracciones.get(0).getNumerador();
        long d1 = fracciones.get(0).getDenominador();
        long n2 = fracciones.get(1).getNumerador();
        long d2 = fracciones.get(1).getDenominador();

        long numRes = n1 * d2;
        long denRes = d1 * n2;

        return new FraccionSimple(numRes, denRes);
    }
}

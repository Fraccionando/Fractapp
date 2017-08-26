package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;

import java.util.ArrayList;

/**
 * Esta clase abstracta es la base para una operacion en la que involucran
 * multiplicaciones y divisiones de fracciones, en sus modalidades que generan
 * explicaciones ya sean detalladas o simples.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public abstract class MultiplicacionDivision extends Operacion {

    /**
     * En este grupo de fracciones entran la primera fraccion y todas las que
     * estan multiplicando.
     */
    private ArrayList<Fraccion> multiplicaciones;
    /**
     * En este grupo de fracciones entran las fracciones que estan dividiendo.
     */
    private ArrayList<Fraccion> divisiones;

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public MultiplicacionDivision(ArrayList<Fraccion> fracciones,
                                  ArrayList<Character> operadores) {
        super(fracciones, operadores);
    }

    @Override
    protected abstract void convertirMixtasAImpropias();

    /**
     * Se encarga de crear los grupos de multiplicaciones y divisiones, para
     * resolverlos por separado.
     */
    private void agrupar() {
        multiplicaciones = new ArrayList<>();
        divisiones = new ArrayList<>();
        multiplicaciones.add(fracciones.get(0));

        for (int i = 1; i < fracciones.size(); i++) {
            if (operadores.get(i - 1) == '*') {
                multiplicaciones.add(fracciones.get(i));
            } else {
                divisiones.add(fracciones.get(i));
            }
        }
    }

    /**
     * Devuelve el codigo latex correspondiente al paso donde se agrupan las
     * multiplicaciones y divisiones de la operacion.
     *
     * @return El codigo latex del paso.
     */
    public String latexAgruparMultiplicaciones() {
        agrupar();

        String latex = "(" + multiplicaciones.get(0).toMathDisplay(false);

        for (int i = 1; i < multiplicaciones.size(); i++) {
            latex += " * ";
            latex += multiplicaciones.get(i).toMathDisplay(false);
        }

        latex += ") ÷ (" + divisiones.get(0).toMathDisplay(false);

        for (int i = 1; i < divisiones.size(); i++) {
            latex += " ÷ ";
            latex += divisiones.get(i).toMathDisplay(false);
        }

        latex += ")";

        return latex;
    }

    /**
     * Devuelve el resultado de las multiplicaciones y divisiones de la
     * operacion.
     *
     * @param operacion Es la operacion de la que se obtendra el resultado.
     * @return Una Fraccion con el resultado.
     */
    public Fraccion resultadoMultiplicacionesDivisiones(
            MultiplicacionDivision operacion) {
        ArrayList<Character> operadoresMult = new ArrayList<>();
        ArrayList<Character> operadoresDiv = new ArrayList<>();

        // Llena los operadores dependiendo del numero de multiplicaciones y
        // divisiones
        // Hay (numDeMultiplicaciones - 1) operadores
        for (int i = 1; i < multiplicaciones.size(); i++) {
            operadoresMult.add('*');
        }

        // Hay (numDeDivisiones) operadores porque tambien se toma en cuenta el 
        // resultado de las multiplicaciones
        for (int i = 0; i < divisiones.size(); i++) {
            operadoresDiv.add('÷');
        }

        Fraccion resultadoMult = null;
        Fraccion resultadoDiv = null;

        if (operacion instanceof MultiplicacionDivisionMuyDetallada) {
            resultadoMult = new MultiplicacionMuyDetallada(multiplicaciones,
                    operadoresMult).calcularResultado(false);
        } else {
            resultadoMult = new MultiplicacionPocoDetallada(multiplicaciones,
                    operadoresMult).calcularResultado(false);
        }

        divisiones.add(0, resultadoMult);

        if (operacion instanceof MultiplicacionDivisionMuyDetallada) {
            if (divisiones.size() > 2) {
                resultadoDiv = new DivisionMultipleMuyDetallada(divisiones,
                        operadoresDiv).calcularResultado(true);
            } else {
                resultadoDiv = new DivisionSimpleMuyDetallada(divisiones,
                        operadoresDiv).calcularResultado(true);
            }
        } else {
            if (divisiones.size() > 2) {
                resultadoDiv = new DivisionMultiplePocoDetallada(divisiones,
                        operadoresDiv).calcularResultado(true);
            } else {
                resultadoDiv = new DivisionSimplePocoDetallada(divisiones,
                        operadoresDiv).calcularResultado(true);
            }
        }

        return resultadoDiv;
    }
}

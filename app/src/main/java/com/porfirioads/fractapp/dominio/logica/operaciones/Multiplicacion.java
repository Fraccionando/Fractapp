package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.logica.fracciones.FraccionSimple;
import com.porfirioads.fractapp.dominio.procedimiento.FraccionPaso;

import java.util.ArrayList;

/**
 * Esta clase abstracta es la base para una operacion de multiplicacion, en sus
 * modalidades que generan explicaciones ya sean detalladas o simples.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public abstract class Multiplicacion extends Operacion {

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public Multiplicacion(ArrayList<Fraccion> fracciones,
                          ArrayList<Character> operadores) {
        super(fracciones, operadores);
    }

    /**
     * Devuelve el codigo latex correspondiente al paso de la indicacion de las
     * multiplicaciones de los numeradores y denominadores de las fracciones.
     *
     * @return Una cadena con el codigo latex correspondiente.
     */
    protected String latexMultiplicacionMiembros() {
        String latex;
        String numerador = fracciones.get(0).getNumerador() + "";
        String denominador = fracciones.get(0).getDenominador() + "";

        for (int i = 1; i < fracciones.size(); i++) {
            numerador += " * ";
            numerador += fracciones.get(i).getNumerador();
            denominador += " * ";
            denominador += fracciones.get(i).getDenominador();
        }

        latex = new FraccionPaso(numerador, denominador).toMathDisplay();

        return latex;
    }

    /**
     * Devuelve la fraccion correspondiente al resultado de la multiplicacion.
     *
     * @return El resultado de la operacion.
     */
    public Fraccion resultadoMultiplicacion() {
        long productoNumeradores = 1;
        long productoDenominadores = 1;

        for (int i = 0; i < fracciones.size(); i++) {
            productoNumeradores *= fracciones.get(i).getNumerador();
            productoDenominadores *= fracciones.get(i).getDenominador();
        }

        Fraccion r = new FraccionSimple(productoNumeradores,
                productoDenominadores);

        return r;
    }

    @Override
    protected abstract void convertirMixtasAImpropias();

}

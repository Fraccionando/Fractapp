package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;

import java.util.ArrayList;

/**
 * Esta clase abstracta es la base para una operacion de division de mas de dos
 * fracciones, en sus modalidades que generan explicaciones ya sean detalladas o
 * simples.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public abstract class DivisionMultiple extends Operacion {

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public DivisionMultiple(ArrayList<Fraccion> fracciones,
                            ArrayList<Character> operadores) {
        super(fracciones, operadores);
    }

    @Override
    protected abstract void convertirMixtasAImpropias();

    /**
     * Organiza la resolucion de las operaciones, separando las fracciones en
     * pares, para resolverlas como divisiones simples.
     *
     * @param clase Es la instancia de la operacion, para determinar si se
     *              trabajara con fracciones simples o detalladas.
     */
    public void separarPorPares(DivisionMultiple clase) {
        resultado = fracciones.get(0);

        ArrayList<Character> operadorDivSimple = new ArrayList<>();
        operadorDivSimple.add('÷');

        for (int i = 1; i < fracciones.size(); i++) {
            ArrayList<Fraccion> dosFracciones = new ArrayList<>();
            dosFracciones.add(resultado);
            dosFracciones.add(fracciones.get(i));

            if (clase instanceof DivisionMultipleMuyDetallada) {
                resultado = new DivisionSimpleMuyDetallada(dosFracciones,
                        operadorDivSimple).calcularResultado(false);
            } else {
                resultado = new DivisionSimplePocoDetallada(dosFracciones,
                        operadorDivSimple).calcularResultado(false);
            }
        }
    }

}

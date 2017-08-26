package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

import java.util.ArrayList;

/**
 * Esta clase abstracta es la base para una operacion con fracciones donde se
 * involucre la precedencia de operadores (suma y multiplicacion, suma y
 * division, etc.), en sus modalidades que generan explicaciones ya sean
 * detalladas o simples.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public abstract class Mixta extends Operacion {

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public Mixta(ArrayList<Fraccion> fracciones,
                 ArrayList<Character> operadores) {
        super(fracciones, operadores);
    }

    @Override
    protected abstract void convertirMixtasAImpropias();

    /**
     * Devuelve el codigo latex correspondiente a la agrupacion de operaciones
     * hecha segun la precedencia de los operadores.
     *
     * @return Cadena con el codigo latex.
     */
    protected String latexAgrupacionPrecedencia() {
        String latex = "";

        ArrayList<ArrayList<Fraccion>> gruposFracciones = new ArrayList<>();

        gruposFracciones.add(new ArrayList<Fraccion>());
        gruposFracciones.get(0).add(fracciones.get(0));
        int indGrupo = 0;

// Agrupa las fracciones
        for (int i = 0; i < operadores.size(); i++) {
            if (operadores.get(i) == '+' || operadores.get(i) == '-') {
                indGrupo++;
                gruposFracciones.add(new ArrayList<Fraccion>());
            }

            gruposFracciones.get(indGrupo).add(fracciones.get(i + 1));
        }

// Genera la cadena latex
        int operador = 0;

        for (int i = 0; i < gruposFracciones.size(); i++) {
            latex += "(";

            for (int j = 0; j < gruposFracciones.get(i).size(); j++) {
                latex += gruposFracciones.get(i).get(j).toMathDisplay(false);

                if (operador < operadores.size()) {
                    latex += operadores.get(operador);
                    operador++;
                }
            }

            char ultimoCar = latex.charAt(latex.length() - 1);

            if (ultimoCar == '+' || ultimoCar == '-') {
                latex = latex.substring(0, latex.length() - 1);
                latex += ")";
                latex += ultimoCar;
            } else {
                latex += ")";
            }
        }

        return latex;
    }

    /**
     * Obtiene el numero de sumas o restas incluidas en la operacion.
     *
     * @return Cantidad de sumas o restas.
     */
    protected int getNumSumas() {
        int numSumas = 0;

        for (Character o : operadores) {
            if (o == '+' || o == '-') {
                numSumas++;
            }
        }

        return numSumas;
    }

    /**
     * Llena las listas de operadores con los operadores correspondientes, segun
     * la precedencia (suma y resta o multiplicacion y division).
     *
     * @param operadoresSumaResta Es la lista donde se agregaran los operadores
     *                            de suma y resta.
     * @param operadoresGrupo     Es la lista donde se agregaran los
     *                            operadores de
     *                            multiplicacion y division.
     */
    protected void setOperadores(ArrayList<Character> operadoresSumaResta,
                                 ArrayList<Character>[] operadoresGrupo) {
        int indGrupo = 0;

        for (Character o : operadores) {
            if (o == '+' || o == '-') {
                operadoresSumaResta.add(o);
                indGrupo++;
            } else {
                if (operadoresGrupo[indGrupo] == null) {
                    operadoresGrupo[indGrupo] = new ArrayList<>();
                }
                operadoresGrupo[indGrupo].add(o);
            }
        }
    }

    /**
     * Resuelve el grupo de fracciones, como un calculo parcial de toda la
     * operacion.
     *
     * @param muyExplicado Establece si es con un procedimiento detallado o no.
     * @param fracciones   Es la lista de fracciones.
     * @param operadores   Es la lista de operadores.
     * @return Una Fraccion con el resultado de la operacion.
     */
    protected Fraccion resolverDeterminandoTipo(boolean muyExplicado,
                                                ArrayList<Fraccion>
                                                        fracciones,
                                                ArrayList<Character>
                                                        operadores) {
        Operacion operacion
                = new Operacion(fracciones, operadores, muyExplicado);
        return operacion.calcularResultado(false);
    }

    /**
     * Resuelve las sumas y restas finales que quedan despues de resolver las
     * operaciones con mayor precedencia (multiplicaciones y divisiones).
     *
     * @param fracciones Es la lista de fracciones de la operacion.
     * @param operadores Son los operadores de la operacion.
     * @return Fraccion con el resultado de la operacion.
     */
    protected Fraccion resolverSumasYRestasFinales(
            ArrayList<Fraccion> fracciones, ArrayList<Character> operadores) {
        boolean muyDetallada = (this instanceof MixtaMuyDetallada);

        Operacion sumaResta = (muyDetallada) ? new SumaMuyDetallada(fracciones,
                operadores) : new SumaPocoDetallada(fracciones, operadores);

        if (this instanceof MixtaMuyDetallada) {
            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.MIX_SX_SUMRES), TipoPaso.string));
        } else {
            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.MIX_NX_SUMRES), TipoPaso.string));
        }

        return sumaResta.calcularResultado(true);
    }

    /**
     * Devuelve el resultado de la operacion mixta.
     *
     * @return Fraccion con el resultado.
     */
    protected Fraccion resultadoMixta() {
        ArrayList<Character> operadoresSumaResta = new ArrayList<>();

        @SuppressWarnings("unchecked")
        ArrayList<Character>[] operadoresGrupos
                = new ArrayList[getNumSumas() + 1];
        ArrayList<ArrayList<Fraccion>> gruposFracciones = new ArrayList<>();

// Se establecen los operadores de acuerdo a los grupos que se deben realizar.
        setOperadores(operadoresSumaResta, operadoresGrupos);

// Agrega la primer fraccion al primer grupo por default
        gruposFracciones.add(new ArrayList<Fraccion>());
        gruposFracciones.get(0).add(fracciones.get(0));
        int indGrupo = 0;

// Agrupa las fracciones
        for (int i = 0; i < operadores.size(); i++) {
            if (operadores.get(i) == '+' || operadores.get(i) == '-') {
                indGrupo++;
                gruposFracciones.add(new ArrayList<Fraccion>());
            } else {

            }

            gruposFracciones.get(indGrupo).add(fracciones.get(i + 1));
        }

// RESOLUCION DE LAS OPERACIONES
        ArrayList<Fraccion> resultadosGrupos = new ArrayList<>();

        if (this instanceof MixtaMuyDetallada) {
            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.MIX_SX_PAREN), TipoPaso.string));
        }

        for (int i = 0; i < gruposFracciones.size(); i++) {
            if (operadoresGrupos[i] == null) {
                resultadosGrupos.add(gruposFracciones.get(i).get(0));
            } else {
                resultadosGrupos.add(resolverDeterminandoTipo(
                        this instanceof MixtaMuyDetallada,
                        gruposFracciones.get(i), operadoresGrupos[i]));
            }
        }

        resultado = resolverSumasYRestasFinales(resultadosGrupos,
                operadoresSumaResta);

        return resultado;
    }
}

package com.porfirioads.fractapp.dominio.logica.fracciones;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.calculos.Calculos;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

/**
 * Esta clase es una implementacion de la clase abstracta Fraccion, donde las
 * operaciones realizadas no generan ningun tipo de explicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class FraccionSimple extends Fraccion {

    /**
     * Constructor que recibe tres parametros.
     *
     * @param entero  Es la parte entera de la fraccion.
     * @param numerador   Es el numerador de la fraccion.
     * @param denominador Es el valor decimal de la fraccion.
     */
    public FraccionSimple(Long entero, Long numerador, Long denominador) {
        super(entero, numerador, denominador);
    }

    /**
     * Constructor que recibe dos parametros.
     *
     * @param numerador   Es el numerador de la fraccion.
     * @param denominador Es el valor decimal de la fraccion.
     */
    public FraccionSimple(Long numerador, Long denominador) {
        this(0l, numerador, denominador);
    }

    /**
     * Constructor que recibe un parametro.
     *
     * @param entero Es la parte entera de la fraccion.
     */
    public FraccionSimple(Long entero) {
        this(entero, 0l, 0l);
    }

    /**
     * Constructor que no recibe parametros.
     */
    public FraccionSimple() {
        this(0l, 0l, 0l);
    }

    @Override
    public void simplificar() {
        if (isSimplificable()) {
            if (IsUnidad()) {
                entero = entero + 1;
                numerador = 0l;
                denominador = 0l;
            } else {
                if (isImpropia()) {
                    convertirAMixta();
                }

                if (isPropia() && isReducible()) {
                    long mcd = Calculos.mcdSimple(numerador, denominador);
                    numerador /= mcd;
                    denominador /= mcd;
                }
            }

            Procedimiento.agregarPaso(
                    new Paso(Strings
                            .getString(R.string.OPE_NX_RES_SIM), TipoPaso.string));
            Procedimiento.agregarPaso(
                    new Paso(toMathDisplay(false), TipoPaso.expresion));
        }
    }

    @Override
    public boolean convertirAMixta() {
        boolean conversion = true;

        if (isImpropia()) {
            if (!isMixta()) {
                entero = numerador / denominador;
                numerador = numerador - (entero * denominador);
            } else {
                entero = entero + (numerador / denominador);
                numerador = numerador
                        - ((numerador / denominador) * denominador);
            }
        } else {
            conversion = false;
        }

        return conversion;
    }

    @Override
    public boolean convertirAImpropia() {
        boolean conversion = true;

        if (isMixta()) {
            if (isNumeroEntero()) {
                numerador = entero;
                entero = 0l;
                denominador = 1l;
            } else {
                numerador = numerador + (entero * denominador);
                entero = 0l;
            }
        } else {
            conversion = false;
        }

        return conversion;
    }
}
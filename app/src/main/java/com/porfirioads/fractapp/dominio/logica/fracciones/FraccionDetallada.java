package com.porfirioads.fractapp.dominio.logica.fracciones;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.calculos.Calculos;
import com.porfirioads.fractapp.dominio.procedimiento.FraccionPaso;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

/**
 * Esta clase es una implementacion de la clase abstracta Fraccion, donde las
 * operaciones realizadas generan una explicacion detallada del proceso.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class FraccionDetallada extends Fraccion {

    /**
     * Constructor que recibe tres parametros.
     *
     * @param entero  Es la parte entera de la fraccion.
     * @param numerador   Es el numerador de la fraccion.
     * @param denominador Es el valor decimal de la fraccion.
     */
    public FraccionDetallada(Long entero, Long numerador, Long denominador) {
        super(entero, numerador, denominador);
    }

    /**
     * Constructor que recibe dos parametros.
     *
     * @param numerador   Es el numerador de la fraccion.
     * @param denominador Es el valor decimal de la fraccion.
     */
    public FraccionDetallada(Long numerador, Long denominador) {
        this(0l, numerador, denominador);
    }

    /**
     * Constructor que recibe un parametro.
     *
     * @param entero Es la parte entera de la fraccion.
     */
    public FraccionDetallada(Long entero) {
        this(entero, 0l, 0l);
    }

    /**
     * Constructor que no recibe parametros.
     */
    public FraccionDetallada() {
        this(0l, 0l, 0l);
    }

    @Override
    public void simplificar() {
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FR_SIM_IND), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(toMathDisplay(false), TipoPaso.expresion));

        if (isSimplificable()) {
            if (IsUnidad()) {
                if (isMixta()) {
                    Procedimiento.agregarPaso(new Paso(Strings
                            .getString(R.string.FR_SIM_SX_UNI_MIX), TipoPaso.string));
                    Procedimiento.agregarPaso(
                            new Paso(entero + " + " + 1 + " = " + (entero + 1),
                                    TipoPaso.expresion));
                    entero += 1;
                    numerador = 0l;
                    denominador = 0l;

                    Procedimiento.agregarPaso(new Paso(Strings
                            .getString(R.string.FR_RESULTADO), TipoPaso.string));
                    Paso res = new Paso(new FraccionSimple(entero, numerador,
                            denominador).toMathDisplay(false), TipoPaso.expresion);
                    Procedimiento.agregarPaso(res);
                    Procedimiento.setResultado(res);
                } else {
                    Procedimiento.agregarPaso(new Paso(Strings
                            .getString(R.string.FR_SIM_SX_UNI_NOMIX),
                            TipoPaso.string));
                    Procedimiento.agregarPaso((new Paso("1",
                            TipoPaso.expresion)));
                    Procedimiento.setResultado((new Paso("1",
                            TipoPaso.expresion)));
                    entero = 1l;
                    numerador = 0l;
                    denominador = 0l;
                }
            } else {
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_SIM_SX_INT), TipoPaso.string));

                if (isImpropia()) { // Siendo impropia, por default es el CASO II

                    String pasoIndicacionMayor = numerador + " > "
                            + denominador;

                    Procedimiento.agregarPaso(new Paso(Strings
                            .getString(R.string.FR_SIM_SX_CAM), TipoPaso.string));
                    Procedimiento.agregarPaso(new Paso(pasoIndicacionMayor,
                            TipoPaso.expresion));

                    convertirAMixta();
                }

// Evalua si la fraccion es propia (CASO I), o si anteriormente era
// impropia, CASO III
                if (isPropia() && isReducible()) {

                    Procedimiento.agregarPaso(new Paso(Strings
                            .getString(R.string.FR_SIM_MCD), TipoPaso.string));

                    long MCD = Calculos.mcdDetallado(numerador, denominador);

                    String enteroPaso = entero + "";
                    String numeradorPaso = "(" + numerador + "÷" + MCD + ")";
                    String denominadorPaso = "(" + denominador + "÷" + MCD
                            + ")";

                    FraccionPaso paso = new FraccionPaso(enteroPaso,
                            numeradorPaso, denominadorPaso);

                    Procedimiento.agregarPaso(new Paso(Strings
                            .getString(R.string.FR_SIM_SX_DIV), TipoPaso.string));
                    Procedimiento.agregarPaso(new Paso(paso.toMathDisplay(),
                            TipoPaso.expresion));

                    numerador /= MCD;
                    denominador /= MCD;

                    Procedimiento.agregarPaso(new Paso(Strings
                            .getString(R.string.FR_RESULTADO), TipoPaso.string));
                    FraccionSimple resultado = new FraccionSimple(entero,
                            numerador, denominador);
                    Procedimiento.agregarPaso(new Paso(resultado.toMathDisplay(false),
                            TipoPaso.expresion));
                    Procedimiento.setResultado(new Paso(resultado.toMathDisplay(false),
                            TipoPaso.expresion));
                }
            }
        } else {
            Paso resultado = new Paso(Strings.getString(R.string.FR_CAM_NOSIMP),
                    TipoPaso.string);
            Procedimiento.agregarPaso(resultado);
            Procedimiento.setResultado(resultado);
        }
    }

    @Override
    public boolean convertirAMixta() {
        boolean huboConversion = true;

        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FR_CAM_IND), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(toMathDisplay(false), TipoPaso.expresion));

        if (isImpropia()) {
            FraccionSimple auxiliar = new FraccionSimple(entero, numerador,
                    denominador);

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.FR_CAM_SX_DES), TipoPaso.string));

            String pasoIndicacionMayor = numerador + " > " + denominador;

            Procedimiento.agregarPaso(new Paso(pasoIndicacionMayor,
                    TipoPaso.expresion));

// DETERMINACION DE CASOS DE CONVERSION
            if (!isExacta() && !isMixta()) { // CASO I
// Entero
                String pasoEntero = numerador + "÷" + denominador;
                auxiliar.setEntero(numerador / denominador);

                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_DET_ENT), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(pasoEntero + " = "
                        + auxiliar.getEntero(), TipoPaso.expresion));

// Numerador
                auxiliar.setNumerador(numerador
                        - (auxiliar.getEntero() * denominador));
                String pasoNumerador = String.format("%d - (%d * %d) = %d",
                        numerador, auxiliar.getEntero(), denominador,
                        auxiliar.getNumerador());

                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_DET_NUM), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(pasoNumerador,
                        TipoPaso.expresion));

// Denominador
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_DET_DEN), TipoPaso.string));

// Resumen operaciones
                pasoEntero = "(" + numerador + "÷" + denominador + ")";
                pasoNumerador = String.format("{%d - [%s * %d]}", numerador,
                        pasoEntero, denominador);
                String pasoDenominador = denominador + "";

                Procedimiento.agregarPaso(new Paso(new FraccionPaso(
                        pasoEntero, pasoNumerador, pasoDenominador).toMathDisplay(),
                        TipoPaso.expresion));

// Se asignan a la fraccion los resultados de los calculos
                entero = auxiliar.getEntero();
                numerador = auxiliar.getNumerador();
            } else if (isExacta() && !isMixta()) { // CASO II
// Entero
                String pasoEntero = numerador + "÷" + denominador;
                auxiliar.setEntero(numerador / denominador);
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_DET_ENT), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(pasoEntero + " = "
                        + auxiliar.getEntero(), TipoPaso.expresion));

// Indicacion de fraccion exacta
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_CII_EXA), TipoPaso.string));

// Resumen operaciones
                pasoEntero = "(" + numerador + "÷" + denominador + ")";
                String pasoNumerador = String.format("{%d - [%s * %d]}",
                        numerador, pasoEntero, denominador);
                String pasoDenominador = denominador + "";

                Procedimiento.agregarPaso(new Paso(new FraccionPaso(
                        pasoEntero, pasoNumerador, pasoDenominador).toMathDisplay(),
                        TipoPaso.expresion));

// Se asignan a la fraccion los resultados de los calculos
                entero = auxiliar.getEntero();
                numerador = 0l;
            } else if (isMixta() && !isExacta()) { // CASO III
                long enteroGuardado = auxiliar.getEntero();

// Tomar en cuenta solo la parte fraccionaria al principio
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_CIII_MIX), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(new FraccionSimple(
                        auxiliar.getNumerador(), auxiliar.getDenominador())
                        .toMathDisplay(false), TipoPaso.expresion));

// Entero
                String pasoEntero = numerador + "÷" + denominador;
                auxiliar.setEntero(numerador / denominador);

                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_DET_ENT), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(pasoEntero + " = "
                        + auxiliar.getEntero(), TipoPaso.expresion));

// Numerador
                auxiliar.setNumerador(numerador
                        - (auxiliar.getEntero() * denominador));
                String pasoNumerador = String.format("%d - (%d * %d) = %d",
                        numerador, auxiliar.getEntero(), denominador,
                        auxiliar.getNumerador());

                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_DET_NUM), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(pasoNumerador,
                        TipoPaso.expresion));

// Denominador
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_DET_DEN), TipoPaso.string));

// Resumen operaciones 1
                pasoEntero = "(" + numerador + "÷" + denominador + ")";
                pasoNumerador = String.format("{%d - [%s * %d]}", numerador,
                        pasoEntero, denominador);
                String pasoDenominador = denominador + "";

                Procedimiento.agregarPaso(new Paso(new FraccionPaso(
                        pasoEntero, pasoNumerador, pasoDenominador).toMathDisplay(),
                        TipoPaso.expresion));

// Resumen operaciones 2, tomar en cuenta el entero inicial
                pasoEntero = "(" + auxiliar.getEntero() + " + "
                        + enteroGuardado + ")";
                pasoNumerador = String.valueOf(auxiliar.getNumerador());
                pasoDenominador = String.valueOf(auxiliar.getDenominador());

                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_CIII_ENT), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(new FraccionPaso(
                        pasoEntero, pasoNumerador, pasoDenominador).toMathDisplay(),
                        TipoPaso.expresion));

                numerador = auxiliar.getNumerador();
                entero = auxiliar.getEntero() + enteroGuardado;
            } else if (isMixta() && isExacta()) { // CASO IV
                long enteroGuardado = auxiliar.getEntero();

// Tomar en cuenta solo la parte fraccionaria al principio
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_CIII_MIX), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(new FraccionSimple(
                        auxiliar.getNumerador(), auxiliar.getDenominador())
                        .toMathDisplay(false), TipoPaso.expresion));

// Entero
                String pasoEntero = numerador + "÷" + denominador;
                auxiliar.setEntero(numerador / denominador);

                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_DET_ENT), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(pasoEntero + " = "
                        + auxiliar.getEntero(), TipoPaso.expresion));

// Asigna 0 al numerador del auxiliar
                auxiliar.setNumerador(0l);

// Indicacion de fraccion exacta
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_CII_EXA), TipoPaso.string));

// Resumen operaciones 1
                pasoEntero = "(" + numerador + "÷" + denominador + ")";
                String pasoNumerador = String.format("{%d - [%s * %d]}",
                        numerador, pasoEntero, denominador);
                String pasoDenominador = denominador + "";

                Procedimiento.agregarPaso(new Paso(new FraccionPaso(
                        pasoEntero, pasoNumerador, pasoDenominador).toMathDisplay(),
                        TipoPaso.expresion));

// Resumen operaciones 2, tomar en cuenta el entero inicial
                pasoEntero = "(" + auxiliar.getEntero() + " + "
                        + enteroGuardado + ")";
                pasoNumerador = String.valueOf(auxiliar.getNumerador());
                pasoDenominador = String.valueOf(auxiliar.getDenominador());

                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAM_SX_CIII_ENT), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(new FraccionPaso(
                        pasoEntero, pasoNumerador, pasoDenominador).toMathDisplay(),
                        TipoPaso.expresion));

                entero = auxiliar.getEntero() + enteroGuardado;
                numerador = auxiliar.getNumerador();
            }

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.FR_RESULTADO), TipoPaso.string));

            FraccionSimple resultado = new FraccionSimple(entero,
                    numerador, denominador);
            Procedimiento.agregarPaso(new Paso(resultado.toMathDisplay(false),
                    TipoPaso.expresion));
            Procedimiento.setResultado(new Paso(resultado.toMathDisplay(false),
                    TipoPaso.expresion));

        } else {
            Paso resultado = new Paso(Strings.getString(R.string.FR_CAM_NOIMP),
                    TipoPaso.string);
            Procedimiento.agregarPaso(resultado);
            Procedimiento.setResultado(resultado);
            huboConversion = false;
        }

        return huboConversion;
    }

    @Override
    public boolean convertirAImpropia() {
        boolean hizoConversion = true;

        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FR_CAI_IND), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(toMathDisplay(false), TipoPaso.expresion));

// Evalua si la fraccion es mixta
        if (isMixta()) {
// Evalua si la fraccion es entera
            if (numerador == 0) {
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAI_DEN_EXA), TipoPaso.string));

                numerador = entero;
                entero = 0l;
                denominador = 1l;
            } // Es una fraccion mixta normal
            else {
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAI_SX_DES), TipoPaso.string));
                Procedimiento.agregarPaso(new Paso(entero + " + "
                        + new FraccionSimple(numerador, denominador).toMathDisplay(hizoConversion),
                        TipoPaso.expresion));
                Procedimiento.agregarPaso(new Paso(Strings
                        .getString(R.string.FR_CAI_SX_OPE), TipoPaso.string));

                String pasoNumerador = String.format("[%d + (%d * %d)]",
                        numerador, entero, denominador);
                String pasoDenominador = String.valueOf(denominador);
                FraccionPaso paso = new FraccionPaso(pasoNumerador,
                        pasoDenominador);

                Procedimiento.agregarPaso(new Paso(paso.toMathDisplay(),
                        TipoPaso.expresion));

                numerador = numerador + (entero * denominador);
                entero = 0l;
            }

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.FR_RESULTADO), TipoPaso.string));
            FraccionSimple resultado = new FraccionSimple(entero,
                    numerador, denominador);
            Procedimiento.agregarPaso(new Paso(resultado.toMathDisplay(false),
                    TipoPaso.expresion));
            Procedimiento.setResultado(new Paso(resultado.toMathDisplay(false),
                    TipoPaso.expresion));
        } else {
            Paso resultado = new Paso(Strings.getString(R.string.FR_CAM_NOMIX),
                    TipoPaso.string);
            Procedimiento.agregarPaso(resultado);
            Procedimiento.setResultado(resultado);

            hizoConversion = false;
        }

        return hizoConversion;
    }
}

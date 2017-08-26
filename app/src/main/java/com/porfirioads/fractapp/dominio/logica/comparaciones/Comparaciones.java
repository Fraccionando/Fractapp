package com.porfirioads.fractapp.dominio.logica.comparaciones;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

/**
 * Esta clase tiene la funcionalidad de hacer diferentes comparaciones entre
 * fracciones, como determinar si dos fracciones son equivalentes, si son
 * reciprocas y obtener la mayor de ellas.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class Comparaciones {

    /**
     * Constructor privado para que no se puedan hacer instancias de la clase
     * con 'new'.
     */
    private Comparaciones() {
    }

    /**
     * Determina si dos fracciones son equivalentes.
     *
     * @param f1 Primer fraccion a comparar.
     * @param f2 Segunda fraccion a comparar.
     */
    public static void determinarEquivalentes(Fraccion f1, Fraccion f2) {
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FUN_DET_EQU), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(f1.toMathDisplay(false) + ","
                + f2
                .toMathDisplay(false), TipoPaso.expresion));

        convertirMixtasEnImpropias(f1, f2);

        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FUN_INST_EQU), TipoPaso.string));

        long producto1 = f1.getNumerador() * f2.getDenominador();
        long producto2 = f2.getNumerador() * f1.getDenominador();

        String producto1Str = "(" + f1.getNumerador() + ")("
                + f2.getDenominador() + ") = " + producto1;
        String producto2Str = "(" + f2.getNumerador() + ")("
                + f1.getDenominador() + ") = " + producto2;

        Procedimiento.agregarPaso(new Paso(producto1Str, TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(producto2Str, TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FUN_PLT), TipoPaso.string));

        Paso resultado = null;

        if (producto1 == producto2) {
            resultado = new Paso(Strings.getString(R.string.FUN_RES_SI_EQU),
                    TipoPaso.string);
        } else {
            resultado = new Paso(Strings.getString(R.string.FUN_RES_NO_EQU),
                    TipoPaso.string);
        }

        Procedimiento.agregarPaso(resultado);
        Procedimiento.setResultado(resultado);
    }

    /**
     * Determina si dos fracciones son reciprocas (su producto es = 1).
     *
     * @param f1 Primer fraccion a comparar.
     * @param f2 Segunda fraccion a comparar.
     */
    public static void determinarReciprocas(Fraccion f1, Fraccion f2) {
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FUN_DET_REC), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(f1.toMathDisplay(false) + "," + f2
                .toMathDisplay(false), TipoPaso.expresion));

        convertirMixtasEnImpropias(f1, f2);

        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FUN_INST_REC), TipoPaso.string));

        long productoNumeradores = f1.getNumerador() * f2.getNumerador();
        long productoDenominadores = f1.getDenominador() * f2.getDenominador();

        String productoNumeradoresStr = "(" + f1.getNumerador() + ")("
                + f2.getNumerador() + ") = " + productoNumeradores;
        String productoDenominadoresStr = "(" + f1.getDenominador() + ")("
                + f2.getDenominador() + ") = " + productoDenominadores;

        Procedimiento.agregarPaso(new Paso(productoNumeradoresStr,
                TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(productoDenominadoresStr,
                TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FUN_PLT), TipoPaso.string));

        Paso resultado = null;

        if (productoNumeradores == productoDenominadores) {
            resultado = new Paso(Strings.getString(R.string.FUN_RES_SI_REC),
                    TipoPaso.string);
        } else {
            resultado = new Paso(Strings.getString(R.string.FUN_RES_NO_REC),
                    TipoPaso.string);
        }

        Procedimiento.agregarPaso(resultado);
        Procedimiento.setResultado(resultado);
    }

    /**
     * Determina la mayor de dos fracciones.
     *
     * @param f1 Primer fraccion a comparar.
     * @param f2 Segunda fraccion a comparar.
     */
    public static void determinarMayor(Fraccion f1, Fraccion f2) {
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FUN_DET_MAY), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(f1.toMathDisplay(false) + ","  +
                " " + f2
                .toMathDisplay(false), TipoPaso.expresion));

        convertirMixtasEnImpropias(f1, f2);

        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.FUN_INST_MAY), TipoPaso.string));

        long producto1 = f1.getNumerador() * f2.getDenominador();
        long producto2 = f2.getNumerador() * f1.getDenominador();

        String producto1Str = "(" + f1.getNumerador() + ")("
                + f2.getDenominador() + ") = " + producto1;
        String producto2Str = "(" + f2.getNumerador() + ")("
                + f1.getDenominador() + ") = " + producto2;

        Procedimiento.agregarPaso(new Paso(producto1Str, TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(producto2Str, TipoPaso.expresion));

        String resultado = null;

        if (producto1 == producto2) {
            resultado = f1.toMathDisplay(false) + " = " + f2.toMathDisplay
                    (false);

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.FUN_RES_IGU), TipoPaso.string));
        } else {
            if (producto1 > producto2) {
                resultado = f1.toMathDisplay(false);
            } else {
                resultado = f2.toMathDisplay(false);
            }

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.FUN_INST_MAY), TipoPaso.string));
        }

        Paso res = new Paso(resultado, TipoPaso.expresion);
        Procedimiento.agregarPaso(res);
        Procedimiento.setResultado(res);
    }

    /**
     * Convierte las fracciones mixtas de la comparacion en fracciones
     * impropias.
     *
     * @param f1 Primer fraccion a convertir.
     * @param f2 Segunda fraccion a convertir.
     */
    private static void convertirMixtasEnImpropias(Fraccion f1, Fraccion f2) {
        if (f1.isMixta() || f2.isMixta()) {
            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.OPE_NX_CAI), TipoPaso.string));

            if (f1.isMixta()) {
                f1.convertirAImpropia();
            }

            if (f2.isMixta()) {
                f2.convertirAImpropia();
            }

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.FUN_CAI_FRACS_RES), TipoPaso.string));
            Procedimiento.agregarPaso(new Paso(f1.toMathDisplay(false) + " y "
                    + f2.toMathDisplay(false), TipoPaso.expresion));
        }

    }
}

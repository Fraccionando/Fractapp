package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

import java.util.ArrayList;

/**
 * Esta clase contiene metodos auxiliares para llevar a cabo procesos que son
 * comunes a todas las operaciones basicas con fracciones, tanto en la version
 * que genera explicaciones detalladas, como en la que no lo hace.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class ComunesOperacion {

    /**
     * Constructor privado para que no se puedan hacer instancias de la clase.
     */
    private ComunesOperacion() {
    }

    /**
     * Convierte a impropias las fracciones mixtas de la operacion, generando
     * una explicacion detallada del proceso.
     *
     * @param operacion Es la operacion de donde se convertiran las fracciones.
     */
    public static void convertirMixtasAImpropiasMuyDetallado(
            Operacion operacion) {
// Evalua si debe hacer conversion de las fracciones mixtas a impropias
        if (operacion.hayUnaFraccionMixta()) {
            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.OPE_SX_CAI), TipoPaso.string));

            for (int i = 0; i < operacion.getFracciones().size(); i++) {
                if (operacion.getFracciones().get(i).isMixta()) {
                    operacion.getFracciones().get(i).convertirAImpropia();
                }
            }

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.OPE_RES_CONV), TipoPaso.string));
            Procedimiento.agregarPaso(new Paso(operacion.toMathDisplay(false),
                    TipoPaso.expresion));
        }
    }

    /**
     * Convierte a impropias las fracciones mixtas de la operacion, generando
     * una explicacion simple del proceso.
     *
     * @param operacion Es la operacion de donde se convertiran las fracciones.
     */
    public static void convertirMixtasAImpropiasSimple(Operacion operacion) {
        if (operacion.hayUnaFraccionMixta()) {
            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.OPE_NX_CAI), TipoPaso.string));

            for (int i = 0; i < operacion.getFracciones().size(); i++) {
                if (operacion.getFracciones().get(i).isMixta()) {
                    operacion.getFracciones().get(i).convertirAImpropia();
                }
            }

            Procedimiento.agregarPaso(new Paso(operacion.toMathDisplay(false),
                    TipoPaso.expresion));
        }
    }

    /**
     * Convierte las fracciones a instancias de FraccionDetallada.
     *
     * @param fracciones Es la lista donde estan las fracciones a convertir.
     */
    public static void toFraccionDetallada(ArrayList<Fraccion> fracciones) {
        for (int i = 0; i < fracciones.size(); i++) {
            fracciones.set(i, fracciones.get(i).toFraccionDetallada());
        }
    }

    /**
     * Convierte las fracciones a instancias de FraccionSimple.
     *
     * @param fracciones Es la lista donde estan las fracciones a convertir.
     */
    public static void toFraccionSimple(ArrayList<Fraccion> fracciones) {
        for (int i = 0; i < fracciones.size(); i++) {
            fracciones.set(i, fracciones.get(i).toFraccionSimple());
        }
    }

    /**
     * Realiza el ultimo paso de cualquier operacion basica con fracciones,
     * simplificar el resultado en caso de ser posible.
     *
     * @param resultado Es el resultado de la operacion, el cual se simplificara
     *  si es posible.
     */
    public static void terminarOperacion(Fraccion resultado) {
        System.out.println(resultado.toString() + "\n  Simplificable: "
                + resultado.isReducible() + "\n  Impropia: "
                + resultado.isImpropia() + "\n  Propia:" + resultado.isPropia()
                + "\n  Reducible: " + resultado.isReducible() + "\n  Exacta: "
                + resultado.isExacta() + "\n  Mixta: " + resultado.isMixta()
                + "\n  Unidad: " + resultado.IsUnidad());

        if (resultado.isSimplificable()) {
            resultado.simplificar();
        }

        Procedimiento.setResultado(new Paso(resultado.toMathDisplay(false)
                + "(" + resultado.getDecimal()
                + ")", TipoPaso.expresion));
    }
}

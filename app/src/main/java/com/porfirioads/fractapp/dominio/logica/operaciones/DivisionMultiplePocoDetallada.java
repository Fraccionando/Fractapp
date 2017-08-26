package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

import java.util.ArrayList;

/**
 * Es una implementacion de DivisionMultiple, donde las operaciones realizadas
 * generan una explicacion simple del procedimiento llevado a cabo.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class DivisionMultiplePocoDetallada extends DivisionMultiple {

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public DivisionMultiplePocoDetallada(ArrayList<Fraccion> fracciones,
                                         ArrayList<Character> operadores) {
        super(fracciones, operadores);
        ComunesOperacion.toFraccionSimple(fracciones);
    }

    @Override
    protected void convertirMixtasAImpropias() {
        ComunesOperacion.convertirMixtasAImpropiasSimple(this);
    }

    @Override
    public Fraccion calcularResultado(boolean operacionFinal) {
        if (isDivisionMultiple()) {
            convertirMixtasAImpropias();

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.DIVS_NX), TipoPaso.string));

            separarPorPares(this);

            if (operacionFinal) {
                ComunesOperacion.terminarOperacion(resultado);
            }

            return resultado;
        } else {
            MultiplicacionDivision multiplicacionDivision
                    = new MultiplicacionDivisionPocoDetallada(fracciones,
                    operadores);
            return multiplicacionDivision.calcularResultado(operacionFinal);
        }
    }

}

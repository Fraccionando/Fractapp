package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

import java.util.ArrayList;

/**
 * Es una implementacion de DivisionSimple, donde las operaciones realizadas
 * generan una explicacion simple del procedimiento llevado a cabo.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class DivisionSimplePocoDetallada extends DivisionSimple {

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public DivisionSimplePocoDetallada(ArrayList<Fraccion> fracciones,
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
        if (isDivisionSimple()) {
            convertirMixtasAImpropias();

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.DIV_SIM_NX), TipoPaso.string));
            Procedimiento.agregarPaso(new Paso(latexMultiplicacionesCruzadas(),
                    TipoPaso.expresion));

            resultado = resultadoMultiplicacionCruzada().toFraccionSimple();

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.OPE_RES), TipoPaso.string));
            Procedimiento.agregarPaso(new Paso(resultado.toMathDisplay(false),
                    TipoPaso.expresion));

            if (operacionFinal) {
                ComunesOperacion.terminarOperacion(resultado);
            }

            return resultado;
        } else {
            DivisionMultiple divisionMultiple
                    = new DivisionMultiplePocoDetallada(fracciones, operadores);
            return divisionMultiple.calcularResultado(operacionFinal);
        }

    }

}

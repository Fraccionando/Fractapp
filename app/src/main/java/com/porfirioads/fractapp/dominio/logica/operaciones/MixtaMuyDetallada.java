package com.porfirioads.fractapp.dominio.logica.operaciones;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

import java.util.ArrayList;

/**
 * Es una implementacion de Mixta, donde las operaciones realizadas generan una
 * explicacion muy detallada del procedimiento llevado a cabo.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class MixtaMuyDetallada extends Mixta {

    /**
     * Constructor que recibe dos parametros.
     *
     * @param fracciones Contiene los operandos de la operacion.
     * @param operadores Contiene los operadores de la operacion.
     */
    public MixtaMuyDetallada(ArrayList<Fraccion> fracciones,
                             ArrayList<Character> operadores) {
        super(fracciones, operadores);
        ComunesOperacion.toFraccionDetallada(fracciones);
    }

    @Override
    protected void convertirMixtasAImpropias() {
        ComunesOperacion.convertirMixtasAImpropiasMuyDetallado(this);
    }

    @Override
    public Fraccion calcularResultado(boolean operacionFinal) {
        if (isMixta()) {
            convertirMixtasAImpropias();

            Procedimiento.agregarPaso(new Paso(Strings
                    .getString(R.string.MIX_SX_GRUP), TipoPaso.string));
            Procedimiento.agregarPaso(new Paso(latexAgrupacionPrecedencia(),
                    TipoPaso.expresion));

            resultado = resultadoMixta();

            if (operacionFinal) {
                ComunesOperacion.terminarOperacion(resultado);
            }

            return resultado;
        } else {
            throw new UnsupportedOperationException(
                    "Operadores no identificados: " + operadores.toString());
        }
    }
}

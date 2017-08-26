package com.porfirioads.fractapp.dominio.logica.fracciones;

import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.logica.calculos.Calculos;
import com.porfirioads.fractapp.dominio.utils.Constantes;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Esta clase abstracta es un modelo para representar una fraccion, la forma de
 * implementacion en sus subclases varia unicamente en los pasos que agrega a la
 * explicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public abstract class Fraccion implements Serializable {

    /**
     * Es la parte entera de la fraccion.
     */
    protected Long entero;
    /**
     * Es el numerador de la fraccion.
     */
    protected Long numerador;
    /**
     * Es el denominador de la fraccion.
     */
    protected Long denominador;

    /**
     * Constructor que recibe tres parametros.
     *
     * @param entero      Es la parte entera de la fraccion.
     * @param numerador   Es el numerador de la fraccion.
     * @param denominador Es el valor decimal de la fraccion.
     */
    public Fraccion(Long entero, Long numerador, Long denominador) {
        this.entero = entero;
        this.numerador = numerador;
        this.denominador = denominador;
    }

    /**
     * Constructor que recibe dos parametros.
     *
     * @param numerador   Es el numerador de la fraccion.
     * @param denominador Es el valor decimal de la fraccion.
     */
    public Fraccion(Long numerador, Long denominador) {
        this(0l, numerador, denominador);
    }

    /**
     * Constructor que recibe un parametro.
     *
     * @param entero Es la parte entera de la fraccion.
     */
    public Fraccion(Long entero) {
        this(entero, 0l, 0l);
    }

    /**
     * Constructor que no recibe parametros.
     */
    public Fraccion() {
        this(0l, 0l, 0l);
    }

    /**
     * Devuelve el entero de la fraccion.
     *
     * @return El numero correspondiente al entero de la fraccion.
     */
    public Long getEntero() {
        return entero;
    }

    /**
     * Establece el entero de la fraccion.
     *
     * @param entero Es el valor que se asignara al entero de la fraccion.
     */
    public void setEntero(Long entero) {
        this.entero = entero;
    }

    /**
     * Devuelve el numerador de la fraccion.
     *
     * @return El numero correspondiente al numerador de la fraccion.
     */
    public Long getNumerador() {
        return numerador;
    }

    /**
     * Establece el numerador de la fraccion.
     *
     * @param numerador Es el valor que se asignara al numerador de la fraccion.
     */
    public void setNumerador(Long numerador) {
        this.numerador = numerador;
    }

    /**
     * Devuelve el denominador de la fraccion.
     *
     * @return El numero correspondiente al denominador de la fraccion.
     */
    public Long getDenominador() {
        return denominador;
    }

    /**
     * Establece el denominador de la fraccion.
     *
     * @param denominador Es el valor que se asignara al denominador de la
     *                    fraccion.
     */
    public void setDenominador(Long denominador) {
        this.denominador = denominador;
    }

    /**
     * Devuelve el valor decimal de la fraccion.
     *
     * @return El valor de la fraccion en notacion decimal.
     */
    public Double getDecimal() {

        if (denominador == 0) {
            return entero + 0.0;
        } else {
            double d = entero + (double) numerador / denominador;
            BigDecimal bd = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            return bd.doubleValue();
        }
    }

    /**
     * Devuelve la representacion en Latex de la fraccion.
     *
     * @param formulacion Es el valor que determina si el codigo latex es para
     *                    formulacion o para expresion final.
     * @return El valor del codigo latex.
     */
    public String toLatex(boolean formulacion) {
        String latex;

        if (isNumeroEntero()) {
            if (formulacion && entero == 0) {
                latex = "";
            } else {
                latex = entero.toString();
            }
        } else {
            String ent = (entero == 0) ? "" : entero.toString();

            if (formulacion) {
                String num = (numerador == 0) ? "" : numerador.toString();
                String den = (denominador == 0) ? "" : denominador.toString();
                latex = String.format("%s\\frac{%s}{%s}", ent, num, den);
            } else {
                if (numerador == 0 || denominador == 0) {
                    latex = entero.toString();
                } else {
                    latex = String.format("%s\\frac{%d}{%d}", ent, numerador,
                            denominador);
                }
            }

        }

        return latex;
    }

    public String toMathDisplay(boolean formulacion) {
        String math;

        if (isNumeroEntero()) {
            if (formulacion && entero == 0) {
                math = "";
            } else {
                math = Documento.numAbre + entero.toString() + Documento.numCierra;
            }
        } else {
            String ent = (entero == 0) ? "" : Documento.numAbre + entero.toString() + Documento.numCierra;

            if (formulacion) {
                String num;
                String den;

                if (numerador == 0) {
                    num = Documento.numAbre + Constantes.espacioHtml
                            + Documento.numCierra;
                } else {
                    num = Documento.numAbre + numerador.toString() + Documento.numCierra;
                }

                if (denominador == 0) {
                    den = Documento.numAbre + Constantes.espacioHtml
                            + Documento.numCierra;
                } else {
                    den = Documento.numAbre + denominador.toString() + Documento.numCierra;
                }

                math = ent + Documento.fracAbre + num + den + Documento.fracCierra;
            } else {
                if (numerador == 0 || denominador == 0) {
                    math = Documento.numAbre + entero.toString() + Documento.numCierra;
                } else {
                    String num = Documento.numAbre + numerador.toString() + Documento.numCierra;
                    String den = Documento.numAbre + denominador.toString() + Documento.numCierra;
                    math = ent + Documento.fracAbre + num + den + Documento.fracCierra;
                }
            }

        }

        return math;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fraccion fraccion = (Fraccion) o;

        if (entero != null ? !entero.equals(fraccion.entero) : fraccion.entero != null)
            return false;
        if (numerador != null ? !numerador.equals(fraccion.numerador) : fraccion.numerador != null)
            return false;
        return !(denominador != null ? !denominador.equals(fraccion.denominador) : fraccion.denominador != null);

    }

    /**
     * Devuelve la representacion en String de la fraccion.
     *
     * @param formulacion Es el valor que determina si el string es para
     *                    formulacion o para expresion final.
     * @return El valor del string de la fraccion.
     */
    public String toString(boolean formulacion) {
        String string;

        if (isNumeroEntero()) {
            if (formulacion && entero == 0) {
                string = "";
            } else {
                string = entero.toString();
            }
        } else {
            String ent = (entero == 0) ? "" : entero.toString();

            if (formulacion) {
                String num = (numerador == 0) ? "" : numerador.toString();
                String den = (denominador == 0) ? "" : denominador.toString();
                string = String.format("%s %s/%s", ent, num, den);
            } else {
                if (numerador == 0 || denominador == 0) {
                    string = entero.toString();
                } else {
                    string = String.format("%s %d/%d", ent, numerador,
                            denominador);
                }
            }

        }

        return string;
    }

    /**
     * Crea una copia de la fraccion, pero la devuelve en forma de una fraccion
     * simple.
     *
     * @return Una instancia de FraccionSimple, con la copia de la fraccion.
     */
    public FraccionSimple toFraccionSimple() {
        return new FraccionSimple(entero, numerador, denominador);
    }

    /**
     * Crea una copia de la fraccion, pero la devuelve en forma de una fraccion
     * detallada.
     *
     * @return Una instancia de FraccionDetallada, con la copia de la fraccion.
     */
    public FraccionDetallada toFraccionDetallada() {
        return new FraccionDetallada(entero, numerador, denominador);
    }

    /**
     * Evalua si la fraccion es impropia (Su numerador es mayor al denominador).
     *
     * @return true si la fraccion es impropia y false si no lo es.
     */
    public boolean isImpropia() {
        boolean impropia = false;

        if (numerador > denominador) {
            impropia = true;
        }

        return impropia;
    }

    /**
     * Evalua si la fraccion es propia (su numerador es menor al denominador).
     *
     * @return true si la fraccion es propia y false si no lo es.
     */
    public boolean isPropia() {
        boolean propia = false;

        if (numerador < denominador) {
            propia = true;
        }

        return propia;
    }

    /**
     * Evalua si la fraccion es una fraccion unidad (el numerador es igual al
     * denominador).
     *
     * @return true si es fraccion unidad o false si no lo es.
     */
    public boolean IsUnidad() {
        return numerador.equals(denominador);
    }

    /**
     * Evalua si la fraccion es mixta (tiene parte entera y parte fraccionaria).
     *
     * @return true si la fraccion es mixta y false si no lo es.
     */
    public boolean isMixta() {
        boolean mixta = false;

        if (entero != 0) {
            mixta = true;
        }

        return mixta;
    }

    /**
     * Determina si la fraccion es reducible (su parte fraccionaria puede
     * expresarse con una fraccion mas simple).
     *
     * @return true si la fraccion es reducible y false si no lo es.
     */
    public boolean isReducible() {
        boolean reducible = false;

        long mcdNumDen = Calculos.mcdSimple(new long[]{numerador, denominador});

        // Evalua si el MCD entre el numerador y denominador sea diferente de 1
        if (mcdNumDen != 1) {
            reducible = true;
        }

        return reducible;
    }

    /**
     * Determina si la fraccion es simplificable (es reducible o impropia).
     *
     * @return true si la fraccion es simplificable o false si no lo es.
     */
    public boolean isSimplificable() {
        boolean simplificable = false;

        if (isImpropia() || isReducible()) {
            simplificable = true;
        }

        return simplificable;
    }

    /**
     * Determina si la fraccion es exacta (el numerador es divisible por el
     * denominador).
     *
     * @return true si la fraccion es simplificable o false si no lo es.
     */
    public boolean isExacta() {
        boolean exacta = false;

        if (denominador != 0) {
            if (numerador % denominador == 0) {
                exacta = true;
            }
        }

        return exacta;
    }

    /**
     * Determina si la fraccion es un numero entero (no tiene parte
     * fraccionaria).
     *
     * @return true si es un numero entero o false si no lo es.
     */
    public boolean isNumeroEntero() {
        return numerador == 0 && denominador == 0;
    }

    /**
     * Simplifica la fraccion, ya sea reduciendo a una fraccion equivalente,
     * convertir a fraccion mixta en caso de que la fraccion sea impropia, o
     * ambas.
     */
    public abstract void simplificar();

    /**
     * Convierte la fraccion a mixta en caso de que sea impropia.
     *
     * @return true si la conversion pudo realizarse o false en caso de que no
     * se pudiera.
     */
    public abstract boolean convertirAMixta();

    /**
     * Convierte la fraccion a impropia en caso de que sea mixta.
     *
     * @return true si la conversion pudo realizarse o false en caso de que no
     * se pudiera.
     */
    public abstract boolean convertirAImpropia();
}

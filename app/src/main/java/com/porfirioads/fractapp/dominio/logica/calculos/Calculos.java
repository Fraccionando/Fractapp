package com.porfirioads.fractapp.dominio.logica.calculos;

import com.porfirioads.fractapp.configuracion.Strings;
import com.porfirioads.fractapp.dominio.enumerados.TipoPaso;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.procedimiento.Paso;
import com.porfirioads.fractapp.dominio.procedimiento.Procedimiento;
import com.porfirioads.fractapp.vistas.R;

import java.util.Arrays;

/**
 * Esta es una clase utilitaria que contiene metodos para resolver calculos
 * necesarios para las operaciones con fracciones, como lo son el mcm, y mcd; Se
 * proporcionan dos versiones de algunos metodos, una que genera explicacion y
 * otra que no lo hace, el motivo de hacerlo directamente aqui es porque al ser
 * pocos metodos en lo que aplica esto, no tendria caso utilizar una jerarquia
 * de herencias para los tipos de explicaciones como con las fracciones y
 * operaciones.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class Calculos {

    /**
     * Constructor privado para que no se puedan hacer instancias de la clase.
     */
    private Calculos() {
    }

    /**
     * Calcula el minimo comun multiplo de un conjunto de numeros, sin generar
     * explicaciones del procedimiento.
     *
     * @param numeros Es un arreglo donde estan los numeros de los cuales se
     *                hara el calculo.
     * @return El valor del mcm.
     */
    public static long mcmSimple(long... numeros) {
        long mcm = 1;

        long[] copiaNumeros = numeros.clone();
        long n = 2; // Valor inicial del factor de prueba
        boolean seModificaraElMcm = false; // Indica si se actualizara el valor
        // del mcm
        long comparacionMcm = 0; // Numero con el que se compara el mcm en cada
        // iteracion de su calculo

        // Se cambian los valores de numeros a su valor absoluto
        cambiarAValoresAbsolutos(copiaNumeros);

        // Repite mientras haya un numero mayor a 1
        while (algunoEsMayorQue1(copiaNumeros)) {
            // Repite indefinidamente
            while (true) {
                seModificaraElMcm = false;

                // Se recorren los numeros
                for (int i = 0; i < copiaNumeros.length; i++) {
                    // Evalua si el numero es divisible entre n
                    if (copiaNumeros[i] % n == 0) {
                        copiaNumeros[i] /= n; // Modifica el numero
                        seModificaraElMcm = true;
                    }
                }

                // Evalua si se modificara el mcm
                if (seModificaraElMcm) {
                    mcm *= n;
                }

                // Evalua si comparacionMcm es igual a mcm
                if (comparacionMcm == mcm) {
                    break; // El mcm no se actualizo, por lo tanto se sale del
                    // ciclo
                } else {
                    comparacionMcm = mcm; // Actualiza el valor a comprobar con
                    // el siguiente mcm
                }
            }

            n++;
        }

        return mcm;
    }

    /**
     * Calcula el maximo comun divisor de un conjunto de numeros, sin generar
     * explicaciones del procedimiento.
     *
     * @param numeros Es un arreglo donde estan los numeros de los cuales se
     *                hara el calculo.
     * @return El valor del mcd.
     */
    public static long mcdSimple(long... numeros) {
        long mcd = 1;

        long[] copiaNumeros = numeros.clone();
        long n = 2; // Valor inicial del factor de prueba

        // Se cambian los numeros a su valor absoluto
        cambiarAValoresAbsolutos(copiaNumeros);

        // Repite mientras n no soprepase a alguno de los elementos de numeros
        while (nNoSuperaANinguno(copiaNumeros, n)) {
            boolean sonTodosDivisibles = true;

            // Recorre los numeros
            for (long c : copiaNumeros) {
                // Evalua si c no es divisible entre n
                if (c % n != 0) {
                    sonTodosDivisibles = false;
                    n++;
                    break;
                }
            }

            // Evalua si todos fueron divisibles para poder modificar el valor
            // del MCD
            if (sonTodosDivisibles) {
                // Actualiza los numeros
                for (int i = 0; i < copiaNumeros.length; i++) {
                    copiaNumeros[i] /= n;
                }

                mcd *= n;
            }
        }

        return mcd;
    }

    /**
     * Calcula el maximo comun divisor de un conjunto de numeros, generando
     * explicaciones del procedimiento.
     *
     * @param numeros Es un arreglo donde estan los numeros de los cuales se
     *                hara el calculo.
     * @return El valor del mcd.
     */
    public static long mcdDetallado(long... numeros) {
        long[] copiaNumeros = numeros.clone();
        long mcd = 1; // Valor inicial del MCD
        long n = 2; // Valor inicial del factor de prueba
        String descomposicion = ""; // Concatenacion de la descomposicion de los
        // numeros
        String factores = ""; // Cadena que indica la multiplicacion de los
        // factores

        // Se cambian los numeros a su valor absoluto
        cambiarAValoresAbsolutos(copiaNumeros);

        descomposicion += Documento.tableAbre + Documento.rAbre;

        // Se concatena la primer linea de la descomposicion
        for (long num : copiaNumeros) {
            descomposicion += Documento.dAbre + Documento.numAbre
                    + num + Documento.numCierra + Documento.space
                    + Documento.dCierra;
        }

        // Repite mientras n no soprepase a alguno de los elementos de numeros
        while (nNoSuperaANinguno(copiaNumeros, n)) {
            boolean sonTodosDivisibles = true;

            // Recorre los numeros
            for (long c : copiaNumeros) {
                // Evalua si c no es divisible entre n
                if (c % n != 0) {
                    sonTodosDivisibles = false;
                    n++;
                    break;
                }
            }

            // Evalua si todos fueron divisibles para poder modificar el valor
            // del MCD
            if (sonTodosDivisibles) {
                descomposicion += "|" + Documento.space + n;

                descomposicion += Documento.rCierra;
                descomposicion += Documento.rAbre;

                // Actualiza los numeros
                for (int i = 0; i < copiaNumeros.length; i++) {
                    copiaNumeros[i] /= n;

                    descomposicion += Documento.dAbre
                            + Documento.numAbre + copiaNumeros[i]
                            + Documento.numCierra
                            + Documento.space + Documento.dCierra;
                }

                // descomposicion += "</html>";
                factores += "(" + n + ")";
                mcd *= n;
            }

            // System.out.println(descomposicion);
        }

        descomposicion += Documento.rCierra;
        descomposicion += Documento.tableCierra;

        // Evalua si no hubo algun factor
        if (factores.length() == 0) {
            factores = "(1)";
        }

        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.CAL_MCD_IND), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(Arrays.toString(numeros),
                TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.CAL_MCD_SX_EXP), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.CAL_MCD_SX_INT), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(descomposicion, TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.CAL_MCD_SX_RES), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(factores + " = " + mcd,
                TipoPaso.expresion));
        Procedimiento.setResultado(new Paso(mcd + "", TipoPaso.expresion));

        return mcd;
    }

    /**
     * Calcula el minimo comun multiplo de un conjunto de numeros, generando
     * explicaciones del procedimiento.
     *
     * @param numeros Es un arreglo donde estan los numeros de los cuales se
     *                hara el calculo.
     * @return El valor del mcm.
     */
    public static long mcmDetallado(long... numeros) {
        int mcm = 1;

        long[] copiaNumeros = numeros.clone();
        long n = 2; // Valor inicial del factor de prueba
        String descomposicion = ""; // Donde se concatena la descomposicion de
        // factores
        String factores = ""; // Cadena que indica la multiplicacion de los
        // factores para llegar al mcm
        boolean seModificaraElMcm = false; // Indica si se actualizara el valor
        // del mcm
        long comparacionMcm = 0; // Numero con el que se compara el mcm en cada
        // iteracion de su calculo

        // Se cambian los valores de numeros a su valor absoluto
        cambiarAValoresAbsolutos(copiaNumeros);

        descomposicion += Documento.tableAbre + Documento.rAbre;

        // Se concatena la primer linea de la descomposicion
        for (long num : copiaNumeros) {
            descomposicion += Documento.dAbre + Documento.numAbre
                    + num + Documento.numCierra + Documento.space
                    + Documento.dCierra;
        }

        // Repite mientras haya un numero mayor a 1
        while (algunoEsMayorQue1(copiaNumeros)) {
            // Repite indefinidamente
            while (true) {
                seModificaraElMcm = false;

                // Se recorren los numeros
                for (int i = 0; i < copiaNumeros.length; i++) {
                    // Evalua si el numero es divisible entre n
                    if (copiaNumeros[i] % n == 0) {
                        copiaNumeros[i] /= n; // Modifica el numero
                        seModificaraElMcm = true;
                    }
                }

                // Evalua si se modificara el mcm
                if (seModificaraElMcm) {
                    mcm *= n;
                    factores += "(" + n + ")";
                    descomposicion += "|" + Documento.space + n;

                    descomposicion += Documento.rCierra;
                    descomposicion += Documento.rAbre;

                    for (long c : copiaNumeros) {
                        descomposicion += Documento.dAbre
                                + Documento.numAbre + c
                                + Documento.numCierra
                                + Documento.space + Documento.dCierra;
                    }


                }

                // Evalua si comparacionMcm es igual a mcm
                if (comparacionMcm == mcm) {
                    break; // El mcm no se actualizo, por lo tanto se sale del
                    // ciclo
                } else {
                    comparacionMcm = mcm; // Actualiza el valor a comprobar con
                    // el siguiente mcm
                }
            }

            n++;
        }

        descomposicion += Documento.rCierra;
        descomposicion += Documento.tableCierra;

        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.CAL_MCM_IND), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(Arrays.toString(numeros),
                TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.CAL_MCM_SX_EXP), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.CAL_MCM_SX_INT), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(descomposicion, TipoPaso.expresion));
        Procedimiento.agregarPaso(new Paso(Strings
                .getString(R.string.CAL_MCM_SX_RES), TipoPaso.string));
        Procedimiento.agregarPaso(new Paso(factores + " = " + mcm,
                TipoPaso.expresion));
        Procedimiento.setResultado(new Paso(mcm + "", TipoPaso.expresion));

        return mcm;
    }

    /**
     * Determina si un numero dentro de un conjunto es mayor a 1.
     *
     * @param numeros Es un arreglo de enteros donde se encuentran los valores a
     *                analizar.
     * @return true si algun numero del arreglo es mayor a 1 o false si ninguno
     * lo es.
     */
    private static boolean algunoEsMayorQue1(long[] numeros) {
        boolean hayUnoMayor = false;

        for (long k : numeros) {
            if (k > 1) {
                hayUnoMayor = true;
                break;
            }
        }

        return hayUnoMayor;
    }

    /**
     * Cambia el valor de los elementos de un arreglo de enteros a su valor
     * absoluto.
     *
     * @param numeros Es el arreglo donde se encuentran los numeros a modificar.
     */
    private static void cambiarAValoresAbsolutos(long[] numeros) {
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = Math.abs(numeros[i]);
        }
    }

    /**
     * Determina si el valor de n no sobrepasa a ningun elemento de un arreglo
     * de enteros.
     *
     * @param numeros Es el arreglo donde se encuentran los numeros a comparar.
     * @param n       Es el valor que no debe superar a los elementos del arreglo.
     * @return true si n no supera a nadie, false si si lo hace
     */
    private static boolean nNoSuperaANinguno(long[] numeros, long n) {
        boolean noLoSobrepasa = true;

        for (long c : numeros) {
            if (n > c) {
                noLoSobrepasa = false;
                break;
            }
        }

        return noLoSobrepasa;
    }
}

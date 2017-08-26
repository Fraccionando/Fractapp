package com.porfirioads.fractapp.dominio.utils;

/**
 * Esta es una clase utilitaria para el manejo de operaciones comunes en cadenas
 * de texto, pero aplicada a numeros.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class ManejoDigitos {

    /**
     * Constructor privado para que no se puedan hacer instancias de la clase
     * con 'new'.
     */
    private ManejoDigitos() {
    }

    /**
     * Agrega un digito al final de un numero.
     *
     * @param numero Es el numero original.
     * @param digito Es el digito que se va a agregar.
     * @return El numero con el digito agregado.
     */
    public static Long agregarDigito(Long numero, Integer digito) {
        String numeroStr = numero.toString() + digito.toString();
        return Long.parseLong(numeroStr);
    }

    /**
     * Elimina el ultimo digito de un numero.
     *
     * @param numero Es el numero al cual se le eliminara un digito.
     * @return El numero sin el ultimo digito.
     */
    public static Long removerUltimoDigito(Long numero) {
        String numeroStr = numero.toString();
        numeroStr = numeroStr.substring(0, numeroStr.length() - 1);

        if (numeroStr.length() == 0) {
            return 0l;
        } else {
            return Long.parseLong(numeroStr);
        }
    }
}

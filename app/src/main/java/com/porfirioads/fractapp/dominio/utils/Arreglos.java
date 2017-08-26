package com.porfirioads.fractapp.dominio.utils;

/**
 * Esta clase utilitaria proporciona metodos para el manejo de arreglos, en
 * necesidades muy especificas de la aplicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class Arreglos {

    /**
     * Constructor privado para que no se puedan hacer instancias de la clase
     * con 'new'.
     */
    private Arreglos() {
    }

    /**
     * Determina el indice donde un objeto o, se encuentra en un arreglo array.
     *
     * @param array Es el arreglo donde se buscara el elemento.
     * @param o     Es el elemento a buscar.
     * @return El indice del objeto en el arreglo o -1 si no se encuentra.
     */
    public static int indexOf(Object[] array, Object o) {
        if (array != null) {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals(o)) {
                    return i;
                }
            }
            return -1;
        } else {
            return -1;
        }
    }

    /**
     * Separa una cadena por medio de un caracter y devuelve un arreglo de
     * longs.
     *
     * @param cadena    Es la cadena de la que se van a extraer los datos.
     * @param separador Es el caracter que separara los elementos.
     * @return El arreglo obtenido de la cadena.
     */
    public static long[] stringToLongArray(String cadena, String separador) {
        String[] strings = cadena.split(separador);
        long[] longs = new long[strings.length];

        for (int i = 0; i < longs.length; i++) {
            longs[i] = Long.parseLong(strings[i]);
        }

        return longs;
    }
}

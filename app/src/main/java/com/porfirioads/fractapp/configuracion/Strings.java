package com.porfirioads.fractapp.configuracion;

import java.util.Map;
import java.util.TreeMap;

/**
 * Esta clase sirve para agregar los Strings de las explicaciones en un mapa,
 * de modo que puedan ser accedidos por las clases de la logica, las cuales
 * no son activities.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class Strings {
    /**
     * Es un mapa que guarda los strings, las llaves para accederlos son los
     * mismos que su id de resource.
     */
    private static Map<Integer, String> strings = new TreeMap<>();

    /**
     * Agrega un string al mapa de strings.
     *
     * @param id     Es la llave del elemento.
     * @param string Es el valor del elemento.
     */
    public static void addString(int id, String string) {
        strings.put(id, string);
    }

    /**
     * Obtiene un string del mapa de strings.
     *
     * @param id Es la llave del string deseado.
     * @return El valor del string.
     */
    public static String getString(int id) {
        return strings.get(id);
    }
}

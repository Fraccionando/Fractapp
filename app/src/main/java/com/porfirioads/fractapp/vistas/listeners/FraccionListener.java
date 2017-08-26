package com.porfirioads.fractapp.vistas.listeners;

/**
 * Esta interface es un listener para los eventos personalizados, que pueden
 * ocurrir al formar fracciones.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public interface FraccionListener {
    /**
     * Se dispara cuando una fraccion ha sido actualizada.
     */
    void onFraccionUpdated();

    /**
     * Se dispara cuando en una operacion, la ultima fraccion se elimina de
     * ella.
     */
    void onFraccionRemoved();

    /**
     * Se dispara cuando la fraccion se reinicia.
     */
    void onFraccionReseted();
}

package com.porfirioads.fractapp.vistas.componentes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.porfirioads.fractapp.controladores.CurrentState;
import com.porfirioads.fractapp.dominio.enumerados.ParteFraccion;
import com.porfirioads.fractapp.dominio.logica.fracciones.Fraccion;
import com.porfirioads.fractapp.dominio.logica.fracciones.FraccionSimple;
import com.porfirioads.fractapp.dominio.utils.Arreglos;
import com.porfirioads.fractapp.dominio.utils.ManejoDigitos;
import com.porfirioads.fractapp.vistas.R;
import com.porfirioads.fractapp.vistas.listeners.FraccionListener;
import com.porfirioads.fractapp.vistas.listeners.FraccionListenerAdapter;

import java.util.ArrayList;

/**
 * Es el componente generador de fracciones.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class GeneradorFraccion extends LinearLayout {
    /**
     * Valor que determina si el componente contiene o no el boton de remover
     * fraccion.
     */
    private boolean botonRemover;
    /**
     * Es el boton Ac del generador.
     */
    private AutoResizeTextButton botonAc;
    /**
     * Es el boton Del del generador.
     */
    private AutoResizeTextButton botonDel;
    /**
     * Es el boton que indica la parte del entero del generador.
     */
    private AutoResizeTextButton botonParteEnt;
    /**
     * Es el boton que indica la parte del numerador del generador.
     */
    private AutoResizeTextButton botonParteNum;
    /**
     * Es el boton que indica la parte del denominador del generador.
     */
    private AutoResizeTextButton botonParteDen;
    /**
     * Es el boton de reiniciar fraccion del generador.
     */
    private AutoResizeTextButton botonReiniciarFraccion;
    /**
     * Es el boton de remover fraccion del generador.
     */
    private AutoResizeTextButton botonRemoverFraccion;
    /**
     * Es un arreglo con los botones que constantemente se habilitan o
     * deshabilitan.
     */
    private AutoResizeTextButton[] botonesTemporales;
    /**
     * Es un arreglo que contiene los botones numericos.
     */
    private AutoResizeTextButton[] botonesNumericos;
    /**
     * Es el viewPager donde se encuentra el generador en caso de ser asi.
     */
    private CustomViewPager viewPager;
    /**
     * Son las fracciones de la operacion o comparacion que se esta formando, en
     * caso de que asi sea, en una conversion, esta lista solo tendra un
     * elemento.
     */
    private ArrayList<Fraccion> fracciones;
    /**
     * Son los operadores de la operacion que se esta formando en su caso.
     */
    private ArrayList<Character> operadores;
    /**
     * Es el escuchador que viene por default con el componente,
     * es que controla los aspectos basicos de la generacion de las fracciones.
     */
    private FraccionListener escuchadorBase;
    /**
     * Es un escuchador adicional que el usuario puede o no colocar,
     * para realizar acciones adicionales en los eventos de la generacion de
     * fracciones.
     */
    private FraccionListener escuchadorPersonal;
    /**
     * Es la parte de la fraccion que se va a ingresar.
     */
    private ParteFraccion parteFraccion;

    /**
     * Constructor que recibe un parametro.
     *
     * @param context Es el contexto donde se coloca el componente.
     */
    public GeneradorFraccion(Context context) {
        this(context, null);
    }

    /**
     * Constructor que recibe dos parametros.
     *
     * @param context Es el contexto donde se coloca el componente.
     * @param attrs   Es el conjunto de atributos del componente.
     */
    public GeneradorFraccion(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Inicializa los componentes del generador de fraccion.
     *
     * @param context Es el contexto en el cual se coloca el componente
     *                GeneradorFraccion.
     */
    private void init(Context context) {
        View rootView = inflate(context, R.layout.fraccion_generador, this);

        int[] ids = {R.id.boton0, R.id.boton1, R.id.boton2, R.id.boton3,
                R.id.boton4, R.id.boton5, R.id.boton6, R.id.boton7,
                R.id.boton8, R.id.boton9, R.id.botonAc, R.id.botonDel,
                R.id.botonParteEnt, R.id.botonParteNum, R.id.botonParteDen,
                R.id.botonReiniciarFraccion, R.id.botonRemoverFraccion};

        AutoResizeTextButton boton0 = (AutoResizeTextButton) rootView
                .findViewById(ids[0]);
        AutoResizeTextButton boton1 = (AutoResizeTextButton) rootView
                .findViewById(ids[1]);
        AutoResizeTextButton boton2 = (AutoResizeTextButton) rootView
                .findViewById(ids[2]);
        AutoResizeTextButton boton3 = (AutoResizeTextButton) rootView
                .findViewById(ids[3]);
        AutoResizeTextButton boton4 = (AutoResizeTextButton) rootView
                .findViewById(ids[4]);
        AutoResizeTextButton boton5 = (AutoResizeTextButton) rootView
                .findViewById(ids[5]);
        AutoResizeTextButton boton6 = (AutoResizeTextButton) rootView
                .findViewById(ids[6]);
        AutoResizeTextButton boton7 = (AutoResizeTextButton) rootView
                .findViewById(ids[7]);
        AutoResizeTextButton boton8 = (AutoResizeTextButton) rootView
                .findViewById(ids[8]);
        AutoResizeTextButton boton9 = (AutoResizeTextButton) rootView
                .findViewById(ids[9]);
        botonAc = (AutoResizeTextButton) rootView.findViewById(ids[10]);
        botonDel = (AutoResizeTextButton) rootView.findViewById(ids[11]);
        botonParteEnt = (AutoResizeTextButton) rootView.findViewById(ids[12]);
        botonParteNum = (AutoResizeTextButton) rootView.findViewById(ids[13]);
        botonParteDen = (AutoResizeTextButton) rootView.findViewById(ids[14]);
        botonReiniciarFraccion = (AutoResizeTextButton) rootView.findViewById
                (ids[15]);
        botonRemoverFraccion = (AutoResizeTextButton) rootView.findViewById
                (ids[16]);

        botonesTemporales = new AutoResizeTextButton[]{boton0, botonAc,
                botonDel};
        botonesNumericos = new AutoResizeTextButton[]{boton0, boton1, boton2,
                boton3,
                boton4, boton5, boton6, boton7, boton8, boton9};

        EscuchadorBotonFormulacionFraccion ebff
                = new EscuchadorBotonFormulacionFraccion();

        for (AutoResizeTextButton boton : botonesNumericos) {
            boton.setOnClickListener(ebff);
        }

        for (AutoResizeTextButton boton : botonesTemporales) {
            boton.setOnClickListener(ebff);
        }

        escuchadorBase = new EscuchadorBase();
        escuchadorPersonal = new FraccionListenerAdapter();

        botonReiniciarFraccion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                escuchadorBase.onFraccionReseted();
                escuchadorPersonal.onFraccionReseted();
            }
        });

        botonRemoverFraccion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                escuchadorBase.onFraccionRemoved();
                escuchadorPersonal.onFraccionRemoved();
            }
        });

        View.OnClickListener listenerParteFraccion = new View.OnClickListener
                () {
            @Override
            public void onClick(View view) {
                AutoResizeTextButton enfocado = null;
                AutoResizeTextButton secundario1 = null;
                AutoResizeTextButton secundario2 = null;

                if (view == botonParteEnt) {
                    enfocado = botonParteEnt;
                    secundario1 = botonParteNum;
                    secundario2 = botonParteDen;
                    parteFraccion = ParteFraccion.ENTERO;
                } else if (view == botonParteNum) {
                    enfocado = botonParteNum;
                    secundario1 = botonParteEnt;
                    secundario2 = botonParteDen;
                    parteFraccion = ParteFraccion.NUMERADOR;
                } else if (view == botonParteDen) {
                    enfocado = botonParteDen;
                    secundario1 = botonParteEnt;
                    secundario2 = botonParteNum;
                    parteFraccion = ParteFraccion.DENOMINADOR;
                }

                enfocado.setAlpha(1.0f);
                secundario1.setAlpha(0.5f);
                secundario2.setAlpha(0.5f);

                updateGUI();
            }
        };

        botonParteEnt.setOnClickListener(listenerParteFraccion);
        botonParteNum.setOnClickListener(listenerParteFraccion);
        botonParteDen.setOnClickListener(listenerParteFraccion);
        reiniciarBotonesParte();
    }

    /**
     * Establece el listener de fraccion personalizado para el componente.
     *
     * @param listener Es el listener a establecer.
     */
    public void setPersonalFraccionListener(FraccionListener listener) {
        this.escuchadorPersonal = listener;
    }

    /**
     * Establece las fracciones de la operacion que se asocia al panel.
     *
     * @param fracciones Son las fracciones de la operacion o comparacion que se
     *                   esta formando.
     */
    public void setFracciones(ArrayList<Fraccion> fracciones) {
        this.fracciones = fracciones;
    }

    /**
     * Establece los operadores de la operacion que se asocia al panel.
     *
     * @param operadores Son los operadores de la operacion que se esta
     *                   formando.
     */
    public void setOperadores(ArrayList<Character> operadores) {
        this.operadores = operadores;
    }

    /**
     * Devuelve la fraccion formada por medio del componente.
     *
     * @return Una instancia de Fraccion, con la fraccion formada.
     */
    public Fraccion getFraccion() {
        if (fracciones != null) {
            if (fracciones.isEmpty()) {
                fracciones.add(new FraccionSimple());
            }

            int ultima = fracciones.size() - 1;

            return fracciones.get(ultima);
        }

        return null;
    }

    /**
     * Determina si la fraccion generada por el panel esta completa (que sus
     * partes asignadas formen una fraccion valida).
     *
     * @return boolean si la fraccion esta completa o false si no lo esta.
     */
    public boolean isCompleta() {
        long num = getFraccion().getNumerador();
        long den = getFraccion().getDenominador();
        long ent = getFraccion().getEntero();

        if (num == 0 && den == 0) {
            if (ent == 0) {
                return false;
            } else {
                return true;
            }
        } else if (num == 0 || den == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Habilita/deshabilita los botones contenidos en un arreglo de JButton.
     *
     * @param botones    Es un arreglo que incluye los botones a modificar.
     * @param habilitado Es el valor que se establecera a la propiedad 'enabled'
     *                   de los botones.
     */
    private void habilitarBotones(AutoResizeTextButton[] botones, boolean
            habilitado) {
        for (AutoResizeTextButton boton : botones) {
            boton.setEnabled(habilitado);
        }
    }

    /**
     * Establece la visibilidad del boton de remover fraccion.
     *
     * @param visible Es el valor que determina si el boton es visible o no.
     */
    public void setBotonRemoverVisible(boolean visible) {
        botonRemover = visible;

        if (visible) {
            botonRemoverFraccion.setVisibility(View.VISIBLE);
        } else {
            botonRemoverFraccion.setVisibility(View.GONE);
        }
    }

    /**
     * Establece si el boton de remover fraccion va a estar habilitado o no.
     *
     * @param enabled Valor que determina si el boton esta habilitado.
     */
    public void setBotonRemoverEnabled(boolean enabled) {
        botonRemoverFraccion.setEnabled(enabled);
    }

    /**
     * Obtiene la parte de la fraccion que se ingresa actualmente.
     *
     * @return La parte de la fraccion.
     */
    public ParteFraccion getParteFraccion() {
        return parteFraccion;
    }

    /**
     * Establece la parte de la fraccion que se desea que sea la que se
     * ingrese con el componente.
     *
     * @param parteFraccion Es la parte de la fraccion a establecer.
     */
    public void setParteFraccion(ParteFraccion parteFraccion) {
        if (parteFraccion == null) {
            parteFraccion = ParteFraccion.NUMERADOR;
        }

        this.parteFraccion = parteFraccion;

        switch (parteFraccion) {
            case ENTERO:
                botonParteEnt.callOnClick();
                break;
            case NUMERADOR:
                botonParteNum.callOnClick();
                break;
            case DENOMINADOR:
                botonParteDen.callOnClick();
                break;
        }
    }

    /**
     * Determina y actualiza los botones del componente segun a la
     * disponibilidad
     * que deban tener en ese momento, habilitandolos o deshabilitandolos.
     */
    public void updateGUI() {
        boolean tieneEntero;
        boolean tieneNumerador;
        boolean tieneDenominador;
        boolean hayVariasFracciones;

        if (getFraccion() == null) {
            tieneEntero = false;
            tieneNumerador = false;
            tieneDenominador = false;
            hayVariasFracciones = false;
        } else {
            tieneEntero = getFraccion().getEntero() != 0l;
            tieneNumerador = getFraccion().getNumerador() != 0l;
            tieneDenominador = getFraccion().getDenominador() != 0l;
            hayVariasFracciones = fracciones.size() > 1;
        }

        if (tieneEntero || tieneNumerador || tieneDenominador) {
            botonReiniciarFraccion.setEnabled(true);
        } else {
            botonReiniciarFraccion.setEnabled(false);
        }

        if (botonRemover) {
            if (tieneEntero || tieneNumerador || tieneDenominador ||
                    hayVariasFracciones) {
                botonRemoverFraccion.setEnabled(true);
            } else {
                botonRemoverFraccion.setEnabled(false);
            }
        }

        boolean activados = true;

        switch (parteFraccion) {
            case ENTERO:
                activados = tieneEntero;
                break;
            case NUMERADOR:
                activados = tieneNumerador;
                break;
            case DENOMINADOR:
                activados = tieneDenominador;
                break;
        }

        habilitarBotones(botonesTemporales, activados);
    }

    /**
     * Reinicia los botones de parte de fraccion.
     */
    public void reiniciarBotonesParte() {
        botonParteNum.callOnClick();
    }

    /**
     * Establece el viewPager donde se encuentra este componente en caso de
     * existir.
     *
     * @param viewPager Es el viewPager a establecer.
     */
    public void setViewPager(CustomViewPager viewPager) {
        this.viewPager = viewPager;
    }

    /**
     * Es el escuchador de fraccion base que contienen todas las instancias
     * de GeneradorFraccion.
     */
    private class EscuchadorBase implements FraccionListener {
        @Override
        public void onFraccionUpdated() {
            updateGUI();
        }

        @Override
        public void onFraccionRemoved() {
            if (fracciones != null) {
                if (fracciones.size() == 1) {
                    onFraccionReseted();
                } else {
                    int indiceFraccion = fracciones.size() - 1;
                    fracciones.remove(indiceFraccion);

                    int indiceOperador = operadores.size() - 1;

                    if (indiceOperador > -1) {
                        operadores.remove(indiceOperador);
                    }

                    reiniciarBotonesParte();
                    updateGUI();
                }
            } else {
                Toast.makeText(getContext(), "ERROR: Operacion nula",
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFraccionReseted() {
            getFraccion().setEntero(0l);
            getFraccion().setNumerador(0l);
            getFraccion().setDenominador(0l);
            reiniciarBotonesParte();
            onFraccionUpdated();
        }
    }

    /**
     * Es un escuchador para los clicks de los botones del componente.
     */
    private class EscuchadorBotonFormulacionFraccion implements
            OnClickListener {
        @Override
        public void onClick(View view) {
            if (view == botonAc) {
                setValorParteFraccion(0l);
            } else if (view == botonDel) {
                setValorParteFraccion(ManejoDigitos
                        .removerUltimoDigito(getValorParteFraccion()));
            } else {
                int indice = Arreglos.indexOf(botonesNumericos, view);
                setValorParteFraccion(ManejoDigitos
                        .agregarDigito(getValorParteFraccion(), indice));

                if (viewPager != null) {
                    viewPager.setPagingEnabled(false);
                    CurrentState.resuelto = false;
                }
            }

            escuchadorBase.onFraccionUpdated();
            escuchadorPersonal.onFraccionUpdated();
        }

        /**
         * Devuelve el valor de la parte de la fraccion controlada por la
         * instancia del listener.
         *
         * @return El valor de la parte de la fraccion.
         */
        private Long getValorParteFraccion() {
            switch (parteFraccion) {
                case ENTERO:
                    return getFraccion().getEntero();
                case NUMERADOR:
                    return getFraccion().getNumerador();
                case DENOMINADOR:
                    return getFraccion().getDenominador();
            }

            return 0l;
        }

        /**
         * Establece el valor a la parte de la fraccion controlada por la
         * instancia del listener.
         *
         * @param valor Es el valor a establecer.
         */
        private void setValorParteFraccion(Long valor) {
            switch (parteFraccion) {
                case ENTERO:
                    getFraccion().setEntero(valor);
                    break;
                case NUMERADOR:
                    getFraccion().setNumerador(valor);
                    break;
                case DENOMINADOR:
                    getFraccion().setDenominador(valor);
                    break;
            }
        }
    }
}

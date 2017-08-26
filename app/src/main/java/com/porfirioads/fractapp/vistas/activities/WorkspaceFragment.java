package com.porfirioads.fractapp.vistas.activities;

import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.controladores.ControladorOperacion;
import com.porfirioads.fractapp.controladores.ControladorResultado;
import com.porfirioads.fractapp.controladores.CurrentState;
import com.porfirioads.fractapp.dominio.html.Documento;
import com.porfirioads.fractapp.dominio.logica.operaciones.Operacion;
import com.porfirioads.fractapp.vistas.utils.GuiUtils;
import com.porfirioads.fractapp.vistas.utils.Mensajes;
import com.porfirioads.fractapp.vistas.R;
import com.porfirioads.fractapp.vistas.componentes.AutoResizeTextButton;
import com.porfirioads.fractapp.vistas.componentes.CustomViewPager;
import com.porfirioads.fractapp.vistas.componentes.GeneradorFraccion;
import com.porfirioads.fractapp.vistas.listeners.FraccionListener;

/**
 * Esta clase es una extension de Fragment y se usa para representar una
 * pagina del ViewPager incluido en la WorkspaceFragmentActivity, este
 * fragment incluye dos modos: fragment de calculadora y fragment de
 * explicacion.
 */
public class WorkspaceFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";
    /**
     * Es el codigo de solicitud para las activities que se muestran como
     * dialogos.
     */
    public static final int REQUEST_CODE = 1;
    /**
     * The fragment's page number, which is set to the argument value for
     * {@link #ARG_PAGE}.
     */
    private int mPageNumber;
    /**
     * Es la vista raiz, de donde podemos obtener los elementos de la GUI por
     * medio de su id.
     */
    private ViewGroup rootView;
    /**
     * Es el documento mostrado en la webView de formulacion de operaciones.
     * Aqui se muestra tanto la formulacion de una operacion basica, asi como
     * los resultados de cualquier operacion o calculo.
     */
    private Documento docOperacion;
    /**
     * Es el documento mostrado en la webView de los pasos de la solucion,
     * mostrandolos.
     */
    private Documento docPasos;
    /**
     * Es la webView donde se muestra la operacion o el resultado de la misma.
     */
    private WebView webViewOperacion;
    /**
     * Es la webView donde se muestran los pasos de la solucion de una
     * operacion.
     */
    private WebView webViewPasos;
    /**
     * Es la instancia del componente para generar fracciones.
     */
    private GeneradorFraccion generadorFraccion;
    /**
     * Es el componente que permite tener paginas en la activity.
     */
    private CustomViewPager viewPager;

    /**
     * Constructor que no recibe argumentos.
     */
    public WorkspaceFragment() {
    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for
     * the given page number.
     */
    public static WorkspaceFragment create(int pageNumber) {
        WorkspaceFragment fragment = new WorkspaceFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
    }

    /**
     * Se ejecuta al momento que se va a crear la actividad con la pagina
     * determinada.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final int idFragment;

        switch (mPageNumber) {
            case 0:
                idFragment = R.layout.fragment_calculadora;
                break;
            case 1:
                idFragment = R.layout.fragment_explicacion;
                break;
            default:
                idFragment = 0;
        }

        rootView = (ViewGroup) inflater.inflate(idFragment, container, false);

        return rootView;
    }


    /**
     * Se ejecuta antes de que el fragment valla a cambiar, por ejemplo,
     * antes de rotar la pantalla.
     *
     * @param outState Es el conjunto de datos que determinan el estado
     *                 actual de la activity, los cuales se desean guardar
     *                 para reestablecerlos cuando la activity se restaure.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (isCalculadoraFragment()) {
            CurrentState.docOperacion = docOperacion;
            CurrentState.operacion = ControladorOperacion.operacion;
            CurrentState.parteFraccion = generadorFraccion.getParteFraccion();
        } else if (isExplicacionFragment()) {
            CurrentState.docPasos = docPasos;
        }

        CurrentState.resuelto = ControladorResultado.resuelto;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            if (Configuracion.mustResetApp(getActivity(), "WorkspaceFragmentActivity")) {
                getActivity().finish();
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                this.startActivity(intent);
                savedInstanceState = null;
            }
        }

//        Log.d("Fractapp", "WorkspaceFragment.onActivityCreated()");

        if (isCalculadoraFragment()) {
            onCalculadoraFragmentCreated(savedInstanceState);
        } else if (isExplicacionFragment()) {
            onExplicacionFragmentCreated(savedInstanceState);
        }
    }

    /**
     * Este metodo se ejecuta cuando el fragment creado corresponde al
     * fragment de calculadora.
     */
    private void onCalculadoraFragmentCreated(Bundle savedInstanceState) {
        generadorFraccion = (GeneradorFraccion) rootView.findViewById(R.id
                .generadorFraccion);

        final AutoResizeTextButton botonMas = (AutoResizeTextButton) rootView
                .findViewById(R.id.botonMas);
        final AutoResizeTextButton botonMenos = (AutoResizeTextButton)
                rootView.findViewById(R.id
                        .botonMenos);
        final AutoResizeTextButton botonPor = (AutoResizeTextButton) rootView
                .findViewById(R.id.botonPor);
        final AutoResizeTextButton botonEntre = (AutoResizeTextButton)
                rootView.findViewById(R.id
                        .botonEntre);
        AutoResizeTextButton botonIgual = (AutoResizeTextButton) rootView
                .findViewById(R.id.botonIgual);
        AutoResizeTextButton botonEquivalentes = (AutoResizeTextButton)
                rootView.findViewById(R.id
                        .botonEquivalentes);
        AutoResizeTextButton botonReciprocas = (AutoResizeTextButton)
                rootView.findViewById(R.id
                        .botonReciprocas);
        AutoResizeTextButton botonMayor = (AutoResizeTextButton) rootView
                .findViewById(R.id.botonMayor);
        AutoResizeTextButton botonSimplificar = (AutoResizeTextButton)
                rootView.findViewById(R.id
                        .botonSimplificar);
        AutoResizeTextButton botonCai = (AutoResizeTextButton) rootView
                .findViewById(R.id.botonCai);
        AutoResizeTextButton botonCam = (AutoResizeTextButton) rootView
                .findViewById(R.id.botonCam);
        AutoResizeTextButton botonMcm = (AutoResizeTextButton) rootView
                .findViewById(R.id.botonMcm);
        AutoResizeTextButton botonMcd = (AutoResizeTextButton) rootView
                .findViewById(R.id.botonMcd);

        View.OnClickListener listenerOperacionBasica = new View
                .OnClickListener() {
            @Override
            public void onClick(View view) {
                char c = ' ';

                if (view == botonMas) {
                    c = '+';
                } else if (view == botonMenos) {
                    c = '-';
                } else if (view == botonPor) {
                    c = '*';
                } else if (view == botonEntre) {
                    c = '÷';
                }

                ControladorOperacion.agregarFraccion(c);
            }
        };

        botonMas.setOnClickListener(listenerOperacionBasica);
        botonMenos.setOnClickListener(listenerOperacionBasica);
        botonPor.setOnClickListener(listenerOperacionBasica);
        botonEntre.setOnClickListener(listenerOperacionBasica);

        botonIgual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControladorOperacion.resolverOperacion();
            }
        });

        botonEquivalentes.setOnClickListener(new EscuchadorComparacion
                (DialogComparacion.EQUIVALENTES));
        botonReciprocas.setOnClickListener(new EscuchadorComparacion
                (DialogComparacion.RECIPROCAS));
        botonMayor.setOnClickListener(new EscuchadorComparacion
                (DialogComparacion.MAYOR));

        botonSimplificar.setOnClickListener(new EscuchadorConversion
                (DialogConversion.SIMPLIFICAR));
        botonCai.setOnClickListener(new EscuchadorConversion(DialogConversion
                .CAI));
        botonCam.setOnClickListener(new EscuchadorConversion(DialogConversion
                .CAM));

        botonMcm.setOnClickListener(new EscuchadorNumeros(DialogNumeros.MCM));
        botonMcd.setOnClickListener(new EscuchadorNumeros(DialogNumeros.MCD));

        Button btnPasos = (Button) rootView.findViewById(R.id.btnPasos);
        btnPasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        generadorFraccion.setBotonRemoverVisible(true);
        generadorFraccion.setPersonalFraccionListener(new FraccionListener() {
            @Override
            public void onFraccionUpdated() {
                ControladorOperacion.updateGUI(false);
            }

            @Override
            public void onFraccionRemoved() {
                ControladorOperacion.updateGUI(false);
            }

            @Override
            public void onFraccionReseted() {
                ControladorOperacion.updateGUI(false);
            }
        });

        viewPager = ((WorkspaceFragmentActivity) getActivity()).getMPager();

        webViewOperacion = (WebView) rootView.findViewById(R.id
                .webViewResultado);
        webViewOperacion.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = webViewOperacion.getSettings();
        webSettings.setJavaScriptEnabled(true);

        ControladorOperacion.generadorFraccion = generadorFraccion;
        ControladorOperacion.botonIgual = botonIgual;
        ControladorOperacion.botonesOperacionesBasicas = new
                AutoResizeTextButton[]{botonMas,
                botonMenos, botonPor, botonEntre};
        generadorFraccion.setViewPager(viewPager);

        ControladorResultado.webViewResultado = webViewOperacion;
        ControladorOperacion.webViewResultado = webViewOperacion;

        // INICIALIZACIONES DE COMPONENTES QUE DEPENDEN DE SI HAY UNA INSTANCIA
        // DE ESTADO GUARDADA.

        ControladorOperacion.viewPager = viewPager;

        if (savedInstanceState != null) {
            docOperacion = CurrentState.docOperacion;
            generadorFraccion.setParteFraccion(CurrentState.parteFraccion);
            viewPager.setPagingEnabled(CurrentState.resuelto);
            Operacion operacion = CurrentState.operacion;
            ControladorOperacion.operacion = operacion;
            generadorFraccion.setFracciones(operacion.getFracciones());
            generadorFraccion.setOperadores(operacion.getOperadores());
            generadorFraccion.updateGUI();
            GuiUtils.loadHtml(webViewOperacion, docOperacion.toString());
            ControladorOperacion.updateGUI(CurrentState.resuelto);
        } else {
            docOperacion = new Documento(Documento.TipoDocumento
                    .RESULTADO_EXPRESION);
            viewPager.setPagingEnabled(false);
            CurrentState.resuelto = false;
            ControladorResultado.docResultado = docOperacion;
            ControladorOperacion.iniciarOperacion();
        }

        GuiUtils.redimensionarTextoBotones(rootView);
    }

    /**
     * Este metodo se ejecuta cuando el fragment creado corresponde al
     * fragment de explicacion.
     */
    private void onExplicacionFragmentCreated(Bundle savedInstanceState) {
        webViewPasos = (WebView) rootView.findViewById(R.id
                .webViewPasos);
        WebSettings settings = webViewPasos.getSettings();
        settings.setJavaScriptEnabled(true);

        if (savedInstanceState != null) {
            docPasos = CurrentState.docPasos;
            GuiUtils.loadHtml(webViewPasos, docPasos.toString());
            CurrentState.viewPager.setPagingEnabled(CurrentState.resuelto);
        } else {
            docPasos = new Documento(Documento.TipoDocumento.PROCEDIMIENTO);
        }

        ControladorResultado.docPasos = docPasos;
        ControladorResultado.webViewPasos = webViewPasos;

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }

    /**
     * Determina si el fragment actual es un fragment de calculadora.
     *
     * @return <code>true</code> si es un fragment de calculadora o
     * <code>false</code> si no lo es.
     */
    public boolean isCalculadoraFragment() {
        return mPageNumber == 0;
    }

    /**
     * Determina si el fragment actual es un fragment de explicacion.
     *
     * @return <code>true</code> si es un fragment de explicacion o
     * <code>false</code> si no lo es.
     */
    public boolean isExplicacionFragment() {
        return mPageNumber == 1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            Mensajes.log("onActivityResult");
            Mensajes.log(isCalculadoraFragment());

            if (requestCode == REQUEST_CODE) {
                if (resultCode == DialogComparacion.RESULTADO_COMPLETADO) {
                    ControladorResultado.docResultado.setTipo
                            (Documento.TipoDocumento.RESULTADO_FRASE);
                    ControladorResultado.colocarResultadoYPasos();
                    viewPager.setPagingEnabled(true);
                } else if (resultCode == DialogComparacion
                        .RESULTADO_CANCELADO) {
                } else if (resultCode == DialogConversion
                        .RESULTADO_COMPLETADO) {
                    ControladorResultado.docResultado.setTipo(Documento
                            .TipoDocumento.RESULTADO_EXPRESION);
                    ControladorResultado.colocarResultadoYPasos();
                    viewPager.setPagingEnabled(true);
                } else if (resultCode == DialogConversion.RESULTADO_CANCELADO) {
                } else if (resultCode == DialogNumeros.RESULTADO_COMPLETADO) {
                } else if (resultCode == DialogNumeros.RESULTADO_CANCELADO) {
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Es un escuchador para cuando se hace click en un boton de alguna
     * comparacion de fracciones.
     */
    private class EscuchadorComparacion implements View.OnClickListener {
        private String tipo;

        public EscuchadorComparacion(String tipo) {
            this.tipo = tipo;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(),
                    DialogComparacion.class);
            intent.putExtra("TIPO", tipo);
            viewPager.setPagingEnabled(false);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    /**
     * Es un escuchador para cuando se hace click en un boton de algun
     * calculo con numeros.
     */
    private class EscuchadorNumeros implements View.OnClickListener {
        private String tipo;

        public EscuchadorNumeros(String tipo) {
            this.tipo = tipo;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(),
                    DialogNumeros.class);
            intent.putExtra("TIPO", tipo);
            viewPager.setPagingEnabled(false);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    private class EscuchadorConversion implements View.OnClickListener {
        private String tipo;

        public EscuchadorConversion(String tipo) {
            this.tipo = tipo;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity(),
                    DialogConversion.class);
            intent.putExtra("TIPO", tipo);
            viewPager.setPagingEnabled(false);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }
}

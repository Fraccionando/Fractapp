package com.porfirioads.fractapp.vistas.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.porfirioads.fractapp.vistas.R;

/**
 * Es un fragment que representa un paso para la FirstStepsActivity, cuenta
 * con un titulo, contenido y numero de pagina. Se desliza para cambiar a
 * otro paso.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class FirstStepFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for
     * {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    /**
     * Constructor que no recibe parametros.
     */
    public FirstStepFragment() {
    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for
     * the given page number.
     */
    public static FirstStepFragment create(int pageNumber) {
        FirstStepFragment fragment = new FirstStepFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_first_step, container, false);

        TextView tituloStep = (TextView) rootView.findViewById(R.id.tituloStep);
        TextView contenidoStep = (TextView) rootView.findViewById(R.id
                .contenidoStep);
        String textoTitulo = getString(R.string.step, mPageNumber + 1) + " ";
        String textoContenido = "";

        switch (mPageNumber) {
            case 0:
                textoTitulo += getString(R.string.step_operaciones_titulo);
                textoContenido = getString(R.string
                        .step_operaciones_descripcion);
                break;
            case 1:
                textoTitulo += getString(R.string.step_comparaciones_titulo);
                textoContenido = getString(R.string
                        .step_comparaciones_descripcion);
                break;
            case 2:
                textoTitulo += getString(R.string.step_conversiones_titulo);
                textoContenido = getString(R.string
                        .step_conversiones_descripcion);
                break;
            case 3:
                textoTitulo += getString(R.string.step_calculos_titulo);
                textoContenido = getString(R.string.step_calculos_descripcion);
                break;
            case 4:
                textoTitulo += getString(R.string.step_explicaciones_titulo);
                textoContenido = getString(R.string
                        .step_explicaciones_descripcion);
                break;
        }

        tituloStep.setText(textoTitulo);
        contenidoStep.setText(textoContenido);

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}

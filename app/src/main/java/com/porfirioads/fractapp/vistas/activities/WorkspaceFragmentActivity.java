package com.porfirioads.fractapp.vistas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.controladores.CurrentState;
import com.porfirioads.fractapp.vistas.R;
import com.porfirioads.fractapp.vistas.componentes.CustomViewPager;

/**
 * Es la acivity donde se contienen los fragments de calculadora y de
 * explicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class WorkspaceFragmentActivity extends FragmentActivity {
    /**
     * Es el numero de paginas a mostrar.
     */
    private static final int NUM_PAGES = 2;
    /**
     * El widget paginador, que controla la animacion y permite arrastrar
     * horizontalmente a la pantalla anterior y siguiente.
     */
    private CustomViewPager mPager;
    /**
     * El adaptador de pagina, que proporciona las paginas para el ViewPager.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuracion.refreshLocale(getApplicationContext());
        setContentView(R.layout.activity_fragment_workspace);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (CustomViewPager) findViewById(R.id.pagerWorkspace);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        CurrentState.viewPager = mPager;
        mPager.setPagingEnabled(false);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    /**
     * Obtiene el paginador de viewPager.
     *
     * @return mPager.
     */
    public CustomViewPager getMPager() {
        return mPager;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getBaseContext(), ConfiguracionActivity
                    .class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A simple pager adapter that represents 5 {@link FirstStepFragment}
     * objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            WorkspaceFragment fragment = WorkspaceFragment.create(position);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Configuracion.setWorkspaceActivityState(this,
                "WorkspaceFragmentActivity", "destroyed");
//        Log.d("Fractapp", "WorkspaceFragmentActivity.onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        Log.d("Fractapp", "WorkspaceFragmentActivity.onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Log.d("Fractapp", "WorkspaceFragmentActivity.onRestart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.d("Fractapp", "WorkspaceFragmentActivity.onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Configuracion.setWorkspaceActivityState(this,
                "WorkspaceFragmentActivity", "none");
//        Log.d("Fractapp", "WorkspaceFragmentActivity.onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d("Fractapp", "WorkspaceFragmentActivity.onResume()");
    }
}


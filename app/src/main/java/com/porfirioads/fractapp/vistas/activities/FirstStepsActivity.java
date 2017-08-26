package com.porfirioads.fractapp.vistas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.porfirioads.fractapp.configuracion.Configuracion;
import com.porfirioads.fractapp.vistas.R;

/**
 * Es la activity que contiene los fragments de primeros pasos de la aplicacion.
 *
 * @author Porfirio Angel Diaz Sanchez [porfirioads@gmail.com]
 */
public class FirstStepsActivity extends FragmentActivity {
    /**
     * Es el numero de paginas a mostrar.
     */
    private static final int NUM_PAGES = 5;

    /**
     * El widger paginador, que controla la animacion y permite arrastrar
     * horizontalmente a la pantalla anterior y siguiente.
     */
    private ViewPager mPager;

    /**
     * El adaptador de pagina, que proporciona las paginas para el ViewPager.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuracion.refreshLocale(getApplicationContext());
        setContentView(R.layout.activity_first_steps);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // Se hace para actualizar el menu, segun la pagina a la que se haya accedido.
                invalidateOptionsMenu();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_screen_slide, menu);

        menu.findItem(R.id.action_previous).setEnabled(mPager.getCurrentItem() > 0);

        // Add either a "next" or "finish" AutoResizeTextButton to the action bar, depending on which page
        // is currently selected.
        MenuItem item = menu.add(Menu.NONE, R.id.action_next, Menu.NONE,
                (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1)
                        ? R.string.action_finish
                        : R.string.action_next);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_previous:
                // Go to the previous step in the wizard. If there is no previous step,
                // setCurrentItem will do nothing.
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                return true;
            case R.id.action_next:
                if (item.getTitle().toString().equals(getString(R.string.action_finish))) {
                    lanzarCalculadora();
                } else {
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.moveTaskToBack(true);
    }

    /**
     * A simple pager adapter that represents 5 {@link FirstStepFragment} objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FirstStepFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private void lanzarCalculadora() {
        Intent intent = new Intent(this, WorkspaceFragmentActivity.class);

        finish();
        startActivity(intent);
    }
}

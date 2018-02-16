package br.com.darisson.noticias.adapter;

import android.support.annotation.Nullable;

import br.com.darisson.noticias.model.Noticia;
import br.com.darisson.noticias.view.fragment.NoticiasFragment_;

public class ScreenSlidePagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

    public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        String category = getCategory(position);
        return NoticiasFragment_.builder().categoria(category).build();
    }

    @Nullable
    private String getCategory(int position) {
        switch (position) {
            case 0:
                return Noticia.TIPO_GERAL;
            case 1:
                return Noticia.TIPO_EVENTOS;
            case 2:
                return Noticia.TIPO_ESPORTES;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "GERAL";
            case 1:
                return "EVENTOS";
            case 2:
                return "ESPORTES";
        }
        return null;
    }
}

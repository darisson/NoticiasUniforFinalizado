package br.com.darisson.noticias.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import br.com.darisson.noticias.R;
import br.com.darisson.noticias.adapter.NoticiasAdapter;
import br.com.darisson.noticias.event.RequestNoticiaFinishedEvent;
import br.com.darisson.noticias.service.NoticiaService;
import br.com.darisson.noticias.utils.EndlessRecyclerOnScrollListener;

@EFragment(R.layout.noticias_fragment)
public class NoticiasFragment extends BaseFragment {

    @FragmentArg
    protected String categoria;

    @ViewById(R.id.recycler_view)
    protected RecyclerView recyclerView;

    @Bean
    protected NoticiasAdapter noticiasAdapter;

    @Bean
    protected NoticiaService noticiaService;
    private int noticiaInicial = 1;
    private int noticiaFinal = 11;

    @AfterViews
    public void afterViews() {
        recyclerView.setAdapter(noticiasAdapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int currentPage) {
                noticiasAdapter.refreshNoticias(categoria);
                noticiaService.requestNoticia(categoria, noticiaInicial = noticiaInicial+11, noticiaFinal = noticiaFinal+11);
            }
        });
        noticiasAdapter.refreshNoticias(categoria);
        noticiaService.requestNoticia(categoria, noticiaInicial, noticiaFinal);
        swipeRefreshLayout();
        swipeColor();
    }

    private void swipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noticiaService.requestNoticia(categoria, noticiaInicial, noticiaFinal);
            }
        });
    }

    @Subscribe
    public void onEvent(RequestNoticiaFinishedEvent event) {
        swipeRefreshLayout.setRefreshing(false);
        noticiasAdapter.refreshNoticias(categoria);
    }
}
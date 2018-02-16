package br.com.darisson.noticias.view.activity;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import br.com.darisson.noticias.R;
import br.com.darisson.noticias.adapter.NoticiasAdapter;
import br.com.darisson.noticias.adapter.ScreenSlidePagerAdapter;
import br.com.darisson.noticias.event.NoticiaClickEvent;
import br.com.darisson.noticias.event.RequestFailedEvent;
import br.com.darisson.noticias.service.NoticiaService;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.tabs)
    protected TabLayout tabLayout;

    @ViewById(R.id.scroll_View)
    protected ViewPager viewPager;

    @ViewById(R.id.swipe)
    protected SwipeRefreshLayout swipeRefreshLayout;


    @Bean
    protected NoticiasAdapter noticiasAdapter;

    @Bean
    protected NoticiaService noticiaService;

    @AfterViews
    protected void afterViews() {
        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(this.getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Subscribe
    public void onEvent(NoticiaClickEvent event){
        DetalhesActivity_.intent(this).idNoticia(event.getId()).start();
    }

    @Subscribe
    public void onEvent(RequestFailedEvent event) {
        if (event.getMessage() != null) {
            String errorMsg = getString(R.string.err_message);
            if (event.isDefaultError()){
                if(event.getMessage().equals("")){
                    errorMsg = getString(event.getErr_message());
                }else{
                    errorMsg = event.getMessage();
                }

            }
            Snackbar.make(tabLayout, event.getErr_message(), Snackbar.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);

        }
    }

}

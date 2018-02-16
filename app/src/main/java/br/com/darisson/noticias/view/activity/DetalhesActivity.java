package br.com.darisson.noticias.view.activity;

import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.ViewById;
import br.com.darisson.noticias.R;
import br.com.darisson.noticias.adapter.NoticiasAdapter;
import br.com.darisson.noticias.model.Noticia;
import br.com.darisson.noticias.service.NoticiaService;

@EActivity(R.layout.activity_detalhes_noticia)
public class DetalhesActivity extends BaseActivity {

    @ViewById(R.id.image_noticia)
    protected ImageView imageView;

    @ViewById(R.id.titulo_noticia)
    protected TextView titulo;

    @ViewById(R.id.data_noticia)
    protected TextView data;

    @ViewById(R.id.corpo_noticia)
    protected TextView corpo;

    @ViewById(R.id.toolbar_include)
    protected android.support.v7.widget.Toolbar toolbar;

    @Extra
    protected Integer idNoticia;

    @Bean
    protected NoticiasAdapter adapter;

    @Bean
    protected NoticiaService noticiaService;

    @AfterViews
    protected void afterViews() {

        Noticia noticia = noticiaService.getNoticia(idNoticia);

        Picasso.with(imageView.getContext())
                .load(noticia.getUrlImg())
                .resize(500, 400)
                .into(imageView);

        titulo.setText(noticia.getTitulo());
        data.setText(noticia.getDataPublicacao());
        corpo.setText(stripHtml(noticia.getCorpo()));

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_custom_toobar);
        }

    }

    public Spanned stripHtml(String html) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(html);
        }
    }

    @OptionsItem(android.R.id.home)
    void homeButtonClicked() {
        onBackPressed();
    }
}

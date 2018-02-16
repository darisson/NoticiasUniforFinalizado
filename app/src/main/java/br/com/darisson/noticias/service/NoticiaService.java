package br.com.darisson.noticias.service;

import android.content.Context;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import br.com.darisson.noticias.R;
import br.com.darisson.noticias.event.RequestFailedEvent;
import br.com.darisson.noticias.event.RequestNoticiaFinishedEvent;
import br.com.darisson.noticias.model.Data;
import br.com.darisson.noticias.model.Noticia;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EBean
public class NoticiaService extends BaseNoticiaService {

    @RootContext
    protected Context context;

    public void requestNoticia(String tipoNoticia, int regIni, int regFim) {
        getNoticiaAPI().getNoticiaTipo(tipoNoticia, regIni, regFim).enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        salvaNoticia(response.body().getData());
                        EventBus.getDefault().post(new RequestNoticiaFinishedEvent());
                    } else {
                        EventBus.getDefault().post(new RequestFailedEvent(response.message(), false, R.string.err_message));
                    }
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                EventBus.getDefault().post(new RequestFailedEvent("", true, R.string.err_message));

            }
        });
    }

    private void salvaNoticia(RealmList<Noticia> noticia) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(noticia);
        realm.commitTransaction();
    }

    public Noticia getNoticia(Integer id) {
        Realm realm = Realm.getDefaultInstance();

        Noticia noticia = realm
                .where(Noticia.class)
                .equalTo("id", id)
                .findFirst();

        realm.close();

        if (noticia != null) {
            return realm.copyFromRealm(noticia);
        } else {
            return null;
        }
    }

    public List<Noticia> getNoticias(String tipoNoticia) {
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Noticia> noticiasTipo = realm
                .where(Noticia.class)
                .equalTo("tipo", tipoNoticia)
                .findAll();

        if (noticiasTipo != null) {
            return realm.copyFromRealm(noticiasTipo);
        } else {
            return null;
        }
    }
}
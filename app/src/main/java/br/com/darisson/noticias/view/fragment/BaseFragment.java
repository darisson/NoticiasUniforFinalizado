package br.com.darisson.noticias.view.fragment;

import android.support.v4.widget.SwipeRefreshLayout;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import br.com.darisson.noticias.R;

@EFragment
public class BaseFragment extends android.support.v4.app.Fragment {

    @ViewById(R.id.swipe)
    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onStart() {
        super.onStart();
        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    public void swipeColor() {
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress);
    }
}

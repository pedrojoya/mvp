package es.iessaladillo.pedrojoya.pr209.base;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.v7.app.AppCompatActivity;

public class BaseLifecycleActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private LifecycleRegistry mRegistry = new LifecycleRegistry(this);

    @Override
    public LifecycleRegistry getLifecycle() {
        return mRegistry;
    }

}

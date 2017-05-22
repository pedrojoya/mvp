package es.iessaladillo.pedrojoya.pr208.ui.main;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import es.iessaladillo.pedrojoya.pr208.R;
import es.iessaladillo.pedrojoya.pr208.databinding.ActivityMainBinding;

public class MainActivity extends LifecycleActivity {

    private ActivityMainBinding mBinding;
    private MainActivityViewModel mViewModel;
    private MainActivityBindingVariable mBindingVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Se obtiene el viewModel
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        // Se crea el binding.
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBindingVariable = new MainActivityBindingVariable();
        mBinding.setBv(mBindingVariable);
        mBinding.setPresenter(mViewModel);
        // Se observa el contador, que al cambiar modifica una propiedad de la variable de binding.
        mViewModel.getContador().observe(this, integer -> mBindingVariable.setContador(integer));
    }

}

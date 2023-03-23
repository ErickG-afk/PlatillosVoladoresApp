package com.example.platillosvoladoresapp.activity.ui.inicio;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.adapter.CategoriaAdapter;
import com.example.platillosvoladoresapp.adapter.SliderAdapter;
import com.example.platillosvoladoresapp.entity.SliderItem;
import com.example.platillosvoladoresapp.viewmodel.CategoriaViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class InicioFragment extends Fragment {

    private SliderView svCarousell;
    private SliderAdapter sliderAdapter;
    private CategoriaViewModel categoriaViewModel;
    private GridView gvCategorias;
    private CategoriaAdapter categoriaAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initAdapter();
        loadData();

    }

    private void loadData() {

        List<SliderItem> list = new ArrayList<>();
        list.add(new SliderItem(R.drawable.tortilla_de_patatas,"Tortilla"));
        list.add(new SliderItem(R.drawable.croquetas,"Croquetas"));
        list.add(new SliderItem(R.drawable.paella,"Paella"));
        sliderAdapter.updateItem(list);
        categoriaViewModel.listarCategoriasActivas().observe(getViewLifecycleOwner(), response -> {
            if(response.getRpta() ==1){
                categoriaAdapter.clear();
                categoriaAdapter.addAll(response.getBody());
                categoriaAdapter.notifyDataSetChanged();
            }else{
                System.out.println("Error al obtener las categorías activas");
            }
        });
    }

    private void initAdapter() {
        //Carrusel de Imágenes
        sliderAdapter = new SliderAdapter(getContext());

        svCarousell.setSliderAdapter(sliderAdapter);
        svCarousell.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        svCarousell.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        svCarousell.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        svCarousell.setIndicatorSelectedColor(Color.WHITE);
        svCarousell.setIndicatorUnselectedColor(Color.GRAY);
        svCarousell.setScrollTimeInSec(4); //set scroll delay in seconds :
        svCarousell.startAutoCycle();
        categoriaAdapter = new CategoriaAdapter(getContext(), R.layout.item_categorias, new ArrayList<>());
        gvCategorias.setAdapter(categoriaAdapter);

    }

    private void init(View view) {
        svCarousell = view.findViewById(R.id.svCarrusel);
        ViewModelProvider vmp = new ViewModelProvider(this);
        categoriaViewModel = vmp.get(CategoriaViewModel.class);
        gvCategorias = view.findViewById(R.id.gvCategorias);
    }


}
package com.example.platillosvoladoresapp.activity.ui.inicio;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.adapter.SliderAdapter;
import com.example.platillosvoladoresapp.entity.SliderItem;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class InicioFragment extends Fragment {

    private SliderView svCarousell;
    private SliderAdapter sliderAdapter;

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
        list.add(new SliderItem(R.drawable.tortilla_de_patatas,"Tortilla de patatas"));
        list.add(new SliderItem(R.drawable.croquetas,"Croquetas"));
        list.add(new SliderItem(R.drawable.paella,"Paella"));
        sliderAdapter.updateItem(list);
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
    }

    private void init(View view) {
        svCarousell = view.findViewById(R.id.svCarrusel);
    }


}
package com.example.platillosvoladoresapp.activity.ui.inicio;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.adapter.CategoriaAdapter;
import com.example.platillosvoladoresapp.adapter.PlatoRecomendadoAdapter;
import com.example.platillosvoladoresapp.adapter.SliderAdapter;
import com.example.platillosvoladoresapp.communication.Communication;
import com.example.platillosvoladoresapp.communication.MostrarBadgeCommunication;
import com.example.platillosvoladoresapp.entity.SliderItem;
import com.example.platillosvoladoresapp.entity.service.DetallePedido;
import com.example.platillosvoladoresapp.entity.service.Plato;
import com.example.platillosvoladoresapp.utils.Carrito;
import com.example.platillosvoladoresapp.viewmodel.CategoriaViewModel;
import com.example.platillosvoladoresapp.viewmodel.PlatoViewModel;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class InicioFragment extends Fragment implements Communication, MostrarBadgeCommunication {

    private SliderView svCarousell;
    private SliderAdapter sliderAdapter;
    private CategoriaViewModel categoriaViewModel;
    private GridView gvCategorias;
    private CategoriaAdapter categoriaAdapter;
    private PlatoViewModel platoViewModel;
    private RecyclerView rcvPlatosRecomendados;
    private PlatoRecomendadoAdapter platoRecomendadoAdapter;
    private List<Plato> platos = new ArrayList<>();

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

        platoViewModel.listarPlatosRecomendados().observe(getViewLifecycleOwner(), response ->{
            platoRecomendadoAdapter.updateItems(response.getBody());
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

        platoRecomendadoAdapter = new PlatoRecomendadoAdapter(platos, this, this);
        rcvPlatosRecomendados.setAdapter(platoRecomendadoAdapter);

    }

    private void init(View view) {
        svCarousell = view.findViewById(R.id.svCarrusel);
        ViewModelProvider vmp = new ViewModelProvider(this);

        categoriaViewModel = vmp.get(CategoriaViewModel.class);
        gvCategorias = view.findViewById(R.id.gvCategorias);

        rcvPlatosRecomendados = view.findViewById(R.id.rcvPlatillosRecomendados);
        rcvPlatosRecomendados.setLayoutManager(new GridLayoutManager(getContext(), 1));
        platoViewModel = vmp.get(PlatoViewModel.class);

    }


    @Override
    public void showDetails(Intent i) {
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }


    @SuppressLint("UnsafeOptInUsageError")
    @Override
    public void add(DetallePedido dp) {
        successMessage(Carrito.agregarPlatillos(dp));
        //BadgeDrawable badgeDrawable = BadgeDrawable.create(this.getContext());
        //badgeDrawable.setNumber(Carrito.getDetallePedidos().size());
        //BadgeUtils.attachBadgeDrawable(badgeDrawable, getActivity().findViewById(R.id.toolbar), R.id.bolsaCompras);
    }
    public void successMessage(String message) {
        new SweetAlertDialog(this.getContext(),
                SweetAlertDialog.SUCCESS_TYPE).setTitleText("Buen Trabajo!")
                .setContentText(message).show();
    }
}
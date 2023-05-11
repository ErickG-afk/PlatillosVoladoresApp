package com.example.platillosvoladoresapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.platillosvoladoresapp.R;
import com.example.platillosvoladoresapp.entity.SliderItem;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private Context context;
    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item,null);

        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        SliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textView.setText(sliderItem.getTitle());
        viewHolder.textView.setTextSize(20);
        viewHolder.textView.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView)
                .load(sliderItem.getImage())
                .fitCenter()
                .into(viewHolder.imgView);
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    public void updateItem(List<SliderItem> list) {
        mSliderItems.clear();
        mSliderItems.addAll(list);
        this.notifyDataSetChanged();
    }

    protected class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imgView;
        TextView textView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.iv_auto_image_slider);
            textView = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
}

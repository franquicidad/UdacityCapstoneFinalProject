package com.example.mac.udacitycapstonefinalproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mac.udacitycapstonefinalproject.Model.Automoviles;
import com.example.mac.udacitycapstonefinalproject.Widget.CarWidget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterViewCars extends RecyclerView.Adapter<AdapterViewCars.RecyclerViewHolder>{
    private List<Automoviles> automovilesList;
    private Context context;

    public AdapterViewCars(Context context, List<Automoviles> itemList){
        this.automovilesList=itemList;
        this.context=context;
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_carros,null);
        RecyclerViewHolder rcv=new RecyclerViewHolder(view);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        Automoviles automoviles=automovilesList.get(position);
        holder.txtTitulo.setText(automoviles.getMarca());
        Glide.with(context)
                .load(automoviles.getImagen())
                .into(holder.imgCarro);

        Intent imageIntent=new Intent(context, CarWidget.class);
        imageIntent.putExtra("widget_image",automoviles);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Automoviles model = automovilesList.get(position);
                Intent intent  =new Intent(context, CarDetailView.class);

                intent.putExtra("ListaVehiculos",model);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return this.automovilesList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder  {

        @BindView(R.id.imgCarro)
        ImageView imgCarro;
        @BindView(R.id.txttitulo)
        TextView txtTitulo;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

    }

}


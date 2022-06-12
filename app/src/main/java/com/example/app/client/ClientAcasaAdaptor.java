package com.example.app.client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app.R;
import com.example.app.admin.UpdateTortModel;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ClientAcasaAdaptor extends RecyclerView.Adapter<ClientAcasaAdaptor.ViewHolder> {
    private Context mContext;
    private List<UpdateTortModel> updateCakeModelList;
    DatabaseReference databaseReference;

    public ClientAcasaAdaptor(Context mContext, List<UpdateTortModel> updateCakeModelList) {
        this.mContext = mContext;
        this.updateCakeModelList = updateCakeModelList;
    }

    @NonNull
    @Override
    public ClientAcasaAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.client_meniu_tort, parent, false);
        return new ClientAcasaAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientAcasaAdaptor.ViewHolder holder, int position) {
        final UpdateTortModel updateCakeModel = updateCakeModelList.get(position);
        Glide.with(mContext).load(updateCakeModel.getImageURL()).into(holder.imageView);
        holder.cakeName.setText(updateCakeModel.getTorturi());
        updateCakeModel.getRandomUID();
        updateCakeModel.getAdmID();
        holder.price.setText("Pret: " + updateCakeModel.getPret() + " RON");
    }

    @Override
    public int getItemCount() {
        return updateCakeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView cakeName, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imaginetort);
            cakeName = itemView.findViewById(R.id.numetort);
            price = itemView.findViewById(R.id.prettort);
        }
    }
}

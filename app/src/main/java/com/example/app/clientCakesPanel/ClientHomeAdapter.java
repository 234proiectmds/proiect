package com.example.app.clientCakesPanel;

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
import com.example.app.UpdateCakeModel;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ClientHomeAdapter extends RecyclerView.Adapter<ClientHomeAdapter.ViewHolder> {
    private Context mContext;
    private List<UpdateCakeModel> updateCakeModelList;
    DatabaseReference databaseReference;

    public ClientHomeAdapter(Context mContext, List<UpdateCakeModel> updateCakeModelList) {
        this.mContext = mContext;
        this.updateCakeModelList = updateCakeModelList;
    }


    @NonNull
    @Override
    public ClientHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.client_menucake, parent, false);
        return new ClientHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientHomeAdapter.ViewHolder holder, int position) {
        final UpdateCakeModel updateCakeModel = updateCakeModelList.get(position);
        Glide.with(mContext).load(updateCakeModel.getImageURL()).into(holder.imageView);
        holder.cakeName.setText(updateCakeModel.getPrice());
        updateCakeModel.getRandomUID();
        updateCakeModel.getAdminID();
        holder.price.setText("Pret: " + updateCakeModel.getPrice() + " RON");
    }

    @Override
    public int getItemCount() {
        return updateCakeModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView cakeName, price;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imaginetort);
            cakeName = itemView.findViewById(R.id.numetort);
            price = itemView.findViewById(R.id.prettort);
        }
    }
}

package com.example.app.admin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;

import java.util.List;

public class AdminAcasaAdaptor extends RecyclerView.Adapter<AdminAcasaAdaptor.ViewHolder> {
    private Context mcont;
    private List<UpdateTortModel> updateCakeModelList;

    public AdminAcasaAdaptor(Context context, List<UpdateTortModel> updateCakeModelList) {
        this.updateCakeModelList = updateCakeModelList;
        this.mcont = context;
    }

    @NonNull
    @Override
    public AdminAcasaAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcont).inflate(R.layout.admin_update_delete, parent, false);
        return new AdminAcasaAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAcasaAdaptor.ViewHolder holder, int position) {
        final UpdateTortModel updateCakeModel = updateCakeModelList.get(position);
        holder.dishes.setText(updateCakeModel.getTorturi());
        updateCakeModel.getRandomUID();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mcont, UpdateDeleteTort.class);
                intent.putExtra("updatedeletecake", updateCakeModel.getRandomUID());
                mcont.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return updateCakeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dishes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishes = itemView.findViewById(R.id.dish_name);
        }
    }
}

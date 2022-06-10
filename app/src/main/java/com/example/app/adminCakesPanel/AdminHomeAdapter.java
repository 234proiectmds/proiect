package com.example.app.adminCakesPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.R;
import com.example.app.UpdateCakeModel;

import java.util.List;

public class AdminHomeAdapter extends RecyclerView.Adapter<AdminHomeAdapter.ViewHolder> {
    private Context mcont;
    private List<UpdateCakeModel> updateCakeModelList;

    public AdminHomeAdapter(Context context, List<UpdateCakeModel> updateCakeModelList){
        this.updateCakeModelList = updateCakeModelList;
        this.mcont = context;
    }

    @NonNull
    @Override
    public AdminHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcont).inflate(R.layout.admin_update_delete,parent,false);
        return new AdminHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminHomeAdapter.ViewHolder holder, int position) {
        final UpdateCakeModel updateCakeModel = updateCakeModelList.get(position);
        holder.dishes.setText(updateCakeModel.getTorturi());
        updateCakeModel.getRandomUID();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcont, UpdateDelete_Cake.class);
                intent.putExtra("updatedeletecake",updateCakeModel.getRandomUID());
                mcont.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return updateCakeModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dishes;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            dishes = itemView.findViewById(R.id.dish_name);
        }
    }
}

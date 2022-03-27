package com.example.firestore_sample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    String TAG = "userRecyclerViewAdapter";

    ArrayList<UserModel> userModels;

    public userRecyclerViewAdapter(ArrayList<UserModel> userModels) {
        this.userModels = userModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //자신이 만든 itemview를 inflate한 다음 뷰홀더 생성
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false);
        //생선된 뷰홀더를 리턴하여 onBindViewHolder에 전달한다.
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tvFirstName.setText(userModels.get(position).getFirstName());
        viewHolder.tvLastName.setText(userModels.get(position).getLastName());
        viewHolder.tvBorn.setText( Integer.toString(userModels.get(position).getBorn()));
    }

    @Override
    public int getItemCount() {
        return userModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFirstName;
        TextView tvLastName;
        TextView tvBorn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tv_first_name);
            tvLastName = itemView.findViewById(R.id.tv_last_name);
            tvBorn = itemView.findViewById(R.id.tv_born);
        }
    }
}

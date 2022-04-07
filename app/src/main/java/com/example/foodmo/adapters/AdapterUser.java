package com.example.foodmo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodmo.chatActivity;
import com.example.foodmo.models.Modelclass;
import com.example.foodmo.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUser extends RecyclerView.Adapter<AdapterUser.MyHolder> {

    Context context;
    List<Modelclass> userList;

    public AdapterUser(Context context, List<Modelclass> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int position) {

        String hisUID = userList.get(position).getUid();

        String userImage = userList.get(position).getImage();
        String userName = userList.get(position).getName();

        String userEmail = userList.get(position).getEmail();

        myHolder.mNameTv.setText(userName);
        myHolder.mEmailTv.setText(userEmail);

        try{
            Picasso.get().load(userImage).placeholder(R.drawable.ic_edit_userface).into(myHolder.mAvatarIv);


        }
        catch(Exception e){

        }
        
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, chatActivity.class);
                intent.putExtra("hisUid",hisUID);
                context.startActivity(intent);

            }


        });



    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv;
        TextView mNameTv,mEmailTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);

            mEmailTv = itemView.findViewById(R.id.emailTv);





        }
    }

}

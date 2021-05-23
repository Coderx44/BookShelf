package com.mitadt.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
private Context context;
private List<Books> booksList;

    public RecyclerViewAdapter(Context context, List<Books> booksList) {
        this.context = context;
        this.booksList = booksList;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Books books = booksList.get(position);
        holder.bookname.setText(books.getBookName());
        holder.bookprice.setText(books.getPrice());
        Log.d("img", books.getImgurl());
        if(books.getImgurl().length()>0) {
            Glide.with(context).load(books.getImgurl()).into(holder.bookimg);
        }

    }


    @Override
    public int getItemCount() {
        return booksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView bookname, bookprice;
        public ImageView bookimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            bookname = itemView.findViewById(R.id.textView3);
            bookimg = itemView.findViewById(R.id.imageView);
            bookprice = itemView.findViewById(R.id.textView9);
            bookimg.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("recycleview", "clicked");
        }
    }
}

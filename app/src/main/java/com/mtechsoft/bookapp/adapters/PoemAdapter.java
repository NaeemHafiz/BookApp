package com.mtechsoft.bookapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mtechsoft.bookapp.R;
import com.mtechsoft.bookapp.models.Chapter;
import com.mtechsoft.bookapp.models.Poem;

import java.util.ArrayList;

public class PoemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Poem> poems;
    private Callback callback;

    public PoemAdapter(Context context, ArrayList<Poem> poems, Callback callback) {
        this.context = context;
        this.callback = callback;
        this.poems = poems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_poems, parent, false);
        return new BookViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        BookViewHolder holder1 = (BookViewHolder) holder;
        holder1.bind(position);
    }

    @Override
    public int getItemCount() {
        return poems.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView tvPoemName;
        LinearLayout llITem;

        private BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPoemName = itemView.findViewById(R.id.tvPoemName);
            llITem = itemView.findViewById(R.id.llItem);

        }

        private void bind(int pos) {
            Poem poem = poems.get(pos);
            tvPoemName.setText(poem.getPoemName());
            initClickListener();
        }

        private void initClickListener() {

            llITem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    public interface Callback {
        void onItemClick(int pos);
    }
}

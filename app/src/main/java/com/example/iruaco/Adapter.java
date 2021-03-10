package com.example.iruaco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    EditText edit_text;
    TextView text_view;

    private LayoutInflater layoutInflater;
    // data to be injected
    private List <Questions> data;

    Adapter(Context context, List<Questions> data, EditText edit_text, TextView text_view) {
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.edit_text = edit_text;
        this.text_view = text_view;
    }

    public void update_list(List <Questions> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.tiles,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // where to bind the input array data to the textviews
        final String adapter_english = data.get(position).question;
        final String adapter_translated = data.get(position).translated_question;
        //String adapter_translate = data.get(position).translated_question;
        holder.english_text.setText(adapter_english);
        holder.translated_text.setText(adapter_translated);

        holder.tile_click.setOnClickListener(new View.OnClickListener() {
            String temp = adapter_english;
            @Override
            public void onClick(View v) {
                System.out.println(adapter_english);
                edit_text.setText(adapter_english);
                text_view.setText(adapter_translated);

            }
        });
    }

    // render number
    @Override
    public int getItemCount() {
        //return data.size();
        return 15 ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView english_text,translated_text;
        ConstraintLayout tile_click;
        EditText translate_edit_text;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // declare textviews
            english_text = itemView.findViewById(R.id.english_text);
            translated_text = itemView.findViewById(R.id.translated_text);
            tile_click = itemView.findViewById(R.id.tile_click);
            translate_edit_text = itemView.findViewById(R.id.translate_edit_text);
        }
    }
}
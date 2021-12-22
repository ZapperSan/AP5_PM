package cz.utb.fai.apiapp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.text.PrecomputedTextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cz.utb.fai.apiapp.R;

public class MainRecyclerView extends RecyclerView.Adapter<MainRecyclerView.ViewHolder> {

    private final JSONArray mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // data is passed into the constructor
    MainRecyclerView(Context context, JSONArray data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.main_recycler_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try
        {
            JSONObject spell = mData.getJSONObject(position);
            String cardTitle = spell.getString("name");
            String cardClass = spell.getString("dnd_class");
            String cardLevel = spell.getString("level");
            holder.cardTitle.setText(cardTitle);
            holder.cardClass.setText(cardClass);
            holder.cardLevel.setText(cardLevel);

            switch(spell.getString("school"))
            {
                case "Evocation":
                        holder.card.setCardBackgroundColor(Color.parseColor("#ff7363"));
                    break;
                case "Conjuration":
                    holder.card.setCardBackgroundColor(Color.parseColor("#ffbf36"));
                    break;
                case "Divination":
                    holder.card.setCardBackgroundColor(Color.parseColor("#c4dfff"));
                    break;
                case "Abjuration":
                    holder.card.setCardBackgroundColor(Color.parseColor("#6392ff"));
                    break;
                case "Enchantment":
                    holder.card.setCardBackgroundColor(Color.parseColor("#faa1ff"));
                    break;
                case "Illusion":
                    holder.card.setCardBackgroundColor(Color.parseColor("#976eff"));
                    break;
                case "Necromancy":
                    holder.card.setCardBackgroundColor(Color.parseColor("#a5ff8a"));
                    break;
                case "Transmutation":
                    holder.card.setCardBackgroundColor(Color.parseColor("#ff9f63"));
                    break;
                default:
                    break;
            }

            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    try
                    {
                        intent.putExtra("json",spell.toString());
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        try
        {
            return mData.getJSONObject(id).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "ERROR!"+e;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.length();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView card;
        TextView cardTitle;
        TextView cardClass;
        TextView cardLevel;

        ViewHolder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            cardClass = itemView.findViewById(R.id.cardClass);
            cardLevel = itemView.findViewById(R.id.cardLevel);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
package com.learntodroid.androidminesweeper.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.learntodroid.androidminesweeper.listner.OnCellClickListener;
import com.learntodroid.androidminesweeper.R;
import com.learntodroid.androidminesweeper.entity.Cell;

import java.util.List;

/*
Adapter class for Listners
 */
public class MineGridRecyclerAdapter extends RecyclerView.Adapter<MineGridRecyclerAdapter.MineTileViewHolder> {
    private List<Cell> cells;
    private OnCellClickListener listener;

    public MineGridRecyclerAdapter(List<Cell> cells, OnCellClickListener listener) {
        this.cells = cells;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MineTileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cell, parent, false);
        return new MineTileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MineTileViewHolder holder, int position) {
        holder.bind(cells.get(position));
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return cells.size();
    }

    public void setCells(List<Cell> cells) {
        this.cells = cells;
        notifyDataSetChanged();
    }


/*
Class for Listing the Bombs, Flags, and Numbers
 */
    class MineTileViewHolder extends RecyclerView.ViewHolder {
        TextView valueTextView;

        public MineTileViewHolder(@NonNull View itemView) {
            super(itemView);

            valueTextView = itemView.findViewById(R.id.item_cell_value);
        }

//      Displaying values in text field
        public void bind(final Cell cell) {
            itemView.setBackgroundColor(Color.LTGRAY);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.cellClick(cell);
                }
            });

            if (cell.isRevealed()) {
                if (cell.getValue() == Cell.BOMB) {
                    valueTextView.setText(R.string.bomb);
                } else if (cell.getValue() == Cell.BLANK) {
                    valueTextView.setText("");
                    itemView.setBackgroundColor(Color.DKGRAY);
                } else {
                    valueTextView.setText(String.valueOf(cell.getValue()));
                    if (cell.getValue() == 1) {
                        valueTextView.setTextColor(Color.BLUE);
                        valueTextView.setTextSize(20);
                        valueTextView.setWidth(40);
                    } else if (cell.getValue() == 2) {
                        valueTextView.setTextColor(Color.BLACK);
                        valueTextView.setTextSize(20);
                    } else if (cell.getValue() == 3) {
                        valueTextView.setTextColor(Color.RED);
                        valueTextView.setTextSize(20);
                    }
                }
            } else if (cell.isFlagged()) {
                valueTextView.setText(R.string.flag);
            }
        }
    }
}

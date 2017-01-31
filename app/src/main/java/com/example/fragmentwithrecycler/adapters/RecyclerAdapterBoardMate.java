package com.example.fragmentwithrecycler.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.BoardMatesDB;
import com.example.fragmentwithrecycler.activities.BoardMateDetailActivity;
import com.example.fragmentwithrecycler.activities.MainActivity;
import com.example.fragmentwithrecycler.models.BoardMate;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterBoardMate extends RecyclerView.Adapter<RecyclerAdapterBoardMate.ViewHolder> {


    static List<BoardMate> boardMateList;
    static Context context;
    static BoardMatesDB bHelper;

    public RecyclerAdapterBoardMate(Context context, List<BoardMate> boardMateList) {
        this.boardMateList = new ArrayList<>();
        this.context = context;
        this.boardMateList = boardMateList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.item_board_mate, null);

        // Create viewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(boardMateList.get(position).getmName());
        holder.tvAddress.setText(boardMateList.get(position).getmAddress());
        holder.tvNumber.setText(boardMateList.get(position).getmNumber());
        holder.tvPayable.setText(String.valueOf(boardMateList.get(position).getmPayable()));
    }

    @Override
    public int getItemCount() {
        return boardMateList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView tvName, tvAddress, tvNumber, tvPayable;
        public ImageView imageIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.textViewBoardMateName);
            tvAddress = (TextView) itemView.findViewById(R.id.textViewAddress);
            tvNumber = (TextView) itemView.findViewById(R.id.textViewNumber);
            imageIcon = (ImageView) itemView.findViewById(R.id.imageView);
            tvPayable = (TextView) itemView.findViewById(R.id.textViewPayable);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, BoardMateDetailActivity.class);
            intent.putExtra(MainActivity.FRAGMENT_TO_LAUNCH, MainActivity.FRAGMENT_TO_LOAD.VIEW);
            intent.putExtra(MainActivity.BOARDMATE_ID, boardMateList.get(getAdapterPosition()).getmId());
            intent.putExtra(MainActivity.BOARDMATE_NAME, boardMateList.get(getAdapterPosition()).getmName());
            intent.putExtra(MainActivity.BOARDMATE_ADDRESS, boardMateList.get(getAdapterPosition()).getmAddress());
            intent.putExtra(MainActivity.BOARDMATE_NUMBER, boardMateList.get(getAdapterPosition()).getmNumber());
            intent.putExtra(MainActivity.BOARDMATE_STATUS, boardMateList.get(getAdapterPosition()).getmStatus());
            intent.putExtra(MainActivity.BOARDMATE_AMOUNT, boardMateList.get(getAdapterPosition()).getmPayable());
            context.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            createPop(view);
            return false;
        }

        private void createPop(final View view){
            PopupMenu popup = new PopupMenu(context, view);
            popup.setGravity(Gravity.CENTER);
            popup.inflate(R.menu.menu_long_press);
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch(item.getItemId()){
                        case R.id.menu_edit:
                            Intent intent = new Intent(context, BoardMateDetailActivity.class);
                            intent.putExtra(MainActivity.FRAGMENT_TO_LAUNCH, MainActivity.FRAGMENT_TO_LOAD.EDIT);
                            intent.putExtra(MainActivity.BOARDMATE_ID, boardMateList.get(getAdapterPosition()).getmId());
                            intent.putExtra(MainActivity.BOARDMATE_NAME, boardMateList.get(getAdapterPosition()).getmName());
                            intent.putExtra(MainActivity.BOARDMATE_ADDRESS, boardMateList.get(getAdapterPosition()).getmAddress());
                            intent.putExtra(MainActivity.BOARDMATE_NUMBER, boardMateList.get(getAdapterPosition()).getmNumber());
                            intent.putExtra(MainActivity.BOARDMATE_STATUS, boardMateList.get(getAdapterPosition()).getmStatus());
                            intent.putExtra(MainActivity.BOARDMATE_AMOUNT, boardMateList.get(getAdapterPosition()).getmPayable());
                            context.startActivity(intent);
                            break;
                        case R.id.menu_delete:
                            bHelper = new BoardMatesDB(context);
                            bHelper.open();
                            Intent intent1 = new Intent(context, MainActivity.class);
                            bHelper.deleteBoardMate(boardMateList.get(getAdapterPosition()).getmId());
                            context.startActivity(intent1);
                            bHelper.close();
                    }
                    return false;
                }
            });
        }
    }
}
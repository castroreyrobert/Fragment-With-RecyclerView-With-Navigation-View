package com.example.fragmentwithrecycler.mFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.BoardMatesDB;
import com.example.fragmentwithrecycler.activities.MainActivity;
import com.example.fragmentwithrecycler.adapters.RecyclerAdapterBoardMate;
import com.example.fragmentwithrecycler.models.BoardMate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardMateViewFragment extends Fragment {

    private TextView tvName, tvAddress, tvNumber, tvStatus, tvAmount;
    RecyclerAdapterBoardMate recyclerAdapterBoardMate;
    private List<BoardMate> boardMateList;
    private BoardMatesDB db;



    public BoardMateViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_board_mate_view,container, false);
        tvName = (TextView)itemView.findViewById(R.id.tvName);
        tvAddress = (TextView)itemView.findViewById(R.id.tvAddress);
        tvNumber = (TextView)itemView.findViewById(R.id.tvNumber);
        tvStatus = (TextView)itemView.findViewById(R.id.tvStatus1);
        tvAmount = (TextView)itemView.findViewById(R.id.tvAmount);

        // Getting the bundles that we passed from the BoardMateDetailActivity.class
        Bundle bundle = getArguments();
        long id = bundle.getLong(MainActivity.BOARDMATE_ID);
        String name = bundle.getString(MainActivity.BOARDMATE_NAME);
        String address = bundle.getString(MainActivity.BOARDMATE_ADDRESS);
        String number = bundle.getString(MainActivity.BOARDMATE_NUMBER);
        String status = bundle.getString(MainActivity.BOARDMATE_STATUS);
        double payable = bundle.getDouble(MainActivity.BOARDMATE_AMOUNT);


        tvName.setText(name);
        tvAddress.setText(address);
        tvNumber.setText(number);
        tvStatus.setText(status);
        tvAmount.setText(String.valueOf(payable));
        return itemView;
    }
}

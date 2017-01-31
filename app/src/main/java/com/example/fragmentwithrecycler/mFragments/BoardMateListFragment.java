package com.example.fragmentwithrecycler.mFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.BoardMatesDB;
import com.example.fragmentwithrecycler.adapters.RecyclerAdapterBoardMate;
import com.example.fragmentwithrecycler.models.BoardMate;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardMateListFragment extends Fragment {

    private RecyclerView rv;
    List<BoardMate> boardMateList;
    BoardMatesDB bHelper;
    private RecyclerView.Adapter mAdapter;


    public BoardMateListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_boardmate_list,null);

        bHelper = new BoardMatesDB(getActivity());
        bHelper.open();

        boardMateList = new ArrayList<BoardMate>();
        boardMateList = bHelper.getAllBoardMates();
        bHelper.close();
        //REFERENCE
        rv= (RecyclerView) rootView.findViewById(R.id.boardMate_RV);
        rv.setHasFixedSize(true);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        //ADAPTER
        mAdapter = new RecyclerAdapterBoardMate(getActivity(), boardMateList);
        rv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        return rootView;
    }

}

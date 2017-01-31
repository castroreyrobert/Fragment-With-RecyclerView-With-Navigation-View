package com.example.fragmentwithrecycler.mFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.BoardMatesDB;
import com.example.fragmentwithrecycler.activities.BoardMateDetailActivity;
import com.example.fragmentwithrecycler.activities.MainActivity;


public class BoardMateAddFragment extends Fragment {

    private BoardMatesDB bHelper;
    private EditText etName, etAddress, etNumber, etPayable;
    private Spinner spinnerStatus;
    private Button buttonSave;
    private boolean newBoardMate;

    public BoardMateAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View itemView = inflater.inflate(R.layout.fragment_board_mate_add,container, false);


        etName = (EditText)itemView.findViewById(R.id.etBName);
        etAddress = (EditText)itemView.findViewById(R.id.etAddress);
        etNumber = (EditText)itemView.findViewById(R.id.etNumber);
        etPayable = (EditText)itemView.findViewById(R.id.etPayable);
        spinnerStatus = (Spinner)itemView.findViewById(R.id.spinnerStatus);
        buttonSave = (Button)itemView.findViewById(R.id.buttonSave);
        bHelper = new BoardMatesDB(getActivity().getApplicationContext());
        bHelper.open();
        String[]status = {"FULLYPAID", "INITIALLYPAID", "UNPAID"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, status);
        spinnerStatus.setAdapter(adapter);

        Bundle bundle = this.getArguments();
        newBoardMate = bundle.getBoolean(BoardMateDetailActivity.ADD_BOARDMATE);
        final long bId = bundle.getLong(MainActivity.BOARDMATE_ID);
        String name = bundle.getString(MainActivity.BOARDMATE_NAME);
        String address = bundle.getString(MainActivity.BOARDMATE_ADDRESS);
        String number = bundle.getString(MainActivity.BOARDMATE_NUMBER);
        double amount = bundle.getDouble(MainActivity.BOARDMATE_AMOUNT);
        String stats = bundle.getString(MainActivity.BOARDMATE_STATUS);
        if (!newBoardMate){
            etName.setText(name);
            etAddress.setText(address);
            etNumber.setText(number);
            etPayable.setText(String.valueOf(amount));
        }
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (newBoardMate){
                    bHelper.addBoardmate(etName.getText().toString(), etAddress.getText().toString(),
                            etNumber.getText().toString(), Double.parseDouble(etPayable.getText().toString()),
                            spinnerStatus.getSelectedItem().toString());

                    }else{
                        bHelper.updateBoardMate(bId, etName.getText().toString(), etAddress.getText().toString(),
                                etNumber.getText().toString(), Double.parseDouble(etPayable.getText().toString()),
                                spinnerStatus.getSelectedItem().toString());
                    }
                    bHelper.close();
                    clear();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        return itemView;
    }

    private void clear(){
        etName.setText("");
        etAddress.setText("");
        etNumber.setText("");
        etPayable.setText("");
    }
}

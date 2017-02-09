package com.example.fragmentwithrecycler.mFragments;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fragmentwithrecycler.R;
import com.example.fragmentwithrecycler.SqliteTables.BoardMatesDB;
import com.example.fragmentwithrecycler.activities.BoardMateDetailActivity;
import com.example.fragmentwithrecycler.activities.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class BoardMateAddFragment extends Fragment {

    private BoardMatesDB bHelper;
    private EditText etName, etAddress, etNumber, etDateStarted, etPayable;
    private Spinner spinnerStatus;
    private Button buttonSave;
    private boolean newBoardMate;
    private DateFormat dateFormat;
    private DatePickerDialog datePickerDialog;

    public BoardMateAddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_board_mate_add,container, false);


        etName = (EditText)itemView.findViewById(R.id.etBName);
        etAddress = (EditText)itemView.findViewById(R.id.etAddress);
        etNumber = (EditText)itemView.findViewById(R.id.etNumber);
        etDateStarted = (EditText)itemView.findViewById(R.id.etDateStarted);
        etPayable = (EditText)itemView.findViewById(R.id.etPayable);
        spinnerStatus = (Spinner)itemView.findViewById(R.id.spinnerStatus);
        buttonSave = (Button)itemView.findViewById(R.id.buttonSave);
        bHelper = new BoardMatesDB(getActivity().getApplicationContext());
        bHelper.open();
        String[]status = {"FULLYPAID", "INITIALLYPAID", "UNPAID"};
        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, status);
        spinnerStatus.setAdapter(adapter);

        dateFormat = DateFormat.getDateInstance();

        setDateStarted();

        final Bundle bundle = this.getArguments();
        newBoardMate = bundle.getBoolean(BoardMateDetailActivity.ADD_BOARDMATE);
        final long bId = bundle.getLong(MainActivity.BOARDMATE_ID);
        String name = bundle.getString(MainActivity.BOARDMATE_NAME);
        String address = bundle.getString(MainActivity.BOARDMATE_ADDRESS);
        String number = bundle.getString(MainActivity.BOARDMATE_NUMBER);
        double amount = bundle.getDouble(MainActivity.BOARDMATE_AMOUNT);
        String stats = bundle.getString(MainActivity.BOARDMATE_STATUS);
        final String date = bundle.getString(MainActivity.BOARDMATE_DATE);
        if (!newBoardMate){
            etName.setText(name);
            etAddress.setText(address);
            etNumber.setText(number);
            etPayable.setText(String.valueOf(amount));
        }
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etName.getText().equals("") || etAddress.getText().equals("") ||
                            etNumber.getText().equals("") || etPayable.getText().equals("") ){
                        Toast.makeText(getActivity(), "Please make sure to fill all the fields", Toast.LENGTH_SHORT).show();
                    }else {
                    if (newBoardMate){
                    bHelper.addBoardmate(etName.getText().toString(), etAddress.getText().toString(),
                            etNumber.getText().toString(), MainActivity.formatNumber(Double.parseDouble(etPayable.getText().toString())),
                            spinnerStatus.getSelectedItem().toString(), MainActivity.calculateDate());

                    }else{
                        bHelper.updateBoardMate(bId, etName.getText().toString(), etAddress.getText().toString(),
                                etNumber.getText().toString(), MainActivity.formatNumber(Double.parseDouble(etPayable.getText().toString())),
                                spinnerStatus.getSelectedItem().toString(),
                                etDateStarted.getText().toString());
                    }
                    bHelper.close();
                    clear();

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.putExtra(MainActivity.NAVIGATION_TO_LAUNCH, MainActivity.NAVIGATION_TO_LOAD.BOARDMATE);
                    startActivity(intent);
                    }
                }
            });

        etDateStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });


        return itemView;
    }

    private void clear(){
        etName.setText("");
        etAddress.setText("");
        etNumber.setText("");
        etPayable.setText("");
        etDateStarted.setText("");
    }

    private void setDateStarted(){;
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, day);
                etDateStarted.setText(dateFormat.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }
}

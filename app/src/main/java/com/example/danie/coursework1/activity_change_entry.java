package com.example.danie.coursework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class activity_change_entry extends AppCompatActivity
{
    //Declaring views
    private ExpenseTemplate expenseTemplate;
    private EditText etDescriptionDEL, etCostDEL, etDateReceiptDEL, etDateAppDEL, etTotalCostDEL;
    private CheckBox chkVatDEL, chkExpensePaidDEL;
    //Declaring Database (Helper)
    private DatabaseHelper databaseHelper;

    //Runs as activity is created/loaded from another activity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_entry);

        Intent intent = getIntent();
        expenseTemplate = (ExpenseTemplate)intent.getSerializableExtra("expense");
        databaseHelper = new DatabaseHelper(this);

        etDescriptionDEL = findViewById(R.id.etDescriptionToDEL);
        etCostDEL = findViewById(R.id.etInitCostToDEL);
        etDateReceiptDEL = findViewById(R.id.etDateReceiptToDEL);
        etDateAppDEL = findViewById(R.id.etTodayDateToDEL);
        etTotalCostDEL = findViewById(R.id.etFinalCostToDEL);
        chkVatDEL = findViewById(R.id.chkAddVATToDEL);
        chkExpensePaidDEL = findViewById(R.id.chkExpensePaid);

        etDescriptionDEL.setText(expenseTemplate.getDesc());
        etCostDEL.setText(expenseTemplate.getCost());
        etDateReceiptDEL.setText(expenseTemplate.getReceiptDate());
        etDateAppDEL.setText(expenseTemplate.getDateAddedtoApp());
        etTotalCostDEL.setText(expenseTemplate.getTotalCost());

        if (expenseTemplate.getVAT() == 1) { chkVatDEL.setChecked(true); }
        else { chkVatDEL.setChecked(false); }

        if(expenseTemplate.getExpensePaid() == 1) { chkExpensePaidDEL.setChecked(true); }
        else { chkExpensePaidDEL.setChecked(false); }
    }

    public void btnUpdateOnClick (View v)
    {
        int paidTrueFalse = 0;
        int vatTrueFalse = 0;
        String datePaid = "";

        if (chkExpensePaidDEL.isChecked()) { paidTrueFalse = 1; }
        if (chkVatDEL.isChecked()) { vatTrueFalse = 1; }

        databaseHelper.updateExpense(expenseTemplate.getID(), etDescriptionDEL.getText().toString(),
                etCostDEL.getText().toString(), etDateReceiptDEL.getText().toString(),
                etDateAppDEL.getText().toString(), vatTrueFalse, paidTrueFalse,
                etTotalCostDEL.getText().toString(), DatePaid(datePaid),
                expenseTemplate.getImage() );

        Toast.makeText(activity_change_entry.this, "Expense Updated Successfully",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity_change_entry.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void btnDeleteOnClick (View v)
    {
        databaseHelper.deleteExpense(expenseTemplate.getID());
        Toast.makeText(activity_change_entry.this, "Expense Deleted Successfully",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(activity_change_entry.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public String DatePaid(String string)
    {
        if (chkExpensePaidDEL.isChecked())
        {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleformat = new SimpleDateFormat("dd-MM-yyyy");
            string = simpleformat.format(calendar.getTime());
        }
        else { string = "Not Paid"; }

        return string;
    }
}



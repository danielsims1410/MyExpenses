package com.example.danie.coursework1;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddExpense extends AppCompatActivity
{

    private Button btnAdd;
    private EditText etDescription;
    private EditText etInitCost;
    private EditText etDateReceipt;
    private EditText etDateAddedApp;
    private EditText etFinalCost;
    private CheckBox chkVAT;
    private DatabaseHelper databaseHelper;
    private ImageHelper imageHelper;
    byte[] imageByte = null;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        databaseHelper = new DatabaseHelper(this);
        etDescription = findViewById(R.id.etDescription);
        etInitCost = findViewById(R.id.etInitCost);
        etDateReceipt = findViewById(R.id.etDateReceipt);
        etFinalCost = findViewById(R.id.etFinalCost);
        etDateAddedApp = findViewById(R.id.etTodayDate);
        chkVAT = findViewById(R.id.chkAddVAT);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        getTodayDate();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==1 && resultCode == Activity.RESULT_OK)
        {
            Uri selectedImage = data.getData();
            try
            {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageByte = imageHelper.getBytes(bitmap);
                Toast.makeText(AddExpense.this, "Image Uploaded", Toast.LENGTH_LONG).show();
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
        }
    }

    public void addCurrency(EditText et)
    {
        et.setText("£" + et.getText().toString());
    }

    public void showDatePickerDialog(View v)
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void getTodayDate()
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleformat = new SimpleDateFormat("dd-MM-yyyy");
        EditText todayDate = findViewById(R.id.etTodayDate);
        todayDate.setText(simpleformat.format(calendar.getTime()));
    }

    public void chkVatOnClick(View v)
    {
        EditText initCost = findViewById(R.id.etInitCost);
        EditText finalCost = findViewById(R.id.etFinalCost);
        CheckBox addVAT = findViewById(R.id.chkAddVAT);
        if(addVAT.isChecked())
        {
            if (initCost.getText().toString() != "" && initCost.getText().length() > 0)
            {
                calculateVAT(initCost, finalCost);
            }
        }

        else
            {
            finalCost.setText(initCost.getText());
            addCurrency(finalCost);
            return;
        }
    }

    public void calculateVAT(EditText etInit, EditText etCost)
    {
            double initValue = Double.parseDouble(etInit.getText().toString());
            double initValueTwentyPercent = initValue * 0.2;
            double finalValue = initValue + initValueTwentyPercent;
            String finalValueString = Double.toString(finalValue);
            etCost.setText(finalValueString);
            addCurrency(etCost);
    }

    public void btnAddExpenseClick(View v)
    {
        int vatYesNo;
        if (chkVAT.isChecked()) { vatYesNo = 1; }
        else
        {
            vatYesNo = 0;
            etFinalCost.setText(etInitCost.getText());
        }

        if (checkAllFieldsEntered())
        {
            databaseHelper.addExpense(etDescription.getText().toString(),
                    etInitCost.getText().toString(),
                    etDateReceipt.getText().toString(),
                    etDateAddedApp.getText().toString(),
                    vatYesNo, 0,
                    etFinalCost.getText().toString(), "Not Paid", imageByte);
            etDescription.setText("");
            etInitCost.setText("");
            etDateReceipt.setText("");
            chkVAT.setChecked(false);
            etFinalCost.setText("");
            imageByte = null;
            Toast.makeText(AddExpense.this, "Expense Added Successfully",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddExpense.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        else if(!checkAllFieldsEntered())
        {
            Toast.makeText(AddExpense.this, "Please Enter all Fields",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void btnInsertImageClick(View v)
    {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);
    }

    public boolean checkAllFieldsEntered()
    {
        if(etInitCost.getText().toString().equals("") ||
                etDateReceipt.getText().toString().equals("") ||
                etFinalCost.getText().toString().equals("") ||
                etDescription.getText().toString().equals("")) { return false; }
        else { return true; }
    }
}




package com.example.danie.coursework1;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private ListView listView;
    private ArrayList<ExpenseTemplate> expenseTemplateArrayList;
    private AllExpensesAdapter allExpensesAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckBox chkShowPaid = findViewById(R.id.chkShowPaid);
        boolean checked = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("chkAllExpense", true);
        chkShowPaid.setChecked(checked);

        listView = findViewById(R.id.lvExpenses);
        databaseHelper = new DatabaseHelper(this);
        expenseTemplateArrayList = databaseHelper.getExpenses(chkShowPaid.isChecked());
        allExpensesAdapter = new AllExpensesAdapter(this, expenseTemplateArrayList);
        listView.setAdapter(allExpensesAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                Intent intent = new Intent(MainActivity.this,
                        activity_change_entry.class);
                intent.putExtra("expense", expenseTemplateArrayList.get(position));
                startActivity(intent);
            }
        });
    }

    public void openAddExpenseActivity(View view)
    {
        Intent intent = new Intent(this, AddExpense.class);
        startActivity(intent);
    }

    public void checkPaidExpensesOnly(View v)
    {
        CheckBox chkAllExpense = findViewById(R.id.chkShowPaid);

        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putBoolean("chkAllExpense", chkAllExpense.isChecked()).apply();
        Intent intent = getIntent();

        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

}

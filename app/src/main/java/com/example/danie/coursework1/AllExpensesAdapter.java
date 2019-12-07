package com.example.danie.coursework1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AllExpensesAdapter extends BaseAdapter
{

    private Context context;
    private ArrayList<ExpenseTemplate> expenseTemplateArrayList;
    private ImageHelper ih;

    public AllExpensesAdapter(Context context, ArrayList<ExpenseTemplate> expenseTemplateArrayList)
    {
        this.context = context;
        this.expenseTemplateArrayList = expenseTemplateArrayList;
    }

    @Override
    public int getCount() {
        return expenseTemplateArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return expenseTemplateArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder
    {
        protected TextView txtDescriptionVH;
        protected TextView txtCostVH;
        protected TextView txtTotalCostVH;
        protected CheckBox chkvatVH;
        protected TextView txtReceiptDateVH;
        protected TextView txtDateAppVH;
        protected TextView txtDatePaidVH;
        protected CheckBox chkPaidVH;
        protected ImageView ivReceiptVH;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ViewHolder viewHolder;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expense_item, null, true);

            viewHolder = new ViewHolder();
            viewHolder.txtDescriptionVH = convertView.findViewById(R.id.txtDescription);
            viewHolder.txtCostVH = convertView.findViewById(R.id.txtCost);
            viewHolder.txtTotalCostVH = convertView.findViewById(R.id.txtTotalCost);
            viewHolder.chkvatVH = convertView.findViewById(R.id.chkVAT);
            viewHolder.txtReceiptDateVH = convertView.findViewById(R.id.txtDateReceipt);
            viewHolder.txtDateAppVH = convertView.findViewById(R.id.txtDateAddedApp);
            viewHolder.txtDatePaidVH = convertView.findViewById(R.id.txtDatePaid);
            viewHolder.chkPaidVH = convertView.findViewById(R.id.chkPaid);
            viewHolder.ivReceiptVH = convertView.findViewById(R.id.ivReceipt);
            convertView.setTag(viewHolder);
        }
        else { viewHolder = (ViewHolder) convertView.getTag(); }


        viewHolder.txtDescriptionVH.setText(expenseTemplateArrayList.get(position).getDesc());
        viewHolder.txtCostVH.setText("Cost: £" + expenseTemplateArrayList.get(position).getCost());
        if(expenseTemplateArrayList.get(position).getVAT() == 1)
        {
            viewHolder.chkvatVH.setChecked(true);
            viewHolder.txtTotalCostVH.setVisibility(View.VISIBLE);
            viewHolder.txtTotalCostVH.setText("Cost with VAT: " + expenseTemplateArrayList.get(position).getTotalCost());
        }
        else if(expenseTemplateArrayList.get(position).getVAT() == 0)
        {
            viewHolder.chkvatVH.setChecked(false);
            viewHolder.txtTotalCostVH.setVisibility(View.GONE);
        }

        if(expenseTemplateArrayList.get(position).getExpensePaid() == 1) { viewHolder.chkPaidVH.setChecked(true); }
        else if(expenseTemplateArrayList.get(position).getExpensePaid() == 0) { viewHolder.chkPaidVH.setChecked(false); }
        viewHolder.txtReceiptDateVH.setText("Date on Receipt: " + expenseTemplateArrayList.get(position).getReceiptDate());
        viewHolder.txtDateAppVH.setText("Date Added to App: " + expenseTemplateArrayList.get(position).getDateAddedtoApp());

        if(expenseTemplateArrayList.get(position).getExpensePaid() == 1)
        {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleformat = new SimpleDateFormat("dd-MM-yyyy");
            viewHolder.txtDatePaidVH.setText("Date Paid: " + simpleformat.format(calendar.getTime()));
        }
        else if(expenseTemplateArrayList.get(position).getExpensePaid() == 0)
        {
            viewHolder.txtDatePaidVH.setText("Date Paid: Unpaid");
        }

        if(expenseTemplateArrayList.get(position).getImage() != null)
        {
            viewHolder.ivReceiptVH.setImageBitmap(ih.getImage(expenseTemplateArrayList
                    .get(position).getImage()));
        }
        else if(expenseTemplateArrayList.get(position).getImage() == null)
        {
            viewHolder.ivReceiptVH.setImageBitmap(null);
        }
        return convertView;
    }

}

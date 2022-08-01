package com.project.coronabank.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.coronabank.Model.Transaction;
import com.project.coronabank.R;

import java.util.ArrayList;

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Context context;
    private int resource;

    public TransactionAdapter(Context context, int resource, ArrayList<Transaction> transactions) {
        super(context, resource, transactions);

        this.context = context;
        this.resource = resource;
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        Transaction transaction = getItem(position);

        ImageView imgTransactionIcon = convertView.findViewById(R.id.img_transaction);
        TextView txtTransactionTitle = convertView.findViewById(R.id.txt_transaction_type_id);
        TextView txtTransactionTimestamp = convertView.findViewById(R.id.txt_transaction_timestamp);
        TextView txtTransactionInfo = convertView.findViewById(R.id.txt_transaction_info);
        txtTransactionInfo.setVisibility(View.VISIBLE);
        TextView txtTransactionAmount = convertView.findViewById(R.id.txt_transaction_amount);

        txtTransactionTitle.setText(transaction.getTransactionType().toString() + " - " + transaction.getTransactionID());
        txtTransactionTimestamp.setText(transaction.getTimestamp());
        txtTransactionAmount.setText("Amount: $" + String.format("%.2f", transaction.getAmount()));
    //below sets the font color for the transactions list of deposits and transfers
        if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.PAYMENT) {
            imgTransactionIcon.setImageResource(R.mipmap.icon_deposit_background);
            txtTransactionInfo.setText("To Payee: " + transaction.getPayee());
            txtTransactionAmount.setTextColor(Color.BLACK);
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.TRANSFER) {
            imgTransactionIcon.setImageResource(R.mipmap.icon_transfer_background);
            txtTransactionInfo.setText("From: " + transaction.getSendingAccount() + " - " + "To: " + transaction.getDestinationAccount());
            txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.black));
        } else if (transaction.getTransactionType() == Transaction.TRANSACTION_TYPE.DEPOSIT) {
            txtTransactionInfo.setVisibility(View.GONE);
            txtTransactionAmount.setTextColor(getContext().getResources().getColor(android.R.color.black));
        }

        return convertView;
    }
}

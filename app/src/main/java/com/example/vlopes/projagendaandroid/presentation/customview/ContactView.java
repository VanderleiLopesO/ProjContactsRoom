package com.example.vlopes.projagendaandroid.presentation.customview;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vlopes.projagendaandroid.R;

public class ContactView extends LinearLayout {

    private int id;
    private TextView tvName, tvNumber;
    private Button btnDeleteItem, btnUpdateItem;
    private ContactViewListener listener;

    public ContactView(Context context, ContactViewListener listener) {
        super(context);
        this.listener = listener;

        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.contact_view, this);
        setUpVariables();
        setUpListeners();
    }

    private void setUpListeners() {
        btnDeleteItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDeleteItem(id);
            }
        });

        btnUpdateItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onUpdateItem(id);
            }
        });
    }

    private void setUpVariables() {
        tvName = findViewById(R.id.view_name);
        tvNumber = findViewById(R.id.view_number);
        btnDeleteItem = findViewById(R.id.view_delete);
        btnUpdateItem = findViewById(R.id.view_update);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTvName(String tvName) {
        this.tvName.setText(tvName);
    }

    public void setTvNumber(String tvNumber) {
        this.tvNumber.setText(tvNumber);
    }
}

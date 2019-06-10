package com.example.vlopes.projagendaandroid.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vlopes.projagendaandroid.R;
import com.example.vlopes.projagendaandroid.entity.Contact;
import com.example.vlopes.projagendaandroid.presentation.customview.ContactView;
import com.example.vlopes.projagendaandroid.presentation.customview.ContactViewListener;
import com.example.vlopes.projagendaandroid.repository.RoomDatabase;

import java.util.List;

import static com.example.vlopes.projagendaandroid.Constants.INTENT_EDIT;
import static com.example.vlopes.projagendaandroid.Constants.RESULT_EDIT;

public class MainActivity extends AppCompatActivity implements ContactViewListener {

    private FloatingActionButton optionButton;
    private GridLayout contactList;
    private ImageView emptyImage;
    private TextView emptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpVariables();
        setUpListeners();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadData();
    }

    private void setUpVariables() {
        optionButton = findViewById(R.id.optionButton);
        contactList = findViewById(R.id.contactsList);
        emptyImage = findViewById(R.id.empty_image);
        emptyText = findViewById(R.id.empty_text);
    }

    private void setUpListeners() {
        optionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddContactActivity.class));
                overridePendingTransition(R.anim.bounce, R.anim.bounce);
            }
        });
    }

    private void loadData() {
        List<Contact> databaseList = RoomDatabase.getDatabase(this).getDAO().getContacts();
        contactList.removeAllViews();
        emptyText.setVisibility(View.GONE);
        emptyImage.setVisibility(View.GONE);

        for (Contact c : databaseList) {
            ContactView item = new ContactView(this, this);
            item.setId(c.getId());
            item.setTvName(c.getName());
            item.setTvNumber(c.getNumber());
            contactList.addView(item);
        }

        if (contactList.getChildCount() == 0) {
            emptyText.setVisibility(View.VISIBLE);
            emptyImage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDeleteItem(int id) {
        for (Contact c : RoomDatabase.getDatabase(this).getDAO().getContacts()) {
            if (c.getId() == id) {
                RoomDatabase.getDatabase(this).getDAO().deleteContact(c);
            }
        }

        loadData();
    }

    @Override
    public void onUpdateItem(int id) {
        Intent i = new Intent(MainActivity.this, UpdateContactActivity.class);
        i.putExtra(INTENT_EDIT, id);
        startActivityForResult(i, RESULT_EDIT);
        overridePendingTransition(R.anim.bounce, R.anim.bounce);
    }
}

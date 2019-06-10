package com.example.vlopes.projagendaandroid.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vlopes.projagendaandroid.R;
import com.example.vlopes.projagendaandroid.entity.Contact;
import com.example.vlopes.projagendaandroid.repository.RoomDatabase;

import java.lang.ref.WeakReference;

public class AddContactActivity extends AppCompatActivity {

    private EditText etName, etNumber;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        setUpVariables();
        setUpListener();
    }

    private void setUpVariables() {
        etName = findViewById(R.id.inputName);
        etNumber = findViewById(R.id.inputNumber);
        btnAdd = findViewById(R.id.addButton);
    }

    private void setUpListener() {
        MaskWatcher watcher = new MaskWatcher(new WeakReference<EditText>(etNumber));
        etNumber.addTextChangedListener(watcher);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(etName.getText().toString(),
                        etNumber.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left, R.anim.right);
    }

    private void addContact(String name, String number) {
        if (name.isEmpty() || number.isEmpty() || number.length() < 10) {
            Toast.makeText(this, "Algum campo está incorreto, não foi possível adicionar contato!", Toast.LENGTH_SHORT).show();
            return;
        }

        Contact contact = new Contact();
        contact.setName(name);
        contact.setNumber(number);

        RoomDatabase.getDatabase(this).getDAO().insertContact(contact);
        Toast.makeText(this, "Adicionado com sucesso!", Toast.LENGTH_LONG).show();
        finish();
    }

}

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

import static com.example.vlopes.projagendaandroid.Constants.INTENT_EDIT;

public class UpdateContactActivity extends AppCompatActivity {

    private EditText etName, etNumber;
    private Button btnAdd;
    private int idToBeEdited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);
        setUpVariables();
        setUpListener();

        idToBeEdited = getIntent().getIntExtra(INTENT_EDIT, 0);
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
                editContact(etName.getText().toString(),
                        etNumber.getText().toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left, R.anim.right);
    }

    private void editContact(String name, String number) {
        if (name.isEmpty() || number.isEmpty() || number.length() < 10) {
            Toast.makeText(this, "Algum campo está incorreto, não foi possível adicionar contato!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Contact c : RoomDatabase.getDatabase(this).getDAO().getContacts()) {
            if (c.getId() == idToBeEdited) {
                c.setName(name);
                c.setNumber(number);
                RoomDatabase.getDatabase(this).getDAO().updateContact(c);
                Toast.makeText(this, "Editado com sucesso!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}

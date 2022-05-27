package com.felipes.first;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaPrincipal extends AppCompatActivity {
    private TextView userEmail, userName;
    private Button btnUnlog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        getSupportActionBar().hide();
        initComponents();
        ActionButton();
    }
    public void initComponents(){
        userName = findViewById(R.id.txtUserName);
        userEmail = findViewById(R.id.txtUserEmail);
        btnUnlog = findViewById(R.id.btn_deslogar);
    }
    @Override
    public void onStart(){
        super.onStart();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference document = db.collection("Usuarios").document(usuarioId);
        document.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    userName.setText(value.getString("nome"));
                    userEmail.setText(email);
                }
            }
        });
    }

    public void ActionButton(){
        btnUnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(TelaPrincipal.this,FormLogin.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
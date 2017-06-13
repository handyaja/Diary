package net.yolo.omjipanggg.diary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText u, p;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        u = (EditText) findViewById(R.id.txtUser);
        p = (EditText) findViewById(R.id.txtPass);
        login = (Button) findViewById(R.id.btnLogin);

        login.setOnClickListener(this);
    }

    private void auth() {
        String user = u.getText().toString();
        String pass = p.getText().toString();
        if (user.equals("handy") && pass.equals("123")) {
            Toast.makeText(MainActivity.this, "Welcome, Handy!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, NoteDisplay.class));
        } else {
            Toast.makeText(MainActivity.this, "Invalid USERNAME or PASSWORD", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogin) {
            auth();
        }
    }
}

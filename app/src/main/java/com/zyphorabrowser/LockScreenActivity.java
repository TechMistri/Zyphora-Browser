package com.zyphorabrowser;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LockScreenActivity extends AppCompatActivity {

    private EditText pinEditText;
    private Button unlockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        pinEditText = findViewById(R.id.pin_edit_text);
        unlockButton = findViewById(R.id.unlock_button);

        unlockButton.setOnClickListener(v -> {
            String enteredPin = pinEditText.getText().toString();
            AppLockManager lockManager = AppLockManager.getInstance();

            if (lockManager.checkPin(this, enteredPin)) {
                // PIN is correct, unlock the app and finish this activity
                lockManager.unlockApp();
                Toast.makeText(this, "App Unlocked!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // PIN is incorrect, show an error message
                Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show();
                pinEditText.setText(""); // Clear the PIN field
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Prevent the user from backing out of the lock screen
        // You could also exit the app here by calling finishAffinity()
    }
}

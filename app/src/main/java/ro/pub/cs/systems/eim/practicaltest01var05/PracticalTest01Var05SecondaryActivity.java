package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var05SecondaryActivity extends AppCompatActivity {

    private EditText stringEditText;
    private Button verifyButton;
    private Button cancelButton;

    private ButtonClickListener buttonClickListener;

    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id == verifyButton.getId()) {
                setResult(Activity.RESULT_OK, new Intent());
                finish();
            } else if (id == cancelButton.getId()) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_secondary);
        buttonClickListener = new ButtonClickListener();

        stringEditText = findViewById(R.id.string_edit_text);
        verifyButton = findViewById(R.id.verify_button);
        cancelButton = findViewById(R.id.cancel_button);

        verifyButton.setOnClickListener(buttonClickListener);
        cancelButton.setOnClickListener(buttonClickListener);

        Intent mainIntent = getIntent();
        if (mainIntent != null) {
            String text = mainIntent.getStringExtra("INTENT_EXTRA");
            if (text.length() > 0) {
                stringEditText.setText(text);
            }
        }
    }
}
package ro.pub.cs.systems.eim.practicaltest01var05;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import ro.pub.cs.systems.eim.practicaltest01var05.general.Constants;
import ro.pub.cs.systems.eim.practicaltest01var05.service.PracticalTest01Var05Service;

public class PracticalTest01Var05MainActivity extends AppCompatActivity {

    private Button navigateButton;
    private Button topLeftButton;
    private Button topRightButton;
    private Button centerButton;
    private Button bottomLeftButton;
    private Button bottomRightButton;
    private GridLayout gridLayout;

    private EditText editText;

    private ButtonClickListener buttonClickListener;

    private int numPresses;

    final private static String SAVE_PRESSES = "PRESS";
    final private static String INTENT_EXTRA = "INTENT_EXTRA";
    final private static int REQUEST_CODE = 2022;

    private boolean isServiceStarted = false;

    private MessageBroadcastReceiver messageBroadcastReceiver;
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    IntentFilter intentFilter = new IntentFilter();


    private class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (id != navigateButton.getId()) {
                String buttonText = ((Button)view).getText().toString();
                String newString = editText.getText().toString();
                newString += buttonText;
                editText.setText(newString);
                numPresses++;
            } else if (id == navigateButton.getId()) {
                Intent intent = new Intent("ro.pub.cs.systems.eim.practicaltest01var05.intent.action.PracticalTest01Var05SecondaryActivity");
                intent.putExtra(INTENT_EXTRA, editText.getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
            }

            if (!isServiceStarted) {
                if (numPresses >= Constants.PRESSES_THRESHOLD) {
                    isServiceStarted = true;
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var05Service.class);
                    intent.putExtra(Constants.INTENT_STRING, editText.getText().toString());
                    intent.putExtra(Constants.INTENT_INT, numPresses);
                    startService(intent);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var05_main);
        buttonClickListener = new ButtonClickListener();
        numPresses = 0;

        navigateButton = (Button) findViewById(R.id.navigate_button);
        navigateButton.setOnClickListener(buttonClickListener);

        topLeftButton = findViewById(R.id.top_left_button);
        topRightButton = findViewById(R.id.top_right_button);
        centerButton = findViewById(R.id.center_button);
        bottomLeftButton = findViewById(R.id.bottom_left_button);
        bottomRightButton = findViewById(R.id.bottom_right_button);

        editText = findViewById(R.id.edit_text);
        gridLayout = findViewById(R.id.grid);

        setAllButtonListener(gridLayout);
        messageBroadcastReceiver = new MessageBroadcastReceiver();
        intentFilter.addAction(Constants.SERVICE_ACTION);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
         savedInstanceState.putInt(SAVE_PRESSES, numPresses);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(SAVE_PRESSES)) {
            numPresses = savedInstanceState.getInt(SAVE_PRESSES);
        } else {
            numPresses = 0;
        }
        Toast.makeText(this, "Number of presses " + numPresses, Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        String result = null;
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    result = getResources().getString(R.string.result_ok);
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    result = getResources().getString(R.string.result_cancel);
                }
                break;
            default:
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }



    public void setAllButtonListener(ViewGroup viewGroup) {
        int noChildren = viewGroup.getChildCount();
        for (int i = 0; i < noChildren; ++i) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof Button) {
                view.setOnClickListener(buttonClickListener);
            }
        }
    }
}
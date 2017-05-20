package tojuhaka.hippiechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import tojuhaka.hippiechatutils.Message;
import tojuhaka.hippiechatutils.tojuhaka.hippiechatutils.db.DatabaseAccess;


public class EditActivity extends AppCompatActivity {
    private EditText etText;
    private EditText etTarget;
    private Button btnSave;
    private Button btnCancel;
    private Message msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        this.etText = (EditText) findViewById(R.id.etText);
        this.etTarget = (EditText) findViewById(R.id.etTarget);
        this.btnSave = (Button) findViewById(R.id.btnSave);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            msg = (Message) bundle.get("MESSAGE");
            if(msg != null) {
                this.etText.setText(msg.getText());
            }
        }

        this.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClicked();
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClicked();
            }
        });
    }

    public void onSaveClicked() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        if(msg == null) {
            // Add new memo
            String text = etText.getText().toString();
            String target = etTarget.getText().toString();
            Message temp = new Message(target, text);
            databaseAccess.save(temp);
        } else {
            // Update the memo
            msg.setText(etText.getText().toString());
            databaseAccess.update(msg);
        }
        databaseAccess.close();
        this.finish();
    }

    public void onCancelClicked() {
        this.finish();
    }
}
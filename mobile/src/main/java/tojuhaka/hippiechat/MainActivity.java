package tojuhaka.hippiechat;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import tojuhaka.hippiechatutils.Message;
import tojuhaka.hippiechatutils.tojuhaka.hippiechatutils.db.DatabaseAccess;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnAdd;
    private DatabaseAccess databaseAccess;
    private List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.databaseAccess = DatabaseAccess.getInstance(this);

        this.listView = (ListView) findViewById(R.id.listView);
        this.btnAdd = (Button) findViewById(R.id.btnAdd);

        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddClicked();
            }
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = messages.get(position);
                TextView txtText = (TextView) view.findViewById(R.id.txtText);
                if (message.isFullDisplayed()) {
                    txtText.setText(message.getShortText());
                    message.setFullDisplayed(false);
                } else {
                    txtText.setText(message.getText());
                    message.setFullDisplayed(true);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        databaseAccess.open();
        this.messages = databaseAccess.getAllMessages();
        databaseAccess.close();
        MessageAdapter adapter = new MessageAdapter(this, messages);
        this.listView.setAdapter(adapter);
    }

    public void onAddClicked() {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
    }

    public void onDeleteClicked(Message message) {
        databaseAccess.open();
        databaseAccess.delete(message);
        databaseAccess.close();

        ArrayAdapter<Message> adapter = (ArrayAdapter<Message>) listView.getAdapter();
        adapter.remove(message);
        adapter.notifyDataSetChanged();
    }

    public void onEditClicked(Message message) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("MESSAGE", message);
        startActivity(intent);
    }

    private class MessageAdapter extends ArrayAdapter<Message> {
        public MessageAdapter(Context context, List<Message> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_list_item, parent, false);
            }

            ImageView btnEdit = (ImageView) convertView.findViewById(R.id.btnEdit);
            ImageView btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);
            TextView txtTarget = (TextView) convertView.findViewById(R.id.txtTarget);
            TextView txtText = (TextView) convertView.findViewById(R.id.txtText);

            final Message message = messages.get(position);
            message.setFullDisplayed(false);
            txtTarget.setText(message.getTarget());
            txtText.setText(message.getShortText());
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked(message);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClicked(message);
                }
            });
            return convertView;
        }
    }
}
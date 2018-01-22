package in.fisicodietclinic.fisico;

import android.support.annotation.FloatRange;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;



import java.util.ArrayList;

import in.fisicodietclinic.fisico.adapters.ChatAdapter;
import in.fisicodietclinic.fisico.models.ChatModel;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ChatModel chatModel;
    ArrayList<ChatModel> chatList;
    ChatAdapter chatAdapter;
    EditText message;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById(R.id.re);
        getSupportActionBar().setTitle("Ask a query");
        //sendR("http://arogyam.rjchoreography.com/wp-json/wp/v2/posts?orderby=date&per_page=4");
        chatList=new ArrayList<>();
        chatList.add(new ChatModel("Hello,Hello","Customer"));
        chatList.add(new ChatModel("How are you","Customer"));
        chatList.add(new ChatModel("I'm Doing good","Doctor"));
        chatList.add(new ChatModel("How are you?","Doctor"));
        chatAdapter = new ChatAdapter(chatList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChatActivity.this);
       // mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
        message = (EditText) findViewById(R.id.editTextMessage);
        fab = (FloatingActionButton) findViewById(R.id.sendMessage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatList.add(new ChatModel(message.getText().toString(),"Customer"));
                chatAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(chatList.size()-1);
                message.setText("");
            }
        });

    }
}

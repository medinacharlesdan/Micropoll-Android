package charlesjamnajim.micropol;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

public class ThePollActivity extends AppCompatActivity{
    private ListView theListV;
    private Firebase pollDb,votersDb;

    public String pollname,child1,useruid,yes;
    private Map<String, String> voters;
    private ArrayList<String> mQuestion = new ArrayList<>();
    private int count;
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_poll);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pollDb = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent/pollnames");
        votersDb = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent/" + pollname + "/forVoters");
        theListV = (ListView)findViewById(R.id.thelistview);



        FloatingActionButton myFab = (FloatingActionButton)findViewById(R.id.addpollbtn);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThePollActivity.this,FormActivity.class));
                finish();
            }
        });

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mQuestion);
        theListV.setAdapter(arrayAdapter);

        pollDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mQuestion.add(dataSnapshot.getValue(String.class));
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




        theListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 pollname = String.valueOf(adapterView.getItemAtPosition(i));
                 votersDb = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent/" + pollname + "/forVoters");
                 useruid = FirebaseAuth.getInstance().getCurrentUser().getUid();


                 count = 1;

                 votersDb.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {

                         for (DataSnapshot voters : dataSnapshot.getChildren()) {
                             //long count = voters.getChildrenCount();
                             String lol = voters.getKey();

                             if (lol.equals(useruid)) {
                                count = count + 1;

                                 break;

                             }
                             else {
                                 yes = "yes";


                             }
                         }

                         if (count > 1){

                             Toast.makeText(ThePollActivity.this,"You cannot vote twice",Toast.LENGTH_SHORT).show();
                         }else{
                             Intent myIntent = new Intent(ThePollActivity.this,voteActivity.class);
                             myIntent.putExtra("pollname", pollname);
                             startActivity(myIntent);
                             finish();

                         }



                     }

                     @Override
                     public void onCancelled(FirebaseError firebaseError) {

                     }
                 });






             }
         });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            startActivity(new Intent(ThePollActivity.this,MainActivity.class));
            finish();
        }


        return super.onOptionsItemSelected(item);

    }


}
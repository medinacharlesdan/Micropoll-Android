package charlesjamnajim.micropol;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class voteActivity extends AppCompatActivity {
    private TextView question,txtexpired;
    private Button b1, b2, b3, b4;
    private String quest, c1, c2, c3, c4, creator, expiration, userUid, currentDateString,userEmail;
    private Firebase voting, voters;
    private Map<String, String> map;
    private int r1, r2, r3, r4;
    private Date expirationdate, currentDate;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        b1 = (Button) findViewById(R.id.choice1);
        b2 = (Button) findViewById(R.id.choice2);
        b3 = (Button) findViewById(R.id.choice3);
        b4 = (Button) findViewById(R.id.choice4);
        question = (TextView) findViewById(R.id.questionSa);
        txtexpired = (TextView) findViewById(R.id.txtExpired);
        final String pollname = getIntent().getStringExtra("pollname");



        currentDate = new Date();

        currentDateString = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);



        voting = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent/" + pollname);
        voters = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent/" + pollname + "/forVoters");


        voting.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = dataSnapshot.getValue(Map.class);
                quest = map.get("Question");
                c1 = map.get("C1");
                c2 = map.get("C2");
                c3 = map.get("C3");
                c4 = map.get("C4");
                final String c1Res = map.get("c1Result");
                final String c2Res = map.get("c2Result");
                final String c3Res = map.get("c3Result");
                final String c4Res = map.get("c4Result");
                expiration = map.get("ExpirationDate");

                creator = map.get("Creator");

                if (expiration.compareTo(currentDateString) <=0){
                   /* Toast.makeText(voteActivity.this,"This poll is expired! Expired: " + expiration,Toast.LENGTH_SHORT).show();
                    finish();*/

                    txtexpired.setVisibility(View.VISIBLE);
                    txtexpired.setText("This poll is expired! Expired: " + expiration);
                    question.setVisibility(View.INVISIBLE);
                    b2.setVisibility(View.INVISIBLE);
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                    b1.setVisibility(View.INVISIBLE);
                }

                if(c3.isEmpty() && c4.isEmpty()){
                    b3.setVisibility(View.INVISIBLE);
                    b4.setVisibility(View.INVISIBLE);
                }else if (c3.isEmpty()){
                    b3.setVisibility(View.INVISIBLE);
                    b4.setY(700);

                }else if(c4.isEmpty())
                {
                    b4.setVisibility(View.INVISIBLE);



                }

                if (creator.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {


                            Intent myIntent = new Intent(voteActivity.this, ResultActivity.class);
                            myIntent.putExtra("pollname", pollname);
                            startActivity(myIntent);
                            finish();



                }
                else {
                    question.setText(quest);
                    userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                /*Button 1 */
                    b1.setText(c1);
                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            r1 = 1 + Integer.parseInt(c1Res);
                            String rr1 = String.valueOf(r1);
                            voting.child("c1Result").setValue(rr1);
                            Toast.makeText(voteActivity.this,"You voted for: " + c1, Toast.LENGTH_SHORT).show();


                            voters.child(userUid).setValue(userUid);


                            Intent myIntent = new Intent(voteActivity.this, ThePollActivity.class);
                            myIntent.putExtra("pollname", pollname);
                            startActivity(myIntent);
                            finish();
                        }
                    });

                /*Button 2 */
                    b2.setText(c2);
                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            r2 = 1 + Integer.parseInt(c2Res);
                            String rr2 = String.valueOf(r2);
                            voting.child("c2Result").setValue(rr2);
                            Toast.makeText(voteActivity.this,"You voted for: " + c2 , Toast.LENGTH_SHORT).show();


                            voters.child(userUid).setValue(userUid);

                            Intent myIntent = new Intent(voteActivity.this, ThePollActivity.class);
                            myIntent.putExtra("pollname", pollname);
                            startActivity(myIntent);
                            finish();
                        }
                    });
                /*Button 3 */
                    b3.setText(c3);
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            r3 = 1 + Integer.parseInt(c3Res);
                            String rr3 = String.valueOf(r3);
                            voting.child("c3Result").setValue(rr3);
                            Toast.makeText(voteActivity.this,"You voted for: " + c3 , Toast.LENGTH_SHORT).show();


                            voters.child(userUid).setValue(userUid);

                            Intent myIntent = new Intent(voteActivity.this, ThePollActivity.class);
                            myIntent.putExtra("pollname", pollname);
                            startActivity(myIntent);
                            finish();
                        }
                    });

                    b4.setText(c4);
                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            r4 = 1 + Integer.parseInt(c4Res);
                            String rr4 = String.valueOf(r4);
                            voting.child("c4Result").setValue(rr4);
                            Toast.makeText(voteActivity.this, "You voted for: " + c4 , Toast.LENGTH_SHORT).show();


                            voters.child(userUid).setValue(userUid);

                            Intent myIntent = new Intent(voteActivity.this, ThePollActivity.class);
                            myIntent.putExtra("pollname", pollname);
                            startActivity(myIntent);
                            finish();
                        }
                    });

                }


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }



        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            startActivity(new Intent(voteActivity.this,ThePollActivity.class));
            finish();
        }


        return super.onOptionsItemSelected(item);

    }
}

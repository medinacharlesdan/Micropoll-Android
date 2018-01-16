package charlesjamnajim.micropol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    private TextView txtRquestion,txtresult1,txtresult2,txtresult3,txtresult4, txtchoice1, txtchoice2, txtchoice3, txtchoice4,expirationtxt;
    private Firebase results;
    private Map<String, String> resultmap;
    private String c1Res,c2Res,c3Res,c4Res,resquest,c1,c2,c3,c4,pollname,currentDateString,expirationDatestring;
    private Date currentDate;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtRquestion = (TextView) findViewById(R.id.txtResultQuestion);
        txtresult1 = (TextView) findViewById(R.id.txtResult1);
        txtresult2 = (TextView) findViewById(R.id.txtResult2);
        txtresult3 = (TextView) findViewById(R.id.txtResult3);
        txtresult4 = (TextView) findViewById(R.id.txtResult4);
        txtchoice1 = (TextView) findViewById(R.id.txtChoice1);
        txtchoice2 = (TextView) findViewById(R.id.txtChoice2);
        txtchoice3 = (TextView) findViewById(R.id.txtChoice3);
        txtchoice4 = (TextView) findViewById(R.id.txtChoice4);
        expirationtxt = (TextView) findViewById(R.id.txtExpirationDate);

        currentDate = new Date();

        currentDateString = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this,ThePollActivity.class));
                finish();
            }
        });
        pollname = getIntent().getStringExtra("pollname");

        results = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent/"+ pollname );

        results.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                resultmap = dataSnapshot.getValue(Map.class);
                c1Res = resultmap.get("c1Result");
                c2Res = resultmap.get("c2Result");
                c3Res = resultmap.get("c3Result");
                c4Res = resultmap.get("c4Result");
                c1 = resultmap.get("C1");
                c2 = resultmap.get("C2");
                c3 = resultmap.get("C3");
                c4 = resultmap.get("C4");
                resquest = resultmap.get("Question");
                expirationDatestring = resultmap.get("ExpirationDate");

                if (expirationDatestring.compareTo(currentDateString) <=0){

                   expirationtxt.append(expirationDatestring + '\n' + "Poll is Expired");


                }
                else{
                    expirationtxt.append(expirationDatestring + '\n' + "Poll is Ongoing");
                }


                txtRquestion.setText(resquest);
                txtchoice1.setText(c1);
                txtchoice2.setText(c2);
                txtchoice3.setText(c3);
                txtchoice4.setText(c4);
                txtresult1.setText(c1Res);
                txtresult2.setText(c2Res);
                txtresult3.setText(c3Res);
                txtresult4.setText(c4Res);

                if (c4.isEmpty() && c3.isEmpty()){
                    txtresult3.setVisibility(View.GONE);
                    txtchoice3.setVisibility(View.GONE);
                    txtresult4.setVisibility(View.GONE);
                    txtchoice4.setVisibility(View.GONE);
                }

                if (c4.isEmpty()){

                    txtresult4.setVisibility(View.GONE);
                    txtchoice4.setVisibility(View.GONE);
                }
                else if (c3.isEmpty()){

                    txtresult3.setVisibility(View.GONE);
                    txtchoice3.setVisibility(View.GONE);

                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }



        });
    }
}

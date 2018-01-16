package charlesjamnajim.micropol;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by cha on 5/15/2017.
 */

public class FormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
   // private DatabaseReference mDatabase;
    private EditText txtPollQuestion,txtC1,txtC2,txtC3,txtC4;
    private Firebase RootRef = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent");
   private Firebase voters;
    private Button add, btnED;
    private String currentDateTimeString;
    private Date myDate;
    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpoll);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtPollQuestion = (EditText) findViewById(R.id.txtQuestion);
        txtC1 = (EditText) findViewById(R.id.txtC1);
        txtC2 = (EditText) findViewById(R.id.txtC2);
        txtC3 = (EditText) findViewById(R.id.txtC3);
        txtC4 = (EditText) findViewById(R.id.txtC4);

        btnED = (Button) findViewById(R.id.btnED);
        add = (Button) findViewById(R.id.button2);

        myDate = new Date();

        currentDateTimeString = new SimpleDateFormat("yyyy-MM-dd").format(myDate);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pollname = txtPollQuestion.getText().toString();
                String c1 = txtC1.getText().toString();
                String c2 = txtC2.getText().toString();
                String c3 = txtC3.getText().toString();
                String c4 = txtC4.getText().toString();
                String ed = btnED.getText().toString();
                voters = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent/"+pollname + "/forVoters");

                if(TextUtils.isEmpty(pollname) || TextUtils.isEmpty(c1) || TextUtils.isEmpty(c2)){

                    Toast.makeText(FormActivity.this,"Must Enter Question first", Toast.LENGTH_LONG).show();
                }else
                {
                    Firebase pollcreate = RootRef.child(pollname);
                    Firebase deadpoll = RootRef.child("pollnames");

                    voters = new Firebase("https://micropoll-ca30a.firebaseio.com/Poll_parent/"+pollname);
                    Firebase lol = voters.child("forVoters");

                    lol.child("default").setValue("default");

                    deadpoll.child(pollname).setValue(pollname);
                    pollcreate.child("Question").setValue(pollname);
                    pollcreate.child("C1").setValue(c1);
                    pollcreate.child("C2").setValue(c2);
                    pollcreate.child("C3").setValue(c3);
                    pollcreate.child("C4").setValue(c4);
                    pollcreate.child("c1Result").setValue("0");
                    pollcreate.child("c2Result").setValue("0");
                    pollcreate.child("c3Result").setValue("0");
                    pollcreate.child("c4Result").setValue("0");
                    pollcreate.child("StartDate").setValue(currentDateTimeString);
                    pollcreate.child("ExpirationDate").setValue(ed);
                    pollcreate.child("Creator").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());

                    startActivity(new Intent(FormActivity.this,ThePollActivity.class));
                    finish();
                }
            }
        });



    }

    public void datePicker(View view){
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getFragmentManager(),"date");
    }

    public void setDate(final Calendar calendar){
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
        ((TextView) findViewById(R.id.btnED)).setText(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }
    public static class DatePickerFragment extends DialogFragment{
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar c =  Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_WEEK);

            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)getActivity(), year, month, day);
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id== android.R.id.home){
            startActivity(new Intent(FormActivity.this,ThePollActivity.class));
            finish();
        }


        return super.onOptionsItemSelected(item);

    }


}

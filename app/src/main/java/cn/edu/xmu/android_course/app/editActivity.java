package cn.edu.xmu.android_course.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editActivity extends AppCompatActivity {

    String dateNote;
    String subjectNote;
    String contentNote;
    EditText subject;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dateNote = whatDateisToday();
        TextView textView = findViewById(R.id.date);
        textView.setText(dateNote);
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        //隐藏键盘
//        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//    }

    private String whatDateisToday(){
        return SimpleDateFormat.getDateTimeInstance().format(new Date());
    }

    public void saveNote(View view) {
        subject = (EditText) findViewById(R.id.edit_subject);
        content = (EditText) findViewById(R.id.edit_text);
        subjectNote = subject.getText().toString();
        contentNote = content.getText().toString();
        contentNote.replaceAll("\\\\n|\\\\r|\\\\r\\\\n|\\\\n\\\\r","A");
        //Log.w("subject",subjectNote);
        Log.w("content",contentNote);

        Bundle saveBundle = new Bundle();
        //Log.w("subject",subjectNote);
        saveBundle.putString("date",dateNote);
        saveBundle.putString("subject",subjectNote);
        saveBundle.putString("content",contentNote);

        Intent saveIntent = new Intent(editActivity.this,MainActivity.class);
        saveIntent.putExtras(saveBundle);
        startActivity(saveIntent);
        finish();
    }

//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        EditText editText = (EditText) findViewById(R.id.edit_text);
//        Bundle bundle = new Bundle();
//        bundle.putString("edit",editText.toString());
//        Intent intent = new Intent(editActivity.this,MainActivity.class);
//        intent.putExtras(bundle);
//    }
}

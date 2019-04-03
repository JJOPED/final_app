package cn.edu.xmu.android_course.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class changeActivity extends AppCompatActivity {

    String dateNote;
    String subjectNote;
    String contentNote;
    String editSubject = "";
    String editContent = "";
    EditText subject;
    EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        dateNote = whatDateisToday();
        TextView textView = findViewById(R.id.date);
        textView.setText(dateNote);

        subject = (EditText) findViewById(R.id.change_subject);
        content = (EditText) findViewById(R.id.change_text);

        Bundle changeBundle = this.getIntent().getExtras();
        editContent = changeBundle.getString("editContent");
        editSubject = changeBundle.getString("editSubject");
        //Log.w("change",editSubject+","+editContent);
        //Log.w("change",content.toString());
        content.setText(editContent);
        subject.setText(editSubject);
    }

    private String whatDateisToday(){
        return SimpleDateFormat.getDateTimeInstance().format(new Date());
    }

    public void saveNote(View view) {
        subjectNote = subject.getText().toString();
        contentNote = content.getText().toString();
        contentNote.replaceAll("\\\\n"," ");
//        Log.w("change",subjectNote);
//        Log.w("change",contentNote);

        Bundle saveBundle = new Bundle();
        //Log.w("subject",subjectNote);
        saveBundle.putString("date",dateNote);
        saveBundle.putString("subject",subjectNote);
        saveBundle.putString("content",contentNote);

        Intent saveIntent = new Intent(changeActivity.this,MainActivity.class);
        saveIntent.putExtras(saveBundle);
        startActivity(saveIntent);
//        finish();
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

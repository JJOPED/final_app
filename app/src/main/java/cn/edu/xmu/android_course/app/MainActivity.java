package cn.edu.xmu.android_course.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String saveDate = null;
    String saveSubject = null;
    String saveContent = null;
    public List<Map<String,String>> list = new ArrayList<Map<String, String>>();
    String fileName = "Note_file";
    String[] from;
    int[] to;
    SimpleAdapter listAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        Log.w("onCreate","create");

        Bundle getBundle = this.getIntent().getExtras();
        from  = new String[]{"date", "subject"};
        to = new int[]{R.id.item_date, R.id.item_subject};
        getData();
        if(list != null){
            listView = (ListView) findViewById(R.id.list_item);
            listAdapter = new SimpleAdapter(
                    this,
                    list,
                    R.layout.item_main,
                    from,
                    to
            );
            listView.setAdapter(listAdapter);
        }
        if(getBundle != null) {
            saveDate = getBundle.getString("date");
            saveSubject = getBundle.getString("subject");
            saveContent = getBundle.getString("content");
            list.clear();
            saveData();
            getData();
//            if(list != null) {
            listView = (ListView) findViewById(R.id.list_item);
            listAdapter = new SimpleAdapter(
                    this,
                    list,
                    R.layout.item_main,
                    from,
                    to
            );
            listView.setAdapter(listAdapter);
        }
        //list事件点击
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String noteString = list.get(i).toString();
                //Log.w("noteString",noteString);
//                        String notString = list.get(i).toString();
//                        Log.w("noteString",notString);
                //Log.w("noteString",noteString);
                //Toast.makeText(MainActivity.this,noteString,Toast.LENGTH_SHORT).show();
                String editContent = "";
                String editSubject = "";
                String[] editString = noteString.split(",");
                editContent = editString[0].split("=")[1];
                editSubject = editString[2].split("=")[1];
                editSubject = editSubject.substring(0,editSubject.length()-1);
                //Log.w("data",editDate+" "+editSubject+" "+editContent);
                //Log.w("content",editContent);
                Intent editIntent = new Intent(MainActivity.this,changeActivity.class);
                Bundle editBundle = new Bundle();
//                        editBundle.putString("editDate",editDate);
                editBundle.putString("editSubject",editSubject);
                editBundle.putString("editContent",editContent);
                editIntent.putExtras(editBundle);
                startActivity(editIntent);
//                finish();
            }
        });
//            }
//        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this,editActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    ;

    //保存最新编辑的note
    public void saveData(){
        FileOutputStream fos = null;
        if(saveDate != null&& saveSubject != null && saveContent != null) {
            String string = saveDate.concat(",").concat(saveSubject).concat(",").concat(saveContent);
            //Log.w("string",string);
            try {
                fos = openFileOutput(fileName, Context.MODE_APPEND);
                try {
                        fos.write(string.getBytes());
                        fos.write("\r\n".getBytes());
                        //Log.w("string", "right");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        saveDate = null;
        saveSubject = null;
        saveContent = null;
    }

    //获取文件的内容并显示
    public void getData(){
        FileInputStream fis = null;
        BufferedReader reader = null;
        try {
            fis = openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while((line = reader.readLine())!=null){
                Map<String,String> data = new HashMap<>();
                String[] str = line.split(",");
                if(str[0] != null && str[1] != null && str[2] != null) {
                    //Log.w("data",str[0]+str[1]+str[2]);
                    data.put("date", str[0]);
                    data.put("subject", str[1]);
                    data.put("content", str[2]);
                    list.add(data);
                }
                else{
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDataByItem(String item){
        FileInputStream fis = null;
        BufferedReader reader = null;
        try {
            fis = openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = "";
            while((line = reader.readLine())!=null){
                Map<String,String> data = new HashMap<>();
                String[] str = line.split(",");
                if(str[0] != null && str[1] != null && str[2] != null) {
                    //Log.w("data",str[0]+str[1]+str[2]);
                    if(str[1].indexOf(item) >= 0) {
                        data.put("date", str[0]);
                        data.put("subject", str[1]);
                        data.put("content", str[2]);
                        list.add(data);
                    }
                }
                else{
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteData(){
        list.clear();
        deleteFile(fileName);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Log.w("set","set");
            list.clear();
            getData();
            listAdapter.notifyDataSetChanged();
            return true;
        }
        else if (id == R.id.action_clear) {
            //Log.w("set","set");
            deleteData();
            listAdapter.notifyDataSetChanged();
            return true;
        }
        else if (id == R.id.action_share) {
            //Log.w("set","set");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String shareNote = list.get(i).toString();
                    String editContent = "";
                    String editSubject = "";
                    String note = "";
                    String[] editString = shareNote.split(",");
                    editContent = editString[0].split("=")[1];
                    editSubject = editString[2].split("=")[1];
                    editSubject = editSubject.substring(0,editSubject.length()-1);
                    note = editSubject.concat(":").concat(editContent);
                    Intent sendNote = new Intent();
                    sendNote.setAction(Intent.ACTION_SEND);
                    sendNote.putExtra(Intent.EXTRA_TEXT,note);
                    sendNote.setType("text/plain");
                    if(sendNote.resolveActivity(getPackageManager())!=null)
                        startActivity(sendNote);
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.one) {
            // Handle the camera action
            list.clear();
            listAdapter.notifyDataSetChanged();
            getDataByItem("日常");
        } else if (id == R.id.two) {
            list.clear();
            listAdapter.notifyDataSetChanged();
            getDataByItem("学习");

        } else if (id == R.id.three) {
            list.clear();
            listAdapter.notifyDataSetChanged();
            getDataByItem("购物");

        } else if (id == R.id.four){
            list.clear();
            listAdapter.notifyDataSetChanged();
            getData();
        }
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

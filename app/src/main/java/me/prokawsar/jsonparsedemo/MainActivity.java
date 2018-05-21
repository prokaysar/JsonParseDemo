package me.prokawsar.jsonparsedemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TaskPocess().execute("http://www.mocky.io/v2/5b0112833000006c0020a728","60000");
        listView = findViewById(R.id.student_list_id);
    }
    class TaskPocess extends AsyncTask<String,String,String>{
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String urlLink = strings[0];
            int timeout = Integer.parseInt(strings[1]);
            HttpURLConnection connection;
            try {
                URL url = new URL(urlLink);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-length","0");
                connection.setAllowUserInteraction(false);
                connection.setUseCaches(false);
                connection.setReadTimeout(timeout);
                connection.setConnectTimeout(timeout);
                connection.connect();
                int status = connection.getResponseCode();
                switch (status){
                    case 200:
                    case 201:
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null){
                            builder.append(line);
                        }
                        reader.close();
                        return builder.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray studentInfo = jsonObject.getJSONArray("studentInfo");
                List<Student> studentList = new ArrayList<>();
                for (int i = 0; i<studentInfo.length(); i++){
                    JSONObject students = studentInfo.getJSONObject(i);

                    String name = students.getString("name");
                    String dep = students.getString("dep");
                    String cgpa = students.getString("cgpa");
                    String desc = students.getString("desc");

                    Student student = new Student();

                    student.setName(name);
                    student.setDep(dep);
                    student.setCgpa(cgpa);
                    student.setDesc(desc);
                    studentList.add(student);
                }
                ArrayAdapter<Student> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,studentList);
                listView.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
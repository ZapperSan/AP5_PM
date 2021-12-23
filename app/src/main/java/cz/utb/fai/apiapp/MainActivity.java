package cz.utb.fai.apiapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.FileInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainRecyclerView.ItemClickListener {

    Context context;
    final static String appFileName = "api.json";
    final static String appDirName = "/appDir/";
    final static String appDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + appDirName;

    static JSONArray results;

    String oneLine = "";
    MainRecyclerView recycler;
    RecyclerView recyclerView;

    @Override
    public void onItemClick(View view, int position) {
        Log.d("CLICK", "Clickety click \n");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_ApiApp);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topToolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler);
        context = this;

        readFromExternalMemory();

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recycler = new MainRecyclerView(context, results);
        recyclerView.setAdapter(recycler);
        // set up the RecyclerView
    }

    private void tryBundle() {
        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();

            try {
                JSONArray filteredJSON = new JSONArray();
                for (int i = 0; i < results.length(); i++) {
                    boolean isHit = true;
                    if (b.getString("name") != "") {
                        if (results.getJSONObject(i).getString("slug").contains(b.getString("name")))
                            isHit = true;
                        else isHit = false;
                    }
                    if (b.getString("level") != "") {
                        if (results.getJSONObject(i).getString("level").contains(b.getString("level"))
                            && isHit==true)
                            isHit = true;
                        else isHit = false;
                    }
                    if (b.getString("school") != "") {
                        if (results.getJSONObject(i).getString("school").contains(b.getString("school"))
                                && isHit==true)
                            isHit = true;
                        else isHit = false;
                    }
                    if (b.getString("range") != "") {
                        if (results.getJSONObject(i).getString("range").contains(b.getString("range"))
                                && isHit==true)
                            isHit = true;
                        else isHit = false;
                    }
                    if (b.getString("dnd_class") != "") {
                        if (results.getJSONObject(i).getString("dnd_class").contains(b.getString("dnd_class"))
                                && isHit==true)
                            isHit = true;
                        else isHit = false;
                    }
                    if (b.getBoolean("ritual") == true) {
                        if (results.getJSONObject(i).getString("ritual").contains("yes")
                                && isHit==true)
                            isHit = true;
                        else isHit = false;
                    }
                    if (b.getBoolean("concentration") == true) {
                        if (results.getJSONObject(i).getString("concentration").contains("yes")
                                && isHit==true)
                            isHit = true;
                        else isHit = false;
                    }
                    if (isHit) {
                        filteredJSON.put(results.getJSONObject(i));
                    }
                }

                while(results.length()>0)
                {
                    results.remove(0);
                }
                for(int i = 0; i < filteredJSON.length(); i++)
                {
                    results.put(filteredJSON.getJSONObject(i));
                }
                Log.d("DONE", "Bundle done! \n");
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("MEGA-ERROR", "Chyba bundle \n" + e.toString());
            }
        } else Log.d("BUNDLE", "No bundle found! \n");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:

                Intent intent = new Intent(this, FilterActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_sync:
                prepareExternalMemory();
                writeToExternalMemory("[");
                getApiContent("https://api.open5e.com/spells/");
                return true;

            case R.id.action_clear:
                finish();
                Intent in = new Intent(this, MainActivity.class);
                startActivity(in);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private boolean readFromExternalMemory() {
        try {
            File appFile = new File(appDirPath + appFileName);
            if (appFile.exists()) {
                FileInputStream fis = new FileInputStream(new File(appDirPath + appFileName));
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader bufferedReader = new BufferedReader(isr);

                String readResult = "";

                while ((oneLine = bufferedReader.readLine()) != null) {
                    readResult += oneLine + System.getProperty("line.separator");
                }

                bufferedReader.close();
                isr.close();
                fis.close();

                fillJSON(readResult);

                //temporary.setText(results.getString(3));
            }
        } catch (Exception e) {
            //temporary.setText("Chyba cteni");
            Log.d("MEGA-ERROR", "Chyba cteni \n" + e.toString());
        }
        return true;
    }

    private void fillJSON(String input) {
        try {
            JSONArray json = new JSONArray(input);
            results = json.getJSONObject(0).getJSONArray("results");
            for (int i = 1; i < json.length(); i++) {
                JSONObject page = json.getJSONObject(i);
                JSONArray pageResults = page.getJSONArray("results");

                for (int j = 0; j < pageResults.length(); j++) {
                    results.put(pageResults.getJSONObject(j));
                }
            }
            tryBundle();
        } catch (Exception e) {
            Log.d("MEGA-ERROR", "Error, filling JSON! \n" + e.toString());
        }
    }

    private void prepareExternalMemory() {
        try {
            File appFile = new File(appDirPath + appFileName);
            boolean isDone = appFile.delete();
            if (isDone) Log.d("PrepareExternalMemory()", "File successfuly deleted! \n");

        } catch (Exception e) {
        }
    }

    private boolean writeToExternalMemory(String dataToWrite) {
        try {
            File appDir = new File(appDirPath);
            appDir.mkdir();
            FileOutputStream fos = new FileOutputStream(appDirPath + appFileName, true);
            fos.write(dataToWrite.getBytes());
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void getApiContent(String url) {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("rrr", response);
                            JSONObject page = new JSONObject(response);
                            String next = page.getString("next");
                            if (next != "null") {
                                writeToExternalMemory(response + ",");
                                getApiContent(next);
                            } else {

                                writeToExternalMemory(response + "]");
                                finish();
                                startActivity(getIntent());

                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Mission successful!", Toast.LENGTH_LONG);
                                toast.show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse.statusCode == 429) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "You used all available free translations for today. Try again later...", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Something went wrong!", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
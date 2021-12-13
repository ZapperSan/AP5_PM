package cz.utb.fai.apiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    TextView level;
    TextView range;
    TextView castTime;
    TextView duration;
    TextView components;
    TextView handbook;
    TextView material;
    TextView concentration;
    TextView ritual;
    TextView school;
    TextView profession;
    TextView high;
    TextView spell;
    TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        description = (TextView) findViewById(R.id.description);
        level = (TextView) findViewById(R.id.level);
        range = (TextView) findViewById(R.id.range);
        castTime = (TextView) findViewById(R.id.castTime);
        duration = (TextView) findViewById(R.id.duration);
        components = (TextView) findViewById(R.id.components);
        handbook = (TextView) findViewById(R.id.handbook);
        material = (TextView) findViewById(R.id.material);
        concentration = (TextView) findViewById(R.id.concentration);
        ritual = (TextView) findViewById(R.id.ritual);
        school = (TextView) findViewById(R.id.school);
        profession = (TextView) findViewById(R.id.profession);
        high = (TextView) findViewById(R.id.high);
        spell = (TextView) findViewById(R.id.spell);
        castTime = (TextView) findViewById(R.id.castTime);

        getDetail();
    }

    public void backToMain(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void getDetail()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.open5e.com/spells/acid-splash/";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // ZPRACOVANI JSONu:
                        try
                        {
                            Log.d("rrr",response);
                            //1. Z DAT, KTERA JSME OBDRZELI VYTVORIME JSONObject
                            JSONObject responseData = new JSONObject(response);

                            // 2. Z PROMENNE jsonObject ZISKAME "responseData" (viz struktura JSONu odpovedi)
                            // JSONObject responseData = jsonObject.getJSONObject("responseData");

                            // 3. Z PROMENNE responseData ZISKAME "translatedText" (viz struktura JSONu odpovedi)
                            // V PROMENNE translatedText JE ULOZEN VYSLEDEK PREKLADU
                            //String translatedText = responseData.getString("translatedText");
                            String titleText = responseData.getString("name");
                            String descriptionText = responseData.getString("desc");
                            String highText = responseData.getString("higher_level");
                            String handbookText = responseData.getString("page");
                            String rangeText = responseData.getString("range");
                            String componentsText = responseData.getString("components");
                            String materialText = responseData.getString("material");
                            String ritualText = responseData.getString("ritual");
                            String durationText = responseData.getString("duration");
                            String concentrationText = responseData.getString("concentration");
                            String levelText = responseData.getString("level");
                            String professionText = responseData.getString("dnd_class");
                            String schoolText = responseData.getString("school");
                            String castTimeText = responseData.getString("casting_time");


                            // 4. V textView ZOBRAZIME VYSLEDEK PREKLADU
                            spell.setText(titleText);
                            description.setText(descriptionText);
                            high.setText(highText);
                            handbook.setText(handbookText);
                            range.setText("Range: " + rangeText);
                            components.setText("Components: " + componentsText);
                            castTime.setText("Casting time: " + castTimeText);
                            material.setText(materialText);
                            if(ritualText == "yes") ritual.setText("(Ritual)");
                            else ritual.setText("");
                            duration.setText("Duration: " + durationText);
                            if(concentrationText == "yes") concentration.setText("(Concentration)");
                            else concentration.setText("");
                            level.setText("Level: " + levelText);
                            profession.setText(professionText);
                            school.setText(schoolText);

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if(error.networkResponse.statusCode == 429)
                        {
                            Toast toast= Toast.makeText(getApplicationContext(),
                                    "You used all available free translations for today. Try again later...",Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else
                        {
                            Toast toast= Toast.makeText(getApplicationContext(),
                                    "Something went wrong!",Toast.LENGTH_LONG);
                            toast.show();
                        }

                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


}
package cz.utb.fai.apiapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    JSONObject json;

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
    TextView description;

    MaterialToolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getData();

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
        castTime = (TextView) findViewById(R.id.castTime);
        toolbar = findViewById(R.id.topToolbar);

        getDetail();

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();


        ab.setDisplayHomeAsUpEnabled(true);
    }

    public void getData() {
        if (getIntent().hasExtra("json")) {
            String intent = getIntent().getStringExtra("json");
            try {
                json = new JSONObject(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDetail() {
        try {
            String titleText = json.getString("name");
            String descriptionText = json.getString("desc");
            String highText = json.getString("higher_level");
            String handbookText = json.getString("page");
            String rangeText = json.getString("range");
            String componentsText = json.getString("components");
            String materialText = json.getString("material");
            String ritualText = json.getString("ritual");
            String durationText = json.getString("duration");
            String concentrationText = json.getString("concentration");
            String levelText = json.getString("level");
            String professionText = json.getString("dnd_class");
            String schoolText = json.getString("school");
            String castTimeText = json.getString("casting_time");
            String spellText = json.getString("name");


            // 4. V textView ZOBRAZIME VYSLEDEK PREKLADU
            toolbar.setTitle(spellText);
            description.setText(descriptionText);
            high.setText(highText);
            handbook.setText(handbookText);
            range.setText("Range: " + rangeText);
            components.setText("Components: " + componentsText);
            castTime.setText("Casting time: " + castTimeText);
            if(!materialText.isEmpty())material.setText("("+materialText+")");
            else material.setText("");
            if (ritualText == "yes") ritual.setText("(Ritual)");
            else ritual.setText("");
            duration.setText("Duration: " + durationText);
            if (concentrationText == "yes") concentration.setText("(Concentration)");
            else concentration.setText("");
            level.setText(levelText);
            profession.setText(professionText);
            school.setText(schoolText);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
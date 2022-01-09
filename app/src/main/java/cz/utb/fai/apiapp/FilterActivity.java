package cz.utb.fai.apiapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FilterActivity extends AppCompatActivity {

    TextInputEditText nameInput;
    AutoCompleteTextView levelInput;
    AutoCompleteTextView schoolInput;
    AutoCompleteTextView rangeInput;
    AutoCompleteTextView classInput;
    SwitchMaterial concentrationInput;
    SwitchMaterial ritualInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.topToolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();

        nameInput = (TextInputEditText)findViewById(R.id.nameInput);
        levelInput = (AutoCompleteTextView)findViewById(R.id.levelInput);
        schoolInput = (AutoCompleteTextView)findViewById(R.id.schoolInput);
        rangeInput = (AutoCompleteTextView)findViewById(R.id.rangeInput);
        classInput = (AutoCompleteTextView)findViewById(R.id.classInput);
        concentrationInput = (SwitchMaterial)findViewById(R.id.concentrationSwitch);
        ritualInput = (SwitchMaterial)findViewById(R.id.ritualSwitch);

        // Level adapter
        String[] levels = {"","Cantrip", "1st","2nd","3rd","4th","5th","6th","7th","8th","9th"};
        ArrayAdapter<String>levelAdapter = new ArrayAdapter<String>(FilterActivity.this,
                android.R.layout.simple_spinner_item,levels);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelInput.setAdapter(levelAdapter);
        // School adapter
        String[] schools = {"","Abjuration", "Conjuration","Divination","Enchantment","Evocation","Illusion","Necromancy","Transmutation"};
        ArrayAdapter<String>schoolAdapter = new ArrayAdapter<String>(FilterActivity.this,
                android.R.layout.simple_spinner_item,schools);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolInput.setAdapter(schoolAdapter);
        // Range adapter
        String[] ranges = {"","Self", "Touch","5","10","15","20","30","60","90"};
        ArrayAdapter<String>rangeAdapter = new ArrayAdapter<String>(FilterActivity.this,
                android.R.layout.simple_spinner_item,ranges);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rangeInput.setAdapter(rangeAdapter);
        // Class adapter
        String[] classes = {"","Bard", "Cleric","Druid","Paladin","Ranger","Ritual Caster","Sorcerer","Warlock","Wizard"};
        ArrayAdapter<String>classAdapter = new ArrayAdapter<String>(FilterActivity.this,
                android.R.layout.simple_spinner_item,classes);
        schoolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classInput.setAdapter(classAdapter);

        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_app_bar_filters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                Intent intent = new Intent(this, MainActivity.class);
                    Bundle b = new Bundle();
                    if(nameInput.getText()!= null) b.putString("name", nameInput.getText().toString());
                    if(levelInput.getText()!= null) b.putString("level", levelInput.getText().toString());
                    if(schoolInput.getText()!= null) b.putString("school", schoolInput.getText().toString());
                    if(rangeInput.getText()!= null) b.putString("range", rangeInput.getText().toString());
                    if(classInput.getText()!= null) b.putString("dnd_class", classInput.getText().toString());
                    if(concentrationInput.isChecked()!= false) b.putBoolean("concentration", concentrationInput.isChecked());
                    if(ritualInput.isChecked()!= false) b.putBoolean("ritual", ritualInput.isChecked());
                    intent.putExtras(b);
                    Log.d("FULL", "Sending filled bundle \n");
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
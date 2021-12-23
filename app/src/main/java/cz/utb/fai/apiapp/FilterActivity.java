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
    TextInputEditText levelInput;
    TextInputEditText schoolInput;
    TextInputEditText rangeInput;
    TextInputEditText classInput;
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
        levelInput = (TextInputEditText)findViewById(R.id.levelInput);
        schoolInput = (TextInputEditText)findViewById(R.id.schoolInput);
        rangeInput = (TextInputEditText)findViewById(R.id.rangeInput);
        classInput = (TextInputEditText)findViewById(R.id.classInput);
        concentrationInput = (SwitchMaterial)findViewById(R.id.concentrationSwitch);
        ritualInput = (SwitchMaterial)findViewById(R.id.ritualSwitch);

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
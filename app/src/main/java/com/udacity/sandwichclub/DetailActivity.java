package com.udacity.sandwichclub;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mOrigin;
    TextView mDescription;
    TextView mIngredients;
    TextView mAlsoKnown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mAlsoKnown = findViewById(R.id.also_known_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mDescription.setText(sandwich.getDescription());
        mOrigin.setText(sandwich.getPlaceOfOrigin());

        if(!(sandwich.getAlsoKnownAs().isEmpty())){
            mAlsoKnown.append(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        }

        if(!(sandwich.getIngredients().isEmpty())){
            mIngredients.append(TextUtils.join(", ", sandwich.getIngredients()));
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.return_parent) {
            Context context = DetailActivity.this;
            Class destination = MainActivity.class;
            Intent goHomeIntent = new Intent(context, destination);
            startActivity(goHomeIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

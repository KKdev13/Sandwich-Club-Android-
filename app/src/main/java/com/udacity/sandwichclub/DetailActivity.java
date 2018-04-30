package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.nio.charset.Charset;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView imageView;
    private TextView origin_tv;
    private TextView origin_label;
    private TextView also_known_tv;
    private TextView also_known_label;
    private TextView ingredients_tv;
    private TextView ingredients_label;
    private TextView description_tv;
    private TextView description_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

         imageView = (ImageView)findViewById(R.id.image_iv);
         origin_tv = (TextView) findViewById(R.id.origin_tv);
         origin_label = (TextView) findViewById(R.id.origin_label);
         also_known_tv = (TextView) findViewById(R.id.also_known_tv);
         also_known_label = (TextView)findViewById(R.id.also_known_label);
         ingredients_tv = (TextView) findViewById(R.id.ingredients_tv);
         ingredients_label = (TextView) findViewById(R.id.ingredients_label);
         description_tv = (TextView) findViewById(R.id.description_tv);
         description_label = (TextView) findViewById(R.id.description_label);

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

        setTitle(sandwich.getMainName());
        populateUI(sandwich);

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageView);

        String originText = sandwich.getPlaceOfOrigin();
        if(originText.isEmpty()){
            origin_tv.setText(R.string.detail_error_message);
        } else {
            //display
            origin_tv.setText(originText);
        }

        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if(alsoKnownAs.size() == 0){
            also_known_tv.setVisibility(View.GONE);
            also_known_label.setVisibility(View.GONE);
        } else {
            //display
            also_known_tv.setVisibility(View.VISIBLE);
            also_known_label.setVisibility(View.VISIBLE);
            String strA = TextUtils.join(", ", alsoKnownAs);
            also_known_tv.setText(strA);

        }


        String description = sandwich.getDescription();
        if (description.isEmpty()){
            description_tv.setText(R.string.detail_error_message);
        } else {
            description_tv.setText(description);
        }



        List<String> ingredients = sandwich.getIngredients();
        if(ingredients.size() == 0) {
            ingredients_tv.setText(R.string.detail_error_message);
        } else {
            ingredients_label.setVisibility(View.VISIBLE);
            ingredients_tv.setVisibility(View.VISIBLE);
            String strB = TextUtils.join(", ", ingredients);
            ingredients_tv.setText(strB);

        }

    }
}

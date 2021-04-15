package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        mSandwich = JsonUtils.parseSandwichJson(json);
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(ingredientsIv);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        TextView mainNameTextView = findViewById(R.id.main_name_tv);
        String name = mSandwich.getMainName();
        mainNameTextView.setText(name);

        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        List<String> alsoKnownAsList = mSandwich.getAlsoKnownAs();

        if (alsoKnownAsList.size()!= 0){
            for (String alsoKnownAsName : alsoKnownAsList) {
                alsoKnownAsTextView.append(alsoKnownAsName + " ");
            }
        }else {
            alsoKnownAsTextView.setText(R.string.no_info);
        }

        TextView placeOfOriginTextView = findViewById(R.id.origin_tv);
        String placeOfOrigin = mSandwich.getPlaceOfOrigin();
        if (!TextUtils.isEmpty(placeOfOrigin)){
            placeOfOriginTextView.setText(placeOfOrigin);
        }else {
            placeOfOriginTextView.setText(R.string.no_info);
        }

        TextView descriptionTextView = findViewById(R.id.description_tv);
        descriptionTextView.setText(mSandwich.getDescription());

        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        List<String> ingredientsList = mSandwich.getIngredients();
        for (String ingredient : ingredientsList){
            ingredientsTextView.append(ingredient + " ");
        }

    }
}

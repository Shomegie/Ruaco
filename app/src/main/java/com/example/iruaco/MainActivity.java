package com.example.iruaco;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context=this;

    RecyclerView questions_container;
    Adapter adapter;
    TextView category_name;
    TextView translate_to_text_view;
    List<Questions> items;
    int category_selectIid=0;
    String DefaultLanguage = "French";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Questions.build_questions_list("French");
        category_select(0);

        questions_container = findViewById((R.id.questions_container));
        questions_container.setLayoutManager(new LinearLayoutManager(this));

        loadAnimation(questions_container);

        final EditText edittext = (EditText) findViewById(R.id.translate_edit_text);
        edittext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    //Toast.makeText(getApplicationContext(),edittext.getText().toString(),Toast.LENGTH_SHORT).show();
                    //volley();
                    Translate(edittext.getText().toString(),DefaultLanguage);
                }
                return false;
            }
        });


    }

    private void loadAnimation (RecyclerView r){
        Context context = r.getContext();

        LayoutAnimationController controller = null;
        controller = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_list_animation2);

        EditText passed_edit = findViewById(R.id.translate_edit_text);
        TextView passed_text = findViewById(R.id.translated_text_main);

        adapter = new Adapter(this,items,passed_edit,passed_text);
        r.setAdapter(adapter);

        r.setLayoutAnimation(controller);
        r.getAdapter().notifyDataSetChanged();
        r.scheduleLayoutAnimation();
    }


    public void category_select (int category){
        int start = (category * 15);
        int end = start + 15;
        items = Questions.questions_list.subList(start,end);
    }

    String Translate(String textToBeTranslated,String language){
        String language_pair = "en-fr";
        TextView textView = findViewById(R.id.translated_text_main);

        switch (language) {
            case "French":
                language_pair = "en-fr";
                break;
            case "Arabic":
                language_pair = "en-ar";
                break;
            case "German":
                language_pair = "en-de";
                break;
            case "Spanish":
                language_pair = "en-es";
                break;
            default:
                textView.setText(language + "is not supported for live translations.");
                return "";
        }

        TranslatorBackgroundTask translatorBackgroundTask= new TranslatorBackgroundTask(getApplicationContext());
        try {
            String translationResult = translatorBackgroundTask.execute(textToBeTranslated,language_pair).get(); // Returns the translated text as a String
            //Log.d("Translation Result",translationResult); // Logs the result in Android Monitor
            String output = translatorBackgroundTask.return_result;
            textView.setText(output);
            return translationResult;
        }
        catch(Exception e) {
            textView.setText("Cannot get translation at the moment");
        }
        return "";
    }

    public void category_selector (View view){
        category_name = findViewById(R.id.category);

        switch (view.getId()) {
            case R.id.aside_airport_button:
                category_selectIid=0;
                category_name.setText("Category : Airport");
                Toast.makeText(getApplicationContext(),"Category: Airport",Toast.LENGTH_SHORT).show();
                break;
            case R.id.airport_bottom:
                category_selectIid=0;
                category_name.setText("Category : Airport");
                Toast.makeText(getApplicationContext(),"Category: Airport",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aside_emergency_button:
                category_selectIid=1;
                category_name.setText("Category : Emergency");
                Toast.makeText(getApplicationContext(),"Category: Emergency",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aside_food_button:
                category_selectIid=2;
                category_name.setText("Category : Food");
                Toast.makeText(getApplicationContext(),"Category: Food",Toast.LENGTH_SHORT).show();
                break;
            case R.id.food_bottom:
                category_selectIid=2;
                category_name.setText("Category : Food");
                Toast.makeText(getApplicationContext(),"Category: Food",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aside_health_button:
                category_selectIid=3;
                category_name.setText("Category : Health");
                Toast.makeText(getApplicationContext(),"Category: Health",Toast.LENGTH_SHORT).show();
                break;
            case R.id.health_bottom:
                category_selectIid=3;
                category_name.setText("Category : Health");
                Toast.makeText(getApplicationContext(),"Category: Health",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aside_leisure_button:
                category_selectIid=4;
                category_name.setText("Category: Leisure");
                Toast.makeText(getApplicationContext(),"Category: Leisure",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aside_lodging_button:
                category_selectIid=5;
                category_name.setText("Category: Lodging");
                Toast.makeText(getApplicationContext(),"Category: Lodging",Toast.LENGTH_SHORT).show();
                break;
            case R.id.lodging_bottom:
                category_selectIid=5;
                category_name.setText("Category: Lodging");
                Toast.makeText(getApplicationContext(),"Category: Lodging",Toast.LENGTH_SHORT).show();
                break;
            case R.id.aside_transportation_button:
                category_selectIid=6;
                category_name.setText("Category: Transportation");
                Toast.makeText(getApplicationContext(),"Category: Transportation",Toast.LENGTH_SHORT).show();
                break;
        }

        category_select(category_selectIid);
        adapter.update_list(items);
        //adapter.notifyDataSetChanged();
        loadAnimation(questions_container);

        DrawerLayout aside = findViewById(R.id.drawer_layout);
        aside.closeDrawer(GravityCompat.START);
    }

    public void openDrawer(View view) {
        DrawerLayout aside = findViewById(R.id.drawer_layout);
        aside.openDrawer(GravityCompat.START);
    }

    public void openLanguageSelector(View view){
        ConstraintLayout tint = findViewById(R.id.tint);
        ConstraintLayout lang_selector = findViewById(R.id.language_selector_layout);

        tint.setVisibility(View.VISIBLE);
        lang_selector.setVisibility(View.VISIBLE);


    }

    public void closeLanguageSelector(View view){
        ConstraintLayout tint = findViewById(R.id.tint);
        ConstraintLayout lang_selector = findViewById(R.id.language_selector_layout);

        lang_selector.setVisibility(View.GONE);
        tint.setVisibility(View.GONE);

    }

    public void select_language (View view){

        String new_lang="English";
        translate_to_text_view = findViewById(R.id.translate_to_text_view);
        switch (view.getId()) {
            case R.id.lang_arabic:
                new_lang = "Arabic";
                break;
            case R.id.lang_french:
                new_lang = "French";
                break;
            case R.id.lang_german:
                new_lang = "German";
                break;
            case R.id.lang_spanish:
                new_lang = "Spanish";
                break;
            case R.id.lang_twi:
                new_lang = "Twi";
                break;
        }
        String translate_to = "("+new_lang+")";
        translate_to_text_view.setText(translate_to);
        DefaultLanguage = new_lang;

        Questions.build_questions_list(new_lang);

        category_select(category_selectIid);
        adapter.update_list(items);
        loadAnimation(questions_container);


        ConstraintLayout tint = findViewById(R.id.tint);
        ConstraintLayout lang_selector = findViewById(R.id.language_selector_layout);

        lang_selector.setVisibility(View.GONE);
        tint.setVisibility(View.GONE);
    }
}

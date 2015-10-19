package com.gridimage.codepath.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.gridimage.codepath.models.SearchOpts;
import com.gridimage.codepath.util.Utils;

public class AdvancedSearchActivity extends AppCompatActivity {
    private Spinner spSize;
    private Spinner spColor;
    private Spinner spType;
    private EditText etSite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);
        spSize = (Spinner) findViewById(R.id.spSize);
        spColor = (Spinner) findViewById(R.id.spColor);
        spType = (Spinner) findViewById(R.id.spType);
        etSite = (EditText) findViewById(R.id.etSite);
        SearchOpts searchOpts = (SearchOpts) getIntent().getSerializableExtra("searchOpts");
        if (searchOpts != null) {
            setSelection(spSize, searchOpts.imageSize);
            setSelection(spColor, searchOpts.color);
            setSelection(spType, searchOpts.type);
            etSite.setText(searchOpts.site);
        }
    }

    private void setSelection(Spinner spinner, String value) {
        if (Utils.any(value)) {
            spinner.setSelection(0);
        } else {
            spinner.setSelection(((ArrayAdapter)spinner.getAdapter()).getPosition(value));
        }
    }

    private String getSelectedValue(Spinner spinner) {
        String value = String.valueOf(spinner.getSelectedItem());
        if (Utils.any(value)) {
            value = null;
        }
        return value;
    }

    public void onSubmit(View view) {
        SearchOpts searchOpts = new SearchOpts();
        searchOpts.imageSize = getSelectedValue(spSize);
        searchOpts.color = getSelectedValue(spColor);
        searchOpts.type = getSelectedValue(spType);
        searchOpts.site = etSite.getText().toString();
        if (searchOpts.site != null && searchOpts.site.trim().isEmpty()) {
            searchOpts.site = null;
        }
        Intent intent = new Intent();
        intent.putExtra("searchOpts", searchOpts);
        setResult(RESULT_OK, intent);
        finish();
    }
}

package com.example.vkinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static com.example.vkinfo.utils.NetworkUtils.generateURL;
import static com.example.vkinfo.utils.NetworkUtils.getUrlResponse;

public class MainActivity extends AppCompatActivity {

    private EditText searchField;
    private Button searchButton;
    private TextView result;
    private TextView errorMassage;
    private ProgressBar loadingIndicator;

    private void showResultTextView() {
        result.setVisibility(View.VISIBLE);
        errorMassage.setVisibility(View.INVISIBLE);
    }

    private void showErrorTextView() {
        result.setVisibility(View.INVISIBLE);
        errorMassage.setVisibility(View.VISIBLE);
    }

    class VKQueryTask extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            loadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            String responce = null;
            try {
                responce = getUrlResponse(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responce;
        }

        @Override
        protected void onPostExecute(String responce) {
            String firstName = null;
            String lastName = null;

            if (responce != null && !responce.equals("")) {
                try {
                    JSONObject jsonResponse = new JSONObject(responce);
                    JSONArray jsonArray = jsonResponse.getJSONArray("response");
                    JSONObject userInfo = jsonArray.getJSONObject(0);
                    firstName = userInfo.getString("first_name");
                    lastName = userInfo.getString("last_name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String resaltData = "Name: " + firstName + "\n" + "LastName: " + lastName;
                result.setText(resaltData);
                showResultTextView();
            } else {
                showErrorTextView();
            }
            loadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.et_search_for_id);
        searchButton = findViewById(R.id.b_search_Vk);
        result = findViewById(R.id.tv_result);
        errorMassage = findViewById(R.id.tv_error);
        loadingIndicator = findViewById(R.id.pb_loadind_Indicator);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL generURL = generateURL(searchField.getText().toString());
                new VKQueryTask().execute(generURL);
            }
        };
        searchButton.setOnClickListener(onClickListener);
    }
}

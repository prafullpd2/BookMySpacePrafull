package com.neoresearch.bookmyspace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
//import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends Fragment implements View.OnClickListener {

    public Login() {
    }
    SessionManager session;
    EditText mob, pass;
    Button login;
    ImageButton redir;
    //ActionBar action;
    TextView banner;
   protected ProgressDialog pDialog;


    JSONParser jsonParser = new JSONParser();
    private static final String LOGIN_URL = "http://prasheel-acropolis.rhcloud.com/login.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Typeface typeface;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view=inflater.inflate(R.layout.activity_login,container,false);
        View InputFragmentView = inflater.inflate(R.layout.activity_login, container, false);

        redir=(ImageButton)InputFragmentView.findViewById(R.id.imageButton);
        mob = (EditText) InputFragmentView.findViewById(R.id.editText);
        pass = (EditText) InputFragmentView.findViewById(R.id.editText2);
        login = (Button) InputFragmentView.findViewById(R.id.button);
        session=new SessionManager(getActivity().getApplicationContext());
        banner=(TextView)InputFragmentView.findViewById(R.id.textView5);



        redir.setOnClickListener(this);
        if(session.isLoggedIn())
        {
            Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            startActivity(i);

        }

        login.setOnClickListener(this);


        return InputFragmentView;
    }


    @Override
    public void onClick(View v) {

          switch(v.getId()) {

              case R.id.imageButton:
                  ((LoginRegisterActivity)getActivity()).setCurrentItem(1, true);
                  break;
              case R.id.button:

                 new AttemptLogin().execute();

          }


    }




    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String mobile = mob.getText().toString();
            String password = pass.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("mobile", mobile));
                params.add(new BasicNameValuePair("password", password));

                Log.d("request!", "starting");
                // getting product details by making HTTP request
                JSONObject json = jsonParser.makeHttpRequest(LOGIN_URL, "POST",
                        params);

                // check your log for json response
                Log.d("Login attempt", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Login Successful!", json.toString());
                    session.createLoginSession(mobile, password);



                    Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                } else {
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
           pDialog.dismiss();
            mob.setText("");
            pass.setText("");
            if (file_url != null) {
                Toast.makeText(getActivity().getApplicationContext(), file_url, Toast.LENGTH_LONG).show();
            }

        }


    }


}
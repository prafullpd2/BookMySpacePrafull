package com.neoresearch.bookmyspace;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Register extends Fragment implements View.OnClickListener{
    public Register() {
    }
    EditText fname,mobile,email,pass;
    Button submit;
    ImageButton lgin;
    JSONParser jsonParser = new JSONParser();
    private static final String REGISTER_URL = "http://prasheel-acropolis.rhcloud.com/register.php";
   //private static final String REGISTER_URL = "http://192.168.0.105/test/register.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.activity_register, container, false);
       fname=(EditText)InputFragmentView.findViewById(R.id.register_name);
        mobile=(EditText)InputFragmentView.findViewById(R.id.register_contact);
        email=(EditText)InputFragmentView.findViewById(R.id.register_email);
        pass=(EditText)InputFragmentView.findViewById(R.id.register_password);
        submit=(Button)InputFragmentView.findViewById(R.id.register_submit);
        lgin=(ImageButton)InputFragmentView.findViewById(R.id.imageButton2);
       submit.setOnClickListener(this);
        lgin.setOnClickListener(this);
        return InputFragmentView;
        }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.imageButton2:
                ((LoginRegisterActivity)getActivity()).setCurrentItem(0, true);
                break;
            case R.id.register_submit:
                new CreateUser().execute();

        }


    }

    class CreateUser extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // pDialog = new ProgressDialog(Register.this);
            //pDialog.setMessage("Creating User...");
            //pDialog.setIndeterminate(false);
           // pDialog.setCancelable(true);
            //pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            String name = fname.getText().toString();
            String password = pass.getText().toString();
            String mail = email.getText().toString();
            String mob = mobile.getText().toString();
            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("name", name));
                params.add(new BasicNameValuePair("password", password));
                params.add(new BasicNameValuePair("mobile", mob));
                params.add(new BasicNameValuePair("email", mail));


                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        REGISTER_URL, "POST", params);

                // full json response
                Log.d("Registering attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());
                    Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Registering Failure!", json.getString(TAG_MESSAGE));
                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;

        }
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once product deleted
           // pDialog.dismiss();
            fname.setText("");
            pass.setText("");
            mobile.setText("");
            email.setText("");
            Intent i = new Intent(getActivity().getApplicationContext(), MainActivity.class);
            if (file_url != null){
            Toast.makeText(getActivity(), file_url, Toast.LENGTH_LONG).show();
            }

        }

    }
}


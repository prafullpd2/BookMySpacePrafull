package com.neoresearch.bookmyspace;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PopupOnMap extends Dialog implements View.OnClickListener{

    Activity c;
    Dialog d;
    Button ok, cancel;
    EditText editTextOnPopup;
    TextView name, contact,address,availability;

    public PopupOnMap(Activity a) {
        super(a);
        this.c = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup_on_map);
        ok = (Button) findViewById(R.id.popup_ok);
        cancel = (Button) findViewById(R.id.popup_cancel);


        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_ok:

                Intent intent = new Intent(getContext(),ConfirmationForBooking.class);
                intent.putExtra("address",address.getText().toString());
                intent.putExtra("contact",contact.getText().toString());
                intent.putExtra("name",name.getText().toString());
                c.startActivity(intent);




                dismiss();
                break;
            case R.id.popup_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void setName(String name,String address,String contact){

        this.name = (TextView) findViewById(R.id.popup_name);
        this.address= (TextView) findViewById(R.id.popup_address);
        this.contact = (TextView) findViewById(R.id.popup_contact);
        this.name.setText(this.name.getText()+" : "+name);
        this.address.setText(this.address.getText()+" : "+address);
        this.contact.setText(this.contact.getText()+" : "+contact);


    }



}

package com.neoresearch.bookmyspace;

import android.app.Activity;
import android.app.Dialog;
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
        editTextOnPopup = (EditText) findViewById(R.id.popup_edit_text);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_ok:
                c.finish();
                break;
            case R.id.popup_cancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }




}

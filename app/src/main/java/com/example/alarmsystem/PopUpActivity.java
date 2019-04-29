package com.example.alarmsystem;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class PopUpActivity extends Activity {

    AlertDialog.Builder popUpBuilder;
    AlertDialog popUpDialog;
    View popUpView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        popUpBuilder = new AlertDialog.Builder(PopUpActivity.this);
        popUpView = getLayoutInflater().inflate(R.layout.pop_up, null);
        popUpBuilder.setView(popUpView);
        popUpDialog = popUpBuilder.create();

        popUpDialog.show();
    }
}

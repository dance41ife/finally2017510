package com.shh.test.Fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.shh.test.R;

/**
 * Created by Administrator on 2017/5/27.
 */

public class RegisterFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register,null);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Register as a new user")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Register",null)
                .create();
    }
}

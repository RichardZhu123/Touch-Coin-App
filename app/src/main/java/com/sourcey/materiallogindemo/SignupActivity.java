package com.sourcey.materiallogindemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.Bind;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    //SharedPreferences usernameValues = new ObscuredSharedPreferences(this, this.getSharedPreferences("touchCoinUsernames", Context.MODE_PRIVATE) );
    //SharedPreferences passwordValues = new ObscuredSharedPreferences(this, this.getSharedPreferences("touchCoinPasswords", Context.MODE_PRIVATE) );
    SharedPreferences usernameValues;
    SharedPreferences passwordValues;
    SharedPreferences mSettings;
    //SharedPreferences.Editor userEditor = usernameValues.edit();
    //SharedPreferences.Editor passwordEditor = passwordValues.edit();


    public static ArrayList<String> usernames = new ArrayList<>();
    public static ArrayList<String> passwords = new ArrayList<>();
    public static ArrayList<String> names = new ArrayList<>();
    public static ArrayList<String> addresses = new ArrayList<>();
    public static int currentUserIndex = -1;

    public static String getCurrentName()
    {
        return names.get(currentUserIndex);
    }

    public static String getCurrentAddress()
    {
        return addresses.get(currentUserIndex);
    }

    public static ArrayList<String> getUsernames()
    {
        return usernames;
    }

    public static String getUsernameAt(int index)
    {
        return usernames.get(index);
    }

    public static String getPasswordAt(int index)
    {
        return passwords.get(index);
    }

    public static void setUsernames(Set<String> setUsernames)
    {
        usernames.clear();
        usernames.addAll(setUsernames);
    }

    public static int indexOfUser(String user) {
        return usernames.indexOf(user);
    }

    public static String passAt(int index) {
        return passwords.get(index);
    }

    public static void setCurrentUserIndex(int index)
    {
        currentUserIndex = index;
    }

    public static int getCurrentUserIndex()
    {
        return currentUserIndex;
    }


    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //usernameValues = this.getSharedPreferences("TouchCoinUsernames", 0);
        //passwordValues = this.getSharedPreferences("TouchCoinPasswords", 0);

        //usernameValues = this.getSharedPreferences("touchCoinUsernames", 0);
        //passwordValues = this.getSharedPreferences("touchCoinPasswords", 0);
        mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        

        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        //usernames.add(usernameValues.getString("touchCoinUsernames", null));
        //passwords.add(passwordValues.getString("touchCoinPasswords", null));
        //System.out.println(usernameValues.getString("touchCoinUsernames", null));
        // passwords = new ArrayList(passwordValues.getStringSet("touchCoinPasswords", null));
        // names = new ArrayList(prefs.getStringSet("touchCoinNames", null));
        // addresses = new ArrayList(prefs.getStringSet("touchCoinAddresses", null));
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.
        usernames.add(email);
        passwords.add(password);
        //usernameValues.edit().putStringSet("touchCoinUsernames", new HashSet<String>(usernames));
        //passwordValues.edit().putStringSet("touchCoinPasswords", new HashSet<String>(passwords));

        //String uuuser = usernameValues.getString("touchCoinUsernames", null);
        //String pppasswords = passwordValues.getString("touchCoinPasswords", null);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }



        return valid;
    }


}
package it.unibg.p3d4amb.carracingcarboard.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import it.unibg.p3d4amb.carracingcarboard.DB.PostCall;
import it.unibg.p3d4amb.carracingcarboard.Manager.Enum.TypeCall;
import it.unibg.p3d4amb.carracingcarboard.Manager.PasswordManager;
import it.unibg.p3d4amb.carracingcarboard.R;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import io.fabric.sdk.android.Fabric;


public class LogInActivity extends Activity {

    private Activity logInActivity;//this activity

    private EditText email;//email input field
    private EditText password;//password input field

    private Button logIn;//LOG IN button
    private Button signUp;//SIGN UP button
    private Button play;//PLAY WITHOUT REGISTRATION button

    private TextView status;
    private TextView statusUpdate;
    private TextView help;//describe our app
    private TextView forgot;//"forgot you password?" link
    private TextView workWithUs;//"collaborate with us" link
    private TextView updateData;//"update your info" link

    private CheckBox keepLog;//checkbox keep me logged in

    private PostCall post,postUpdate;//my PostCall object

    private String id_user;// save the id_user from server
    private String doctor;// doctor flag
    private String date;//birthday
    private String passwordToSend;// password to send to the server
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log_in);

        logInActivity = this;

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        logIn = (Button) findViewById(R.id.logInButton);
        signUp = (Button) findViewById(R.id.textViewSignUp);
        keepLog = (CheckBox) findViewById(R.id.checkBox);
        workWithUs = (TextView) findViewById(R.id.workWithUs);
        help= (TextView) findViewById(R.id.help);
        help.setText("?");
        workWithUs.setPaintFlags(workWithUs.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        updateData = (TextView) findViewById(R.id.updateInfo);
        updateData.setPaintFlags(updateData.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        passwordToSend = PasswordManager.calculateHash(password.toString());
        play = (Button) findViewById(R.id.playWithoutReg);
        forgot = (TextView) findViewById(R.id.forgotPassword);
        forgot.setPaintFlags(forgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        status = (TextView) findViewById(R.id.status);
        statusUpdate = (TextView) findViewById(R.id.statusUpdate);
        SharedPreferences settings1;

        status.setAlpha(0);
        statusUpdate.setAlpha(0);

        keepLog.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getSharedPreferences("LOG_IN",0 );
                SharedPreferences.Editor editor = settings.edit();
                if (isChecked) {
                    editor.putString("email", email.getText().toString());
                    editor.putString("password", password.getText().toString());
                    editor.putBoolean("isCheck", true);
                    editor.commit();

                } else {
                    editor.putBoolean("isCheck", false);
                    editor.commit();
                }
            }
        });
        if (getSharedPreferences("LOG_IN", 0) != null) {
            settings1 = getSharedPreferences("LOG_IN", 0);
            if (settings1.getBoolean("isCheck", false)) {
                email.setText(settings1.getString("email", ""));
                password.setText(settings1.getString("password", ""));
                keepLog.setChecked(settings1.getBoolean("isCheck", false));
            }
        }


        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(logInActivity);
                    alert.setTitle("Warning");
                    alert.setMessage("wrong email or password");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alert.show();

                } else {
                    passwordToSend = PasswordManager.calculateHash(password.getText().toString());
                    post = new PostCall(email.getText().toString(), passwordToSend, status);
                    post.myPostCall(TypeCall.LOG_IN, logInActivity);
                    launchRingDialog();

                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpWarning();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              warningNoRegistration();

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordAlert();
            }
        });

        workWithUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workWithUsAlert();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfoAlert();
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpAlert();
            }
        });

    }


    private void signUpWarning() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("for the purpose of proper treatment," + '\n' + "enter patient data in fields firstname,lastname and birthday");
        alert.setTitle("Attention");
        alert.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertSignUp("", "", "","","");
            }
        });
        alert.show();
    }

    /**
     *
     * @param firstname
     * @param lastname
     * @param email
     * @param color
     * @param birthday
     */
    private void alertSignUp(String firstname, String lastname, final String email,String color,String birthday) {


        final EditText firstName = new EditText(this);
        final EditText lastName = new EditText(this);
        final EditText eMail = new EditText(this);
        final EditText myColor = new EditText(this);
        final EditText password = new EditText(this);
        final EditText confirmPassword = new EditText(this);
        final EditText formDate = new EditText(this);

        LinearLayout layout = new LinearLayout(this);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        formDate.setFocusable(false);
        alert.setMessage("SIGN UP");

        firstName.setText(firstname);
        lastName.setText(lastname);
        eMail.setText(email);
        myColor.setText(color);
        formDate.setText(birthday);
        firstName.setHint("first name");
        lastName.setHint("last name");
        eMail.setHint("email");
        formDate.setHint("birthday");
        myColor.setHint("your favourite color");
        password.setHint("password");
        confirmPassword.setHint("confirm password");
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        confirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        formDate.setInputType(0);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(firstName);
        layout.addView(lastName);
        layout.addView(eMail);
        layout.addView(formDate);
        layout.addView(myColor);
        layout.addView(password);
        layout.addView(confirmPassword);
        alert.setView(layout);
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        final Date currentDate = new Date(year, month, day);
        // Launch Date Picker Dialog
        final DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // Display Selected date in textbox
                month += 1;
                Date sectedDate = new Date(year, month, day);
                if (currentDate.before(sectedDate)) {
                    Toast.makeText(getBaseContext(), "Please insert a correct date", Toast.LENGTH_LONG).show();

                } else {
                    formDate.setText(day + "/" + month + "/" + year);
                    date = year + "-" + month + "-" + day;
                }
            }

        }
                , year, month, day);

        formDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show();
            }
        });


        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String backUpFirstName = firstName.getText().toString();
                String backUpLastName = lastName.getText().toString();
                String backUpEmail = eMail.getText().toString();
                String backUpColor = myColor.getText().toString();
                String backUpDate= date;
                if (firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || eMail.getText().toString().isEmpty() || myColor.getText().toString().isEmpty() || formDate.getText().toString().isEmpty()) {
                    Toast.makeText(getBaseContext(), "some field was empty", Toast.LENGTH_LONG).show();
                    alertSignUp(backUpFirstName, backUpLastName, backUpEmail,backUpColor,backUpDate);
                } else {
                    if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                        //ALERT MESSAGE
                        Toast.makeText(getBaseContext(), "Please insert the same password", Toast.LENGTH_LONG).show();
                        alertSignUp(backUpFirstName, backUpLastName, backUpEmail,backUpColor,backUpDate);


                    } else {
                        if (!password.getText().toString().isEmpty()) {
                            //Cript the password
                            passwordToSend = PasswordManager.calculateHash(password.getText().toString());
                            //send data
                            post = new PostCall(firstName.getText().toString(), lastName.getText().toString(), eMail.getText().toString(), myColor.getText().toString(), date, passwordToSend, statusUpdate);
                            post.myPostCall(TypeCall.SIGN_UP, logInActivity);
                            launchRingDialog();
                        } else {
                            Toast.makeText(getBaseContext(), "you must choose a password", Toast.LENGTH_LONG).show();
                            alertSignUp(backUpFirstName, backUpLastName, backUpEmail,backUpColor,backUpDate);
                        }
                    }
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alert.show();

    }

    private void warningNoRegistration() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Warning");
        alert.setMessage("you can play without registration, but we can't trace your improvement");
        alert.setPositiveButton("PLAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                launchRingDialog();
                passwordToSend = PasswordManager.calculateHash("default_user");
                post = new PostCall("3d4ambunibg@gmail.com", passwordToSend, status);
                post.myPostCall(TypeCall.LOG_IN, logInActivity);
            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();
    }

    private void forgotPasswordAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("resert your password: ");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText mailTo = new EditText(this);
        final EditText myColor = new EditText(this);
        mailTo.setHint("insert email");
        myColor.setHint("your favourite color");
        layout.addView(mailTo);
        layout.addView(myColor);
        alert.setView(layout);
        alert.setTitle("Warning");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newPasswd = generateRandom();
                passwordToSend = PasswordManager.calculateHash(newPasswd);
                post = new PostCall(mailTo.getText().toString(), myColor.getText().toString(), newPasswd, passwordToSend, status);
                post.myPostCall(TypeCall.RESET, logInActivity);
                launchRingDialog();
            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();
    }

    private void updateInfoAlert() {

        final AlertDialog.Builder alert;
        alert = new AlertDialog.Builder(LogInActivity.this);
        String vector[] = {"email", "firstname", "lastname", "birthday", "password"};
        alert.setSingleChoiceItems(vector, 0, null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                        Integer i = new Integer(selectedPosition);
                        statusUpdate.setText(i.toString());
                        updateLogInAlert();
                    }
                })
                .show();

    }

    private void updateLogInAlert() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText email = new EditText(this);
        final EditText password = new EditText(this);
        email.setHint("email");
        password.setHint("password");
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        layout.addView(email);
        layout.addView(password);
        alert.setView(layout);
        alert.setTitle("log in");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                passwordToSend = PasswordManager.calculateHash(password.getText().toString());
                post = new PostCall(email.getText().toString(), passwordToSend, status);
                post.myPostCall(TypeCall.LOG_IN, logInActivity);
                launchRingDialogUpdate();
            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();
    }

    private void updateEmailAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText newValue = new EditText(this);
        newValue.setHint("insert the new email");
        layout.addView(newValue);
        alert.setView(layout);
        alert.setTitle("Update");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                post = new PostCall(id_user, newValue.getText().toString(), statusUpdate, true);
                post.myPostCall(TypeCall.UPDATE_MAIL, logInActivity);
            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();

    }

    private void updateFirstnameAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText newValue = new EditText(this);
        newValue.setHint("insert firstname");
        layout.addView(newValue);
        alert.setView(layout);
        alert.setTitle("Update");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                postUpdate = new PostCall(id_user, newValue.getText().toString(), statusUpdate, true);
                postUpdate.myPostCall(TypeCall.UPDATE_FIRSTNAME, logInActivity);
            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();

    }

    private void updateLastnameAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText newValue = new EditText(this);
        newValue.setHint("insert lastname");

        layout.addView(newValue);

        alert.setView(layout);
        alert.setTitle("Update");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                post = new PostCall(id_user, newValue.getText().toString(), statusUpdate, true);
                post.myPostCall(TypeCall.UPDATE_LASTNAME, logInActivity);
            }
        });
        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();

    }

    private void updateBirthdayAlert() {

        final EditText formDate = new EditText(this);
        LinearLayout layout = new LinearLayout(this);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        formDate.setFocusable(false);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(formDate);
        alert.setView(layout);
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        formDate.setHint("Birthday");
        alert.setTitle("Update");
        formDate.setInputType(0);

        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        final Date currentDate = new Date(year, month, day);
        // Launch Date Picker Dialog
        final DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                // Display Selected date in textbox
                month += 1;
                Date sectedDate = new Date(year, month, day);
                if (currentDate.before(sectedDate)) {
                    Toast.makeText(getBaseContext(), "Please insert a correct date", Toast.LENGTH_LONG).show();

                } else {
                    formDate.setText(day + "/" + month + "/" + year);
                    date = year + "-" + month + "-" + day;
                }
            }

        }
                , year, month, day);

        formDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd.show();
            }
        });


        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                post = new PostCall(id_user, date.toString(), statusUpdate, true);
                post.myPostCall(TypeCall.UPDATE_BIRTHDAY, logInActivity);
            }
        });
        alert.show();

    }

    private void updatePasswordAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Update");
        final EditText password = new EditText(this);
        final CheckBox check = new CheckBox(this);
        check.setText("show password");
        password.setHint("new password");
        password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    password.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else if (!isChecked) {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(password);
        layout.addView(check);
        alert.setView(layout);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                passwordToSend = PasswordManager.calculateHash(password.getText().toString());
                post = new PostCall(id_user, passwordToSend, statusUpdate, true);
                post.myPostCall(TypeCall.UPDATE_PASSWORD, logInActivity);

            }
        });

        alert.show();
    }

    private void launchRingDialogUpdate() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(LogInActivity.this, "Please wait ...", "contacting server ...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...

                    Thread.sleep(2000);
                    StringTokenizer token = new StringTokenizer(status.getText().toString());
                    token.nextToken("/");
                    id_user = token.nextToken("/");
                    doctor = token.nextToken("/");

                    if (statusUpdate.getText().toString().equals("0")) {
                        logInActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateEmailAlert();
                            }
                        });


                    } else if (statusUpdate.getText().toString().equals("1")) {
                        logInActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateFirstnameAlert();
                            }
                        });


                    }
                    else if (statusUpdate.getText().toString().equals("2")) {
                        logInActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateLastnameAlert();
                            }
                        });


                    } else if (statusUpdate.getText().toString().equals("3")) {
                        logInActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateBirthdayAlert();
                            }
                        });
                    } else if (statusUpdate.getText().toString().equals("4")) {
                        logInActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updatePasswordAlert();
                            }
                        });
                    }

                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();
            }
        }).start();

    }

    private void launchRingDialog() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(LogInActivity.this, "Please wait ...", "contacting server ...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...

                    Thread.sleep(2000);

                    StringTokenizer token = new StringTokenizer(status.getText().toString());
                    token.nextToken("/");
                    id_user = token.nextToken("/");
                    doctor = token.nextToken("/");

                    if (doctor.contains("1")) {
                        Intent i = new Intent(LogInActivity.this, DoctorActivity.class);
                        i.putExtra("id_doctor", id_user);
                        startActivity(i);

                    } else if (status.getText().toString().contains("connection")) {

                            SharedPreferences settings2 = getSharedPreferences("TUTORIAL", 0);
                            if (settings2.getBoolean("isCheck", false)) {
                                Intent i = new Intent(LogInActivity.this, SplashActivity.class);
                                i.putExtra("id_user", id_user);
                                startActivity(i);
                            }
                            else {

                                Intent i = new Intent(LogInActivity.this, TutorialActivity.class);
                                i.putExtra("id_user", id_user);
                                startActivity(i);
                            }


                    }
                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();
            }
        }).start();

    }

    private void workWithUsAlert() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("The 3D4Amb project aims at developing a system based on the 3D for the diagnosis and treatment of amblyopia in young children." + '\n' + '\n' +
                "if you are a Doctor and you want to collaborate with us, send a mail, just click ok here " + '\n' + "Best regards," + '\n' + '\n' + "3D4Amb staff " + '\n' + '\n' + "3D4AmbUnibg@gmail.com" + '\n' + '\n' + "http://3d4amb.unibg.it");
        alert.setTitle("COLLABORATE WITH US");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"3d4ambunibg@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "3D4Amb DOCTOR COLLABORATION");
                i.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(LogInActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        alert.show();
    }

    private void helpAlert() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://3d4amb.unibg.it/3dcar/cardboard%20site/"));
        startActivity(browserIntent);

    }


    private String generateRandom() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(40, random).toString(32);
    }


}


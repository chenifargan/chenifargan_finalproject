package com.example.chenifargan_finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
private Button shareContext,btn_search,btn_next;
private TextInputEditText et_phonenumber,et_name;
private String phoneNumber,nameofperson;
    private static final int MANUALLY_CONTACTS_PERMISSION_REQUEST_CODE = 124;
    private final int REQUEST_CODE_PERMISSION_CONTACTS = 900;

DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://chenifarganfinalproject-default-rtdb.firebaseio.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();

        shareContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestContacts();


/*
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                        !=PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},1);
                }
                else{
                    getContact();
                }
*/



            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
         public void onClick(View view) {
                phoneNumber = needToChack(et_phonenumber.getText().toString().trim());
                Intent intent =null;
                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber",phoneNumber);
                intent= new Intent(MainActivity.this, ViewAllContactsActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);


      }
    });


        et_phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(validateMobile(et_phonenumber.getText().toString()))
                {
                    btn_search.setEnabled(true);

                }
                else{
                    btn_search.setEnabled(false);
                    et_phonenumber.setError("Invalid Mobile No");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameofperson =et_name.getText().toString();
                if(!nameofperson.matches("")){
                    btn_search.setVisibility(View.VISIBLE);
                    et_phonenumber.setVisibility(View.VISIBLE);
                    et_name.setVisibility(View.INVISIBLE);
                    btn_next.setVisibility(View.INVISIBLE);
                    shareContext.setVisibility(View.VISIBLE);

                }
                else{
                    Toast.makeText(MainActivity.this,"ENTER NAME",Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    private void getContact() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
         @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
         @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
          /*  TelephonyManager tMgr = (TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();*/
       String  number1 = needToChack(number);
            Log.d("TAG", "getContact1111: "+ number1);

            databaseReference.child("user").child(number1).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        model m = new model(number1);
                        Log.d("TAG", "onDataChange: ");

                        //  ArrayList <String> st = new ArrayList<String>();
                        ArrayList <person> arrayList =  new ArrayList<person>();
                        for (DataSnapshot dss : snapshot.getChildren()){

                            person p = dss.getValue(person.class);
                     //       String n = dss.getValue(String.class);

                            arrayList.add(p);
                            //st.add(n);

                       }
                        arrayList.add(new person(name,nameofperson));
                        for (int i=0;i<arrayList.size();i++){
                            Log.d("TAG", arrayList.get(i).getNameOfContext() + arrayList.get(i).getNameOfPerson());
                        }
                        //st.add(name);
                        m.setPerson(arrayList);
                       // m.setName(st);

                        databaseReference.child("user").child(number1).setValue(arrayList).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"list is uploaded",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });




                    }
                    else{

                        model m = new model(number1);
                       //ArrayList <String> arr = m.setNametoArry(name);
                        ArrayList<person> arrlist = m.addobject(name,nameofperson);
                        databaseReference.child("user").child(number1).setValue(arrlist).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"list is uploaded",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }




             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });








        }
    }

    private String needToChack(String number) {
        String s =number;


        if(s.substring(3,4).equals("-")) {
            s = s.substring(0, 3) + s.substring(4, 7) + s.substring(8);
           // Log.d("TAG", "chen: "+ s);


        }
        else if(s.substring(0,4).equals("+972")){
            s = s.substring(4);

        }
        else if(s.substring(0,3).equals("972")){
            s= s.substring(3);

        }
        else if(s.substring(0,1).equals("(")){
            s= s.substring(1,4)+s.substring(6,9)+s.substring(10);
        }

        return s;

    }

    private void initViews() {
        shareContext = findViewById(R.id.btn_clicktosharecontect);
        et_phonenumber = findViewById(R.id.et_phonenumber);
        btn_search = findViewById(R.id.next);
        et_name = findViewById(R.id.et_name);
        btn_next = findViewById(R.id.next1);
    }

    public boolean validateMobile(String input){
        Pattern p = Pattern.compile("[0][5][0-5][0-9]{7}");
        Matcher m = p.matcher(input);
        return m.matches();
    }
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                getContact();

            }


        }
    }

     */
    private void openPermissionSettingDialog() {
        String message = "Setting screen if user have permanently disable the permission by clicking Don't ask again checkbox.";
        AlertDialog alertDialog =
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage(message)
                        .setPositiveButton(getString(android.R.string.ok),
                                (dialog, which) -> {
                                    openSettingsManually();
                                    dialog.cancel();
                                }).show();
        alertDialog.setCanceledOnTouchOutside(true);
    }

    private void openSettingsManually() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, MANUALLY_CONTACTS_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_CONTACTS: {
                Log.d("pttt", "REQUEST_CODE_PERMISSION_CONTACTS");
                boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
                if (result) {
                    getContact();
                    return;
                }
                requestPermissionWithRationaleCheck();
                return;
            }

        }

    }
    private void requestContacts() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_PERMISSION_CONTACTS);
    }


    private void requestPermissionWithRationaleCheck() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
            Log.d("pttt", "shouldShowRequestPermissionRationale = true");
            // Show user description for what we need the permission

            String message = "permission description for approve";
            AlertDialog alertDialog =
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage(message)
                            .setPositiveButton(getString(android.R.string.ok),
                                    (dialog, which) -> {
                                        requestContacts();
                                        dialog.cancel();
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // disabled functions due to denied permissions
                                }
                            })
                            .show();
            alertDialog.setCanceledOnTouchOutside(true);
        } else {
            Log.d("pttt", "shouldShowRequestPermissionRationale = false");
            openPermissionSettingDialog();
        }
    }
}
/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class VerivikasiData extends AppCompatActivity {
    private final static int REQUEST_CODE = 999;

    Button btnPhone, btnEmail ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verivikasi_data);
        btnEmail = findViewById(R.id.btn_veremail);
        btnPhone = findViewById(R.id.btn_verhp);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage (LoginType.EMAIL);
            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoginPage (LoginType.PHONE);
            }
        });

//        printKeyHash();
    }
    private void startLoginPage(LoginType loginType) {
        if (loginType == LoginType.EMAIL){
            Intent intent = new Intent(this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL,AccountKitActivity.ResponseType.TOKEN);// use token when enable client acces token flow is YES
            intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build()); //ResponseType.CODE /TOKEN
            startActivityForResult(intent,REQUEST_CODE);
        }else if (loginType == LoginType.PHONE){
            Intent intent = new Intent(this, AccountKitActivity.class);
            AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                    new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.PHONE,AccountKitActivity.ResponseType.TOKEN);// use token when enable client acces token flow is NO
            intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,configurationBuilder.build());//ResponseType.CODE / TOKEN
            startActivityForResult(intent,REQUEST_CODE);
        }

    }
    // press cntrl 0


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE){
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (result.getError() != null){
                Toast.makeText(this,""+result.getError().getErrorType().getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
            else if (result.wasCancelled()){
                Toast.makeText(this,"Cancel", Toast.LENGTH_SHORT).show();
                return;

            }else{
                if (result.getAccessToken() != null )

//                    Toast.makeText(this,"Succes ! "+result.getAccessToken().getAccountId(), Toast.LENGTH_LONG).show();
                    Toast.makeText(this,"Succes ! ", Toast.LENGTH_LONG).show();
                else

                    Toast.makeText(this,"Succes ! ", Toast.LENGTH_LONG).show();
//                    Toast.makeText(this,"Succes ! "+result.getAuthorizationCode().substring(0,10), Toast.LENGTH_LONG).show();

                startActivity(new Intent(this,IsiData.class));
            }
        }
    }


    private void printKeyHash() {
        try{
            PackageInfo info   = getPackageManager().getPackageInfo("com.example.sholeh_attadzkirah.alumniunuja",
                    PackageManager.GET_SIGNATURES);

            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}

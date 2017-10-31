package momma_beta.momma_bv.Splash_Login_SignUp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tsengvn.typekit.TypekitContextWrapper;

import momma_beta.momma_bv.Model.SignUpResult;
import momma_beta.momma_bv.Model.UserInfo;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp2Activity extends AppCompatActivity {
    private String pemail, pname, ppasswd, pnickname, sex_result;
    private String strColor1 = "#FFFFFF", strColor2 = "#4fbcba";
    private Button sign_end_btn, boybox, girlbox;
    private EditText bname_edit, bbrith_edit;
    private boolean boy_set = false, girl_set = false;
    NetworkService networkService;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        networkService = ApplicationController.getInstance().getNetworkService();

        Intent signup = getIntent();
        pemail = signup.getExtras().getString("pemail");
        pname = signup.getExtras().getString("pname");
        ppasswd = signup.getExtras().getString("ppasswd");
        pnickname = signup.getExtras().getString("pnickname");

        sign_end_btn = (Button)findViewById(R.id.sign_end_btn);
        bname_edit = (EditText)findViewById(R.id.bname_edit);
        bbrith_edit = (EditText)findViewById(R.id.bbirth_edit);
        boybox = (Button) findViewById(R.id.boybox);
        girlbox = (Button) findViewById(R.id.girlbox);

        boybox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boy_set == true){
                    boybox.setBackgroundResource(R.drawable.girlboy_box_1);
                    boybox.setTextColor(Color.parseColor(strColor2));
                    boy_set = false;
                } else {
                    if(girl_set == true){
                        girlbox.setBackgroundResource(R.drawable.girlboy_box_1);
                        girlbox.setTextColor(Color.parseColor(strColor2));
                        girl_set = false;
                        boybox.setBackgroundResource(R.drawable.girlbox_box_2);
                        boybox.setTextColor(Color.parseColor(strColor1));
                        boy_set = true;
                    } else {
                        boybox.setBackgroundResource(R.drawable.girlbox_box_2);
                        boybox.setTextColor(Color.parseColor(strColor1));
                        boy_set = true;
                    }
                }
                if(boy_set == true){
                    sex_result = "남아";
                }else if(girl_set == true){
                    sex_result = "여아";
                }
            }
        });

        girlbox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(girl_set == true){
                    girlbox.setBackgroundResource(R.drawable.girlboy_box_1);
                    girlbox.setTextColor(Color.parseColor(strColor2));
                    girl_set = false;
                } else {
                    if(boy_set == true){
                        boybox.setBackgroundResource(R.drawable.girlboy_box_1);
                        boybox.setTextColor(Color.parseColor(strColor2));
                        boy_set = false;
                        girlbox.setBackgroundResource(R.drawable.girlbox_box_2);
                        girlbox.setTextColor(Color.parseColor(strColor1));
                        girl_set = true;
                    } else {
                        girlbox.setBackgroundResource(R.drawable.girlbox_box_2);
                        girlbox.setTextColor(Color.parseColor(strColor1));
                        girl_set = true;
                    }
                }
                if(boy_set == true){
                    sex_result = "남아";
                }else if(girl_set == true){
                    sex_result = "여아";
                }
        }
        });



        sign_end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }
    public void signup()
    {
        UserInfo user = new UserInfo();
        user.p_name = pname.toString();
        user.email = pemail.toString();
        user.birth = bbrith_edit.getText().toString();
        user.nickname = pnickname.toString();
        user.password = ppasswd.toString();
        user.b_name = bname_edit.getText().toString();
        user.sex = sex_result.toString();

        Log.i("sex", sex_result);


        Call<SignUpResult> signupResultCall = networkService.getSignResult(user);
        signupResultCall.enqueue(new Callback<SignUpResult>()
        {
            @Override
            public void onResponse(Call<SignUpResult> call, Response<SignUpResult> response) {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    SignUpResult signUpResult = response.body();
                    Log.i("MyTag", "결과 : " + signUpResult);
                    Toast.makeText(getApplicationContext(), "회원가입 완료",Toast.LENGTH_LONG).show();
                    Intent signend = new Intent(getApplicationContext(), LoginActivity.class);
                    signend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    signend.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(signend);
                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<SignUpResult> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
                finish();
            }
        });
    }
}

package momma_beta.momma_bv.Splash_Login_SignUp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import momma_beta.momma_bv.Intro.IntroActivity;
import momma_beta.momma_bv.Model.SignUpResult;
import momma_beta.momma_bv.Model.UserInfo;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by idongsu on 2017. 5. 17..
 */

public class NicknameDialog extends Dialog
{

    private Activity activity;
    private String title;
    private View.OnClickListener clickListener;
    NetworkService networkService;
    String email;
    EditText nickname;
    Button nick_confirm;
    String p_name;
    SharedPreferences.Editor autoLogin;
    SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.nickname_dialog);

        auto = getContext().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        nickname = (EditText)findViewById(R.id.nick_edit);
        nick_confirm = (Button)findViewById(R.id.nick_confirm);
        networkService = ApplicationController.getInstance().getNetworkService();
        nick_confirm.setOnClickListener(mClickCloseListener);

    }

    public NicknameDialog(Activity activity, String p_name, String email)
    {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
        this.p_name = "미입력";
        this.email = email;

    }

    public void signup()
    {
        UserInfo user = new UserInfo();


        user.p_name = p_name;
        user.email = email;
        user.birth = "00000000";
        user.nickname = nickname.getText().toString();
        user.password = "";
        user.b_name = "";
        user.sex = "";

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
                    Toast.makeText(getApplicationContext(), "닉네임 설정완료",Toast.LENGTH_LONG).show();
                    autoLogin.putString("inputId", nickname.getText().toString());
                    autoLogin.putString("inputPwd", "abcd");
                    autoLogin.putString("nickname", nickname.getText().toString());
                    autoLogin.commit();
                    cancel();
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
                activity.finish();
            }
        });
    }

    View.OnClickListener mClickCloseListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            signup();
            Intent signend = new Intent(getApplicationContext(), IntroActivity.class);
            signend.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            signend.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(signend);
        }
    };
}


package momma_beta.momma_bv.Splash_Login_SignUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import momma_beta.momma_bv.Intro.IntroActivity;
import momma_beta.momma_bv.Model.LoginInfo;
import momma_beta.momma_bv.Model.LoginResult1;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
{
    private FrameLayout login_btn, sign_btn;
    private EditText email_edit, passwd_edit;
    private CheckBox auto_Login, save_id;
    SharedPreferences auto;
    SharedPreferences.Editor autoLogin;
    ImageView kakao;
    LoginResult1.ResultData data;
    NetworkService networkService;
    String loginId, loginPwd, nickname;
    CallbackManager callbackManager;
    private int FlagLogin = 0;
    private String userName;
    private String userId;
    private String profileUrl;
    private SessionCallback mKakaocallback;
    NicknameDialog nicknameDialog;
    Activity activity = LoginActivity.this;
    JSONObject jsonObject;
    int autoFlag = 0;
    int saveidFlag = 0;
    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data))
        {
            super.onActivityResult(requestCode, resultCode, data);
            return;//여긴 카톡
        }
        else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            return;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        passwd_edit = (EditText)findViewById(R.id.passwd_edit);
        email_edit = (EditText)findViewById(R.id.email_edit);
        login_btn = (FrameLayout) findViewById(R.id.login_btn);
        sign_btn = (FrameLayout) findViewById(R.id.sign_btn);
        kakao = (ImageView)findViewById(R.id.login_kakao);
        auto_Login = (CheckBox)findViewById(R.id.auto_login_btn);
        save_id = (CheckBox)findViewById(R.id.save_id_btn);


        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(getApplicationContext(), SignUpActivity.class);
                signup.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                signup.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(signup);
            }
        });

        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 카카오 로그인 요청
                Log.i("mytag","kakao login request");
                isKakaoLogin();
            }
        });


        //자동로그인 처리
        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        autoLogin = auto.edit();
        loginId = auto.getString("inputId",null);
        loginPwd = auto.getString("inputPwd",null);
        nickname = auto.getString("nickname",null);
        autoFlag = auto.getInt("autoflag",0);
        saveidFlag = auto.getInt("save",0);

        if(loginId !=null && loginPwd != null && autoFlag == 1)
        {
                Toast.makeText(LoginActivity.this, nickname + "님 환영합니다", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
        }
        else if(loginId !=null && loginPwd != null && autoFlag == 0)
        {
            email_edit.setText(loginId);
        }

        else if(loginId == null && loginPwd == null)
        {
            login_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {

                    networkLogin();
                    Log.i("FlagLogin",FlagLogin+"");
                    if(FlagLogin == 1)
                    {
                        auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

                        autoLogin.putString("inputId", email_edit.getText().toString());
                        autoLogin.putString("inputPwd", passwd_edit.getText().toString());
                        autoLogin.putString("nickname", nickname);
                        autoLogin.commit();
                        Intent gointro = new Intent(getApplicationContext(), IntroActivity.class);
                        gointro.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        gointro.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(gointro);
                    }

                }
            });

        }
        auto_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(autoFlag == 0) //1이 자동로그인 설정
                {
                    autoLogin.putInt("autoflag",1);
                    autoLogin.commit();
                }
                else
                {
                    autoLogin.putInt("autoflag",0);
                    autoLogin.commit();
                }
            }
        });
        save_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


}
    // 로그인 레트로핏
    private void networkLogin()
    {
        final LoginInfo login = new LoginInfo();

        login.email = email_edit.getText().toString();
        login.password = passwd_edit.getText().toString();
        Call<LoginResult1> loginResultCall = ApplicationController.getInstance().getNetworkService().getLoginResult(login);
        loginResultCall.enqueue(new Callback<LoginResult1>() {
            @Override
            public void onResponse(Call<LoginResult1> call, Response<LoginResult1> response) {
                if (response.isSuccessful())
                {
                    data = response.body().result;
                    if (data.signresult.signin == true)
                    {

                        FlagLogin = 1;
                        Log.i("mytag","login nickname : "+data.signresult.nickname);
                        autoLogin.putString("nickname",data.signresult.nickname);
                        autoLogin.commit();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        FlagLogin = 0;
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginResult1> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), "Failed to load", Toast.LENGTH_LONG).show();
                Log.i("MyTag", "에러내용 : " + t.getMessage());
                finish();
            }
        });
    }


    //카카오톡 로그인
    private void isKakaoLogin()
    {
        // 카카오 세션을 오픈한다

        mKakaocallback = new SessionCallback();
        com.kakao.auth.Session.getCurrentSession().addCallback(mKakaocallback);
        com.kakao.auth.Session.getCurrentSession().checkAndImplicitOpen();
        com.kakao.auth.Session.getCurrentSession().open(AuthType.KAKAO_TALK_EXCLUDE_NATIVE_LOGIN, LoginActivity.this);
        Log.i("mytag", "kakao session open");
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened()
        {
            redirectSignupActivity();  // 세션 연결성공 시 redirectSignupActivity() 호출
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
            setContentView(R.layout.activity_login); // 세션 연결이 실패했을때
        }                                            // 로그인화면을 다시 불러옴
    }
    protected void redirectSignupActivity()
    {

//        profileUrl = userProfile.getProfileImagePath();
//        userId = String.valueOf(userProfile.getId());
//        userName = userProfile.getNickname();
        Log.i("kakao info", "profile : "+ profileUrl+"userID : "+ userId +"User name : "+ userName);
        autoLogin = auto.edit();
        autoLogin.putString("profile", profileUrl);
        autoLogin.commit();
        nicknameDialog = new NicknameDialog(activity,userId,"미입력");
        autoLogin.putInt("autoflag",1);
        autoLogin.commit();
        nicknameDialog.show();
    }




    // 페이스북 로그인
    public void facebookLoginOnClick(View v)
    {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null)
                        {

                        }
                        else
                        {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                            jsonObject = user;
                            try
                            {
                                autoLogin.putInt("autoflag",1);
                                autoLogin.commit();
                                nicknameDialog = new NicknameDialog(activity,jsonObject.get("name").toString(),jsonObject.get("id").toString());
                                nicknameDialog.show();
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                            setResult(RESULT_OK);

                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }
}

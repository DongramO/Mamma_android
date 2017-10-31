package momma_beta.momma_bv.Splash_Login_SignUp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tsengvn.typekit.TypekitContextWrapper;

import momma_beta.momma_bv.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText pemail_edit, pname_edit, ppasswd_edit, ppasswd2_edit, pnickname_edit;
    private Button go_signup2_btn;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        pemail_edit = (EditText)findViewById(R.id.pemail_edit);
        pname_edit = (EditText)findViewById(R.id.pname_edit);
        ppasswd_edit = (EditText)findViewById(R.id.ppasswd_edit);
        ppasswd2_edit = (EditText)findViewById(R.id.ppasswd2_edit);
        pnickname_edit = (EditText)findViewById(R.id.pnickname_edit);

        go_signup2_btn = (Button)findViewById(R.id.go_signup2_btn);

        go_signup2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), SignUp2Activity.class);
                next.putExtra("pemail", pemail_edit.getText().toString());
                next.putExtra("pname", pname_edit.getText().toString());
                next.putExtra("ppasswd", ppasswd_edit.getText().toString());
                next.putExtra("ppasswd2", ppasswd2_edit.getText().toString());
                next.putExtra("pnickname", pnickname_edit.getText().toString());
                startActivity(next);
            }
        });
    }
}

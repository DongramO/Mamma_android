package momma_beta.momma_bv.product;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.IngreList;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.network.NetworkService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by idongsu on 2017. 5. 17..
 */

public class IngreDialog extends Dialog
{
    TextView name_en, name_kr, effect1, effect2, effect3, side1, side2, side3;
    ImageView close;
    IngreList.ResultData data1;
    private Activity activity;
    private String title;
    private View.OnClickListener clickListener;
    NetworkService networkService;
    String ingrename;
    ArrayList<TextView> effect;
    ArrayList<TextView> side;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.ingre_dialog);
        networkService = ApplicationController.getInstance().getNetworkService();

        name_en = (TextView) findViewById(R.id.ingre_name_en);
        name_kr = (TextView) findViewById(R.id.ingre_name_kr);
        close =(ImageView)findViewById(R.id.close);
        close.setOnClickListener(clickListener);
        effect = new ArrayList<>();
        effect1 = (TextView) findViewById(R.id.effect1); effect.add(effect1);
        effect2 = (TextView) findViewById(R.id.effect2); effect.add(effect2);
        effect3 = (TextView) findViewById(R.id.effect3); effect.add(effect3);
        side = new ArrayList<>();
        side1 = (TextView) findViewById(R.id.side1);

        getIngreResult();
    }

    public IngreDialog(Activity activity, String title, View.OnClickListener clickListener, String ingrename) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
        this.clickListener = clickListener;
        this.title = title;
        this.ingrename = ingrename.trim();
        Log.i("MyTag",ingrename);

    }
    public void getIngreResult()
    {
        Call<IngreList> ingreresult = networkService.getIngreResult(ingrename);
        ingreresult.enqueue(new Callback<IngreList>()
        {
            @Override
            public void onResponse(Call<IngreList> call, Response<IngreList> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "다이얼로그 통신 성공");
                    data1 = response.body().result;
                    name_en.setText(data1.ingrelist.ingre_name_en);
                    name_kr.setText(data1.ingrelist.ingre_name_kr);
                    String[] effect1 = data1.ingrelist.effect.split(":");
                    String[] side = data1.ingrelist.side.split(":");
                    for(int i=0; i<effect1.length; i++)
                    {
                        effect.get(i).setText(effect1[i]);
                    }
                    side1.setText(side[0]);
                }
            }
            @Override
            public void onFailure(Call<IngreList> call, Throwable t)
            {
                Log.i("MyTag", "다이얼로그 통신 실패");
            }
        });
    }

}

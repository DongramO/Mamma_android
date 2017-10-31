package momma_beta.momma_bv.product;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;

import momma_beta.momma_bv.Model.ProductDetail;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.Review.ReviewWriteFragment;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import momma_beta.momma_bv.product.Adapter.Adapter_ingreDetail;
import momma_beta.momma_bv.product.Adapter.Adapter_reviewDetail;
import momma_beta.momma_bv.product.Product_model.addbookmark;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetailFragment extends MasterFragment
{
    private NavigationActivity mContext;
    RecyclerView ingrerv, reviewrv;

    NetworkService networkService;
    String milkname;
    ProductDetail.ResultData data;
    Adapter_ingreDetail adapter_ingreDetail;
    Adapter_reviewDetail adapter_reviewDetail;
    LinearLayoutManager linearLayoutManager1,linearLayoutManager2;
    RecyclerView recyclerView1, recyclerView2;
    ImageView img, write_review, bookmark, sharing;
    TextView company, milkname1;
    RatingBar rb;
    ImageView ingre_list;
    int milk_id;
    SharedPreferences auto;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mContext = (NavigationActivity) getMasterActivity();
        FacebookSdk.sdkInitialize(mContext);
        AppEventsLogger.activateApp(mContext);
        View view = inflater.inflate(R.layout.activity_product_detail, container, false);
        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        recyclerView1 = (RecyclerView)view.findViewById(R.id.ingre_rv);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        recyclerView2 = (RecyclerView)view.findViewById(R.id.review_rv);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(linearLayoutManager2);

        img = (ImageView)view.findViewById(R.id.detail_image);
        company = (TextView)view.findViewById(R.id.detail_company_txt);
        milkname1 = (TextView)view.findViewById(R.id.detail_milkname_txt);
        rb = (RatingBar) view.findViewById(R.id.detail_review_rating);
        ingre_list = (ImageView)view.findViewById(R.id.ingre_list);
        write_review = (ImageView)view.findViewById(R.id.write_review);
        bookmark = (ImageView)view.findViewById(R.id.add_bookmark);
        sharing = (ImageView)view.findViewById(R.id.sharing);
        milkname = getArguments().getString("milkname");
        milk_id = getArguments().getInt("id");

        Log.i("MyTag", milkname+"");

        ingre_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment ingreListFragment = new IngreListFragment();
                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putString("milkname",milkname); // key , value
                ingreListFragment.setArguments(bundle);
                ReplaceFragement(ingreListFragment);
                mContext.flag=3;
            }

        });
        write_review.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment Reviewwirte = new ReviewWriteFragment();
                Bundle bundle = new Bundle(4); // 파라미터는 전달할 데이터 개수
                bundle.putString("milkname",milkname); // key , value
                bundle.putInt("id",milk_id);
                bundle.putString("milkcom",company.getText().toString());
                bundle.putString("milkimg",data.milk.image);
                Reviewwirte.setArguments(bundle);
                ReplaceFragement(Reviewwirte);
                mContext.flag =3;
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookmark();
            }
        });

        sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
//
//                intent.setType("text/plain");
//
//            // Set default text message
//            // 카톡, 이메일, MMS 다 이걸로 설정 가능
//            //String subject = "문자의 제목";
//                String text = "원하는 텍스트를 입력하세요";
//            //intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//                intent.putExtra(Intent.EXTRA_TEXT, text);
//
//            // Title of intent
//                Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
//                startActivity(chooser);



//                shareKakao();

                shareFacebook();
            }
        });
        getProductDetailInfoResult();

        return view;
    }
    public void ReplaceFragement(Fragment fragment)
    {

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    public void getProductDetailInfoResult()
    {
        Call<ProductDetail> productDetailInfoResult = networkService.getProductDetailInfoResult(milkname);
        productDetailInfoResult.enqueue(new Callback<ProductDetail>()
        {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "product detail success");
                    data = response.body().result;
                    adapter_ingreDetail = new Adapter_ingreDetail(data.ingredient);
                    recyclerView1.setAdapter(adapter_ingreDetail);

                    if(data.review != null)
                    {
                        adapter_reviewDetail = new Adapter_reviewDetail(data.review);
                        recyclerView2.setAdapter(adapter_reviewDetail);
                    }
                    Glide.with(getActivity())
                            .load(data.milk.image)
                            .into(img);
                    company.setText(data.milk.company);
                    milkname1.setText(data.milk.name);
                    rb.setRating(data.milk.grade);

                }
            }
            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t)
            {
                Log.i("MyTag", "product detail fail");
            }
        });
    }

    public void addBookmark()
    {
        addbookmark mark = new addbookmark();
        mark.kind ="product";
        mark.id = milk_id;
        mark.nickname = auto.getString("nickname",null);

        Call<addbookmark> bookmark = networkService.addBookmark(mark);
        bookmark.enqueue(new Callback<addbookmark>()
        {
            @Override
            public void onResponse(Call<addbookmark> call, Response<addbookmark> response)
            {
                if (response.isSuccessful())
                {
                    Log.i("MyTag", "product detail success");
                    Toast.makeText(mContext, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<addbookmark> call, Throwable t)
            {
                Log.i("MyTag", "product detail fail");
                Toast.makeText(mContext, "이미 추가된 제품 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void shareKakao()
    {
        Log.i("mytag","dfdf");
        try
        {
            final KakaoLink kakaoLink = KakaoLink.getKakaoLink(mContext);
            final KakaoTalkLinkMessageBuilder kakaoTalkLinkMessageBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();
            Log.i("mytag","kkk");
            kakaoTalkLinkMessageBuilder.addText("링크 테스트");

            String url = "https://s3.ap-northeast-2.amazonaws.com/milklogo/drymilk/%EC%95%B1%EC%86%94%EB%A3%A8%ED%8A%B8+%ED%94%84%EB%A6%AC%EB%AF%B8%EC%97%84+%EB%AA%85%EC%9E%91.png";
            kakaoTalkLinkMessageBuilder.addImage(url,160,160);

            kakaoTalkLinkMessageBuilder.addAppButton("앱 실행 혹은 다운로드");

            kakaoLink.sendMessage(kakaoTalkLinkMessageBuilder,mContext);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void shareFacebook()
    {
        ShareLinkContent content = new ShareLinkContent.Builder()

        .setContentTitle("페이스북 공유 링크 입니다")
        .setImageUrl(Uri.parse("https://s3.ap-northeast-2.amazonaws.com/milklogo/drymilk/%EC%95%B1%EC%86%94%EB%A3%A8%ED%8A%B8+%ED%94%84%EB%A6%AC%EB%AF%B8%EC%97%84+%EB%AA%85%EC%9E%91.png"))
        .setContentUrl(Uri.parse("www.naver.com"))
        .setContentDescription("문장1, 문장2").build();

        ShareDialog shareDialog = new ShareDialog(mContext);
        shareDialog.show(content);

    }

}

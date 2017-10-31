package momma_beta.momma_bv.Review;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import momma_beta.momma_bv.Model.ReviewWriteInfo;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import momma_beta.momma_bv.product.ProductDetailFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ReviewWriteFragment extends MasterFragment {

    private NavigationActivity mContext;
    NetworkService networkService;
    public EditText title1, good_comment1, bad_comment1, tip1;
    private FrameLayout fl;
    ImageView milk_img;
    TextView com,pro;
    String company;
    RatingBar review_grade1;
    int salt_grade =1;
    int brix_grade =1;
    String milkname;
    String nickname;
    String milkimg;
    int milk_id1, review_id1;
    Button complete;
    public SharedPreferences auto;
    public SharedPreferences.Editor autoLogin;
    String imgUrl = "";
    ImageView myImage;
    Uri data1;
//    TextView milk_name,company;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext =(NavigationActivity) getMasterActivity();
        networkService = ApplicationController.getInstance().getNetworkService();
        auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
        nickname = auto.getString("nickname",null);
        milk_id1 = getArguments().getInt("id");

        mContext = (NavigationActivity) getMasterActivity();
        milkname = getArguments().getString("milkname");
        company = getArguments().getString("milkcom");
        milkimg = getArguments().getString("milkimg");
        View view = inflater.inflate(R.layout.activity_review_write, container, false);

        fl = (FrameLayout)view.findViewById(R.id.reviewimg);
        title1 = (EditText)view.findViewById(R.id.oneline_edit);
        good_comment1 = (EditText)view.findViewById(R.id.goodpoint_edit);
        bad_comment1 = (EditText)view.findViewById(R.id.badpoint_edit);
        tip1 = (EditText)view.findViewById(R.id.honeytip_edit);
        complete = (Button)view.findViewById(R.id.review_write_end_btn);
        review_grade1 = (RatingBar)view.findViewById(R.id.detail_review_rating);
        com = (TextView)view.findViewById(R.id.review_write_com);
        pro = (TextView)view.findViewById(R.id.review_write_pro);
        milk_img = (ImageView)view.findViewById(R.id.review_write_img);
        myImage = (ImageView)view.findViewById(R.id.realimg);
        com.setText(company);
        pro.setText(milkname);
        Glide.with(getActivity())
                .load(milkimg)
                .into(milk_img);
//        milk_name = (TextView)view.findViewById(R.id.favorite_singleline_txt);


       complete.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v) {
               WriteRegist();
               Fragment reviewDetail = new ProductDetailFragment();
               Bundle bundle = new Bundle(2); // 파라미터는 전달할 데이터 개수
               bundle.putString("milkname", milkname); // key , value
               bundle.putInt("id",milk_id1); // key , value
//               bundle.putString("writer",nickname); // key , value
               reviewDetail.setArguments(bundle);
               Toast.makeText(mContext, "리뷰 작성을 완료하였습니다", Toast.LENGTH_SHORT).show();
               mContext.flag = 4;
               ReplaceFragement(reviewDetail);

           }
           public void ReplaceFragement(Fragment fragment)
           {
               FragmentTransaction transaction = getFragmentManager().beginTransaction();
               transaction.replace(R.id.fragment_container, fragment);
               transaction.commit();
           }
       });

       fl.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_PICK);
               intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
               intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(intent, 200);
           }
       });


        return view;
    }

    public void WriteRegist()
    {
        if (title1.length() == 0 || good_comment1.length() == 0 || bad_comment1.length() == 0)
        {
            Toast.makeText(getApplicationContext(), "제목 및 내용을 확인해주세요.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            RequestBody good_comment = RequestBody.create(MediaType.parse("multipart/form-data"), good_comment1.getText().toString());
            RequestBody bad_comment = RequestBody.create(MediaType.parse("multipart/form-data"), bad_comment1.getText().toString());
            RequestBody title = RequestBody.create(MediaType.parse("multipart/form-data"), title1.getText().toString());
            RequestBody tip = RequestBody.create(MediaType.parse("multipart/form-data"), tip1.getText().toString());
            RequestBody review_grade = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(review_grade1.getRating()));
            RequestBody salt_grade = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
            RequestBody brix_grade = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
            RequestBody milk_id = RequestBody.create(MediaType.parse("multipart/form-data"), "1");
            RequestBody milk_name= RequestBody.create(MediaType.parse("multipart/form-data"), milkname);
            RequestBody review_writer = RequestBody.create(MediaType.parse("multipart/form-data"), nickname);
            RequestBody image_flag = RequestBody.create(MediaType.parse("multipart/form-data"), "false");
            MultipartBody.Part body;

            if (imgUrl == "")
            {
                body = null;

            }
            else
            {
                image_flag = RequestBody.create(MediaType.parse("multipart/form-data"), "true");
                BitmapFactory.Options options = new BitmapFactory.Options();
                       options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

                InputStream in = null; // here, you need to get your context.
                try {
                    in = mContext.getContentResolver().openInputStream(data1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                        /*inputstream 형태로 받은 이미지로 부터 비트맵을 만들어 바이트 단위로 압축
                        그이우 스트림 배열에 담아서 전송합니다.
                         */

                Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);

                // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림
                RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

                File photo = new File(imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다

                // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
                body = MultipartBody.Part.createFormData("image", photo.getName(), photoBody);

            }

        Call<ReviewWriteInfo> getWriteResult = networkService.getWriteResult(body, good_comment, bad_comment,title,tip,review_grade,salt_grade,brix_grade,milk_id,milk_name,review_writer,image_flag,milkname,nickname);
        getWriteResult.enqueue(new Callback<ReviewWriteInfo>()
        {
            @Override
            public void onResponse(Call<ReviewWriteInfo> call, Response<ReviewWriteInfo> response)
            {
                Log.i("MyTag", response.message());
                Log.i("MyTag", "response code : " + response.code());

                if (response.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "리뷰 등록 완료",Toast.LENGTH_LONG).show();
                }
                else
                {
                    int statusCode = response.code();
                    Log.i("MyTag", "응답코드 : " + statusCode);
                }
            }

            @Override
            public void onFailure(Call<ReviewWriteInfo> call, Throwable t)
            {
                Log.i("MyTag", "에러내용 : " + t.getMessage());
            }
        });
        }
    }
        // 선택된 이미지 가져오기
        public void onActivityResult(int requestCode, int resultCode, Intent data)
        {

            if (requestCode == 200)
            {
                if (resultCode == Activity.RESULT_OK)
                {
                    try {
                        //Uri에서 이미지 이름을 얻어온다.
                        String name_Str = getImageNameToUri(data.getData());
                        this.data1 = data.getData();
                        Bitmap bitmap=BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(data1));
                        myImage.setImageBitmap(bitmap);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    imgUrl = "";

                }
            }
        }

    // 선택된 이미지 파일명 가져오기
    public String getImageNameToUri(Uri data)
    {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        imgUrl = imgPath;

        //불러온 사진을 이미지 뷰에 장착!
        File imgFile = new  File(imgUrl);
        Log.i("mytag","리뷰 사진불러오기"+imgFile.getAbsolutePath());

        return imgName;
    }


}

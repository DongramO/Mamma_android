package momma_beta.momma_bv.MyPage;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import momma_beta.momma_bv.Model.MypageInfo;
import momma_beta.momma_bv.MyPage.Model.MyImage;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.application.ApplicationController;
import momma_beta.momma_bv.navigation_main.MasterFragment;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.network.NetworkService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MyPageFragment extends MasterFragment implements View.OnClickListener {

	private NavigationActivity mContext;
	private RelativeLayout mypage_brand_layout, mypage_product_layout, mypage_review_layout;
	private Button myreview_btn, mypage_edit_btn;
	private TextView pname, bname, nickname1, birth,worry1, worry2, worry3, brand, review, product, worry4,worry5,worry6,worry7,worry8;
	String imgUrl = "";
	Uri Uridata;
	CircleImageView p_img,b_img;
	NetworkService networkService;
	String nickname;
	String image;
	MypageInfo.ResultData data;
	final int REQ_CODE_SELECT_IMAGE = 100;
	String kind="";
	MultipartBody.Part body;
	SharedPreferences auto;
	SharedPreferences.Editor autoLogin;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContext = (NavigationActivity) getMasterActivity();
		View view = inflater.inflate(R.layout.activity_mypage, container, false);
		networkService = ApplicationController.getInstance().getNetworkService();
		auto = this.getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
		nickname = auto.getString("nickname", null);
		Log.i("mytag",nickname);
		image = auto.getString("profile", null);
		mypage_brand_layout = (RelativeLayout)view.findViewById(R.id.mypage_brand_layout);
		mypage_product_layout = (RelativeLayout)view.findViewById(R.id.mypage_product_layout);
		mypage_review_layout = (RelativeLayout)view.findViewById(R.id.mypage_review_layout);
		myreview_btn = (Button)view.findViewById(R.id.myreview_btn);
		mypage_edit_btn = (Button)view.findViewById(R.id.mypage_edit_btn);


		mypage_brand_layout.setOnClickListener(this);
		mypage_product_layout.setOnClickListener(this);
		mypage_review_layout.setOnClickListener(this);
		myreview_btn.setOnClickListener(this);
		mypage_edit_btn.setOnClickListener(this);


		pname = (TextView)view.findViewById(R.id.mypage_pname);
		bname = (TextView)view.findViewById(R.id.mypage_bname);
		nickname1= (TextView)view.findViewById(R.id.mypage_pnickname);
		birth = (TextView)view.findViewById(R.id.mypage_bbirth);
		worry1 = (TextView)view.findViewById(R.id.worry1);
		worry2 = (TextView)view.findViewById(R.id.worry2);
		worry3 = (TextView)view.findViewById(R.id.worry3);
        worry4 = (TextView)view.findViewById(R.id.worry4);
        worry5 = (TextView)view.findViewById(R.id.worry5);
        worry6 = (TextView)view.findViewById(R.id.worry6);
        worry7 = (TextView)view.findViewById(R.id.worry7);
        worry8 = (TextView)view.findViewById(R.id.worry8);
		brand = (TextView)view.findViewById(R.id.brand_count);
		product = (TextView)view.findViewById(R.id.product_count);
		review = (TextView)view.findViewById(R.id.review_count);
		p_img = (CircleImageView) view.findViewById(R.id.p_img);
		b_img = (CircleImageView) view.findViewById(R.id.b_img);

		p_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				kind ="parent";
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
			}
		});

		b_img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v)
			{
				kind="baby";
				Intent intent = new Intent(Intent.ACTION_PICK);
				intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
				intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
			}
		});

		nickname1.setText(nickname);
		getMyPageResult();

		return view;
	}


	public void getMyPageResult()
	{
		auto = this.getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
		nickname =  auto.getString("nickname",null);
		Call<MypageInfo> mypageresult = networkService.getMypageResult(nickname);
		mypageresult.enqueue(new Callback<MypageInfo>()
		{
			@Override
			public void onResponse(Call<MypageInfo> call, Response<MypageInfo> response)
			{
				if (response.isSuccessful())
				{
					data = response.body().result;
					pname.setText(data.myinfo.p_name);
					bname.setText(data.myinfo.b_name);
					birth.setText(data.age+"개월");
                    String strColor = "#4FBCBA";
					for(int i=0; i<data.worry.size();i++)
                    {
                        if(data.worry.get(i).worry !=null)
                        {
                            if(data.worry.get(i).worry.contains("알레르기"))
                                worry1.setTextColor(Color.parseColor(strColor));
                            if(data.worry.get(i).worry.contains("아토피"))
                                worry2.setTextColor(Color.parseColor(strColor));
                            if(data.worry.get(i).worry.contains("소화불량"))
                                worry3.setTextColor(Color.parseColor(strColor));
                            if(data.worry.get(i).worry.contains("변비"))
                                worry4.setTextColor(Color.parseColor(strColor));
                            if(data.worry.get(i).worry.contains("설사"))
                                worry5.setTextColor(Color.parseColor(strColor));
                            if(data.worry.get(i).worry.contains("성장부진"))
                                worry6.setTextColor(Color.parseColor(strColor));
                            if(data.worry.get(i).worry.contains("피부발진"))
                                worry7.setTextColor(Color.parseColor(strColor));
                            if(data.worry.get(i).worry.contains("과다체중"))
                                worry8.setTextColor(Color.parseColor(strColor));
                        }
                    }
					brand.setText(String.valueOf(data.bookmark.company));
					product.setText(String.valueOf(data.bookmark.product));
					review.setText(String.valueOf(data.bookmark.review));
					if(data.myinfo.p_image != null)
					{
						Glide.with(getApplicationContext())
								.load(data.myinfo.p_image)
								.into(p_img);
					}
					if(data.myinfo.b_image != null) {
						Glide.with(getApplicationContext())
								.load(data.myinfo.b_image)
								.into(b_img);
					}
					autoLogin = auto.edit();
					autoLogin.putString("profile", data.myinfo.p_image);
					autoLogin.commit();
				}
			}
			@Override
			public void onFailure(Call<MypageInfo> call, Throwable t)
			{
				Log.i("MyTag", "mypage info fail~");
			}
		});
	}
	@Override
	public void onClick(View view)
	{
		switch (view.getId()){
			case R.id.mypage_brand_layout:
				mContext.changeFragment(16,null);
				break;
			case R.id.mypage_product_layout:
				mContext.changeFragment(8,null);
				break;
			case R.id.mypage_review_layout:
				mContext.changeFragment(9,null);
				break;
			case R.id.myreview_btn:
				mContext.changeFragment(10,null);
				break;
			case R.id.mypage_edit_btn:
				mContext.changeFragment(11,null);
				break;
		}
	}

	// 선택된 이미지 가져오기
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQ_CODE_SELECT_IMAGE)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				try
				{
					//Uri에서 이미지 이름을 얻어온다.
					String name_Str = getImageNameToUri(data.getData());
					this.Uridata = data.getData();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				imgUrl = "";
			}
			ImageSet();
			getImage();

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

		File imgFile = new  File(imgUrl);

		if(imgFile.exists() && kind.equals("baby"))
		{
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			b_img.setImageBitmap(myBitmap);
		}

		if(imgFile.exists() && kind.equals("parent"))
		{
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			p_img.setImageBitmap(myBitmap);
		}

		imgUrl = imgPath;
		return imgName;
	}

	public void ImageSet()
	{

		if (imgUrl == "")
		{
			body = null;
		}
		else
		{
			/*
			이미지를 리사이징하는 부분입니다.
			리사이징하는 이유!! 안드로이드는 메모리에 민감하다고 세미나에서 말씀드렸습니다~
			구글에서는 최소 16MByte로 정하고 있으나, 제조사 별로 또 디바이스별로 메모리의 크기는 다릅니다.
			또한, 이미지를 서버에 업로드할 때 이미지의 크기가 크다면 시간이 오래 걸리고 데이터 소모가 큽니다!!
			*/

			BitmapFactory.Options options = new BitmapFactory.Options();
//                       options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

			InputStream in = null; // here, you need to get your context.
			try {
				in = mContext.getContentResolver().openInputStream(Uridata);
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

	}
	public void getImage()
	{
		auto = this.getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
		nickname =  auto.getString("nickname",null);
		Call<MyImage> mypageresult = networkService.getImage(body, nickname, kind);
		mypageresult.enqueue(new Callback<MyImage>()
		{
			@Override
			public void onResponse(Call<MyImage> call, Response<MyImage> response)
			{
				if (response.isSuccessful())
				{
					Log.i("mytag","이미지 삽입 성공");
					getMyPageResult();
				}
			}
			@Override
			public void onFailure(Call<MyImage> call, Throwable t)
			{
				Log.i("MyTag", "mypage info fail~");
			}
		});
	}

}

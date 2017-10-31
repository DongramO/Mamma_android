package momma_beta.momma_bv.MyPage.Mypage_Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.MyBookMarkInfo;
import momma_beta.momma_bv.R;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class Adapter_myBrand extends RecyclerView.Adapter<myBrandViewHolder>
{
    ArrayList<MyBookMarkInfo> brandlist;
    Context mcontext;
//    View.OnClickListener clickListener;

    public Adapter_myBrand(ArrayList<MyBookMarkInfo> brandlist, Context mcontext) {
        this.brandlist = brandlist;
        this.mcontext = mcontext;
    }

    // 뷰홀더 객체 생성
    @Override
    public myBrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_brand_listitem, parent, false);
        myBrandViewHolder BrandViewHolder = new myBrandViewHolder(view); // 뷰홀더를 통해 메모리 할당
        return BrandViewHolder;
    }

    // 어떤 객체를 바인딩 할지 설정
    @Override
    public void onBindViewHolder(myBrandViewHolder holder, int position)
    {
        Log.i("MyTag", brandlist.get(position).image+"brand image bookmark list");
//        holder.img.setImageResource(R.drawable.bookmark_image);
        Glide.with(mcontext)
                .load(brandlist.get(position).image)
                .into(holder.img);
    }

    @Override
    public int getItemCount()
    {
        return brandlist !=null ? brandlist.size():0;
    }
}

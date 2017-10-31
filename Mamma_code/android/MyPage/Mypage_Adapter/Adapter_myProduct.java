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

public class Adapter_myProduct extends RecyclerView.Adapter<myProductViewHolder>
{
    ArrayList<MyBookMarkInfo> productlist;
    Context mContext;
//    View.OnClickListener clickListener;

    public Adapter_myProduct(ArrayList<MyBookMarkInfo> productlist, Context mContext)
    {
        this.productlist = productlist;
        this.mContext = mContext;
    }

    // 뷰홀더 객체 생성
    @Override
    public myProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_product_listitem, parent, false);
        myProductViewHolder productViewHolder = new myProductViewHolder(view); // 뷰홀더를 통해 메모리 할당
//        view.setOnClickListener(clickListener);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(myProductViewHolder holder, int position)
    {
        Log.i("MyTag", productlist.get(position).image+"");
        holder.tv.setText(productlist.get(position).name.toString());
        holder.rb.setRating(productlist.get(position).grade);
        Glide.with(mContext)
            .load(productlist.get(position).image)
            .into(holder.img);
    }


    @Override
    public int getItemCount()
    {
        return productlist !=null ? productlist.size():0;
    }
}

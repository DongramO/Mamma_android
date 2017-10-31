package momma_beta.momma_bv.product.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.DetailReviewInfo;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.NavigationActivity;


/**
 * Created by idongsu on 2017. 5. 12..
 */

public class Adapter_reviewDetail extends RecyclerView.Adapter<reviewViewHolder>
{
    ArrayList<DetailReviewInfo> reviewlist;
    NavigationActivity mContext;
//    View.OnClickListener clickListener;

    public Adapter_reviewDetail(ArrayList<DetailReviewInfo> reviewlist)
    {
        this.reviewlist = reviewlist;
    }

    // 뷰홀더 객체 생성
    @Override
    public reviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_detail_reveiw, parent, false);
        reviewViewHolder reviewViewHolder = new reviewViewHolder(view); // 뷰홀더를 통해 메모리 할당
//        view.setOnClickListener(clickListener);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(reviewViewHolder holder, int position)
    {
        holder.tv.setText(reviewlist.get(position).title);
        holder.rb.setRating(reviewlist.get(position).review_grade);
    }



    @Override
    public int getItemCount()
    {
        return reviewlist !=null ? reviewlist.size():0;
    }
}

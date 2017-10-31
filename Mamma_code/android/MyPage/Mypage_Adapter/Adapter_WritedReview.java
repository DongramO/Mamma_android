package momma_beta.momma_bv.MyPage.Mypage_Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.MyReviewInfo;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.NavigationActivity;



/**
 * Created by idongsu on 2017. 5. 12..
 */

public class Adapter_WritedReview extends RecyclerView.Adapter<myReviewViewHolder>
{
    ArrayList<MyReviewInfo> reviewlist;
    NavigationActivity mContext;
//    View.OnClickListener clickListener;

    public Adapter_WritedReview(ArrayList<MyReviewInfo> reviewlist, NavigationActivity mContext)
    {
        this.reviewlist = reviewlist;
        this.mContext = mContext;
    }

    // 뷰홀더 객체 생성
    @Override
    public myReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_review_listitem, parent, false);
        myReviewViewHolder reviewViewHolder = new myReviewViewHolder(view); // 뷰홀더를 통해 메모리 할당
//        view.setOnClickListener(clickListener);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(myReviewViewHolder holder, int position)
    {
        holder.tv.setText(reviewlist.get(position).review_writer); //review nick
        holder.rb.setRating(reviewlist.get(position).review_grade); // review_grade
        holder.tv1.setText(reviewlist.get(position).title); // review title
        if(reviewlist.get(position).review_image != null)
            Glide.with(mContext)
                    .load(reviewlist.get(position).review_image)
                    .into(holder.img);

    }

    @Override
    public int getItemCount()
    {
        return reviewlist !=null ? reviewlist.size():0;
    }
}

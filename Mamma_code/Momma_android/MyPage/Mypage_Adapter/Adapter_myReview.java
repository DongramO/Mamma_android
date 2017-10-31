package momma_beta.momma_bv.MyPage.Mypage_Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.MyBookMarkInfo;
import momma_beta.momma_bv.R;



/**
 * Created by idongsu on 2017. 5. 12..
 */

public class Adapter_myReview extends RecyclerView.Adapter<myReviewViewHolder>
{
    ArrayList<MyBookMarkInfo> reviewlist;
    Context mContext;
//    View.OnClickListener clickListener;

    public Adapter_myReview(ArrayList<MyBookMarkInfo> reviewlist, Context mContext)
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
        holder.tv.setText(reviewlist.get(position).name.toString());
        Log.i("Mytag",reviewlist.get(position).grade+"");
        holder.rb.setRating(reviewlist.get(position).grade);
        holder.tv1.setText(reviewlist.get(position).title) ;

    }



    @Override
    public int getItemCount()
    {
        return reviewlist !=null ? reviewlist.size():0;
    }
}

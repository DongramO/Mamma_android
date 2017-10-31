package momma_beta.momma_bv.MyPage.Mypage_Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import momma_beta.momma_bv.R;


/**
 * Created by idongsu on 2017. 5. 12..
 */

public class myReviewViewHolder extends RecyclerView.ViewHolder
{
    ImageView img;
    TextView tv, tv1;
    RatingBar rb;
    public myReviewViewHolder(View itemView)
    {
        super(itemView);
        img = (ImageView)itemView.findViewById(R.id.favorite_review_img);
        tv = (TextView)itemView.findViewById(R.id.favorite_reviewnick_txt);
        tv1 = (TextView)itemView.findViewById(R.id.favorite_singleline_txt);
        rb = (RatingBar)itemView.findViewById(R.id.favorite_review_rating);
    }

}

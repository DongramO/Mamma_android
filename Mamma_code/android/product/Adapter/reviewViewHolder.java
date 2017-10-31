package momma_beta.momma_bv.product.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import momma_beta.momma_bv.R;


/**
 * Created by idongsu on 2017. 5. 12..
 */

public class reviewViewHolder extends RecyclerView.ViewHolder
{

    TextView tv;
    RatingBar rb;
    public reviewViewHolder(View itemView)
    {
        super(itemView);
        tv = (TextView)itemView.findViewById(R.id.detail_review_title);
        rb = (RatingBar)itemView.findViewById(R.id.detail_review_rating);
    }

}

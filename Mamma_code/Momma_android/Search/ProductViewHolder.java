package momma_beta.momma_bv.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import momma_beta.momma_bv.R;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class ProductViewHolder extends RecyclerView.ViewHolder
{

    TextView tv,tv2;
    ImageView img;
    RatingBar rt;

    public ProductViewHolder(View itemView)
    {
        super(itemView);
        tv = (TextView)itemView.findViewById(R.id.search_result_name);
        img = (ImageView)itemView.findViewById(R.id.search_result_img);
        rt = (RatingBar)itemView.findViewById(R.id.search_result_grade);
    }

}

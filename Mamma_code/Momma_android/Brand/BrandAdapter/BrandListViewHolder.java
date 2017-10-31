package momma_beta.momma_bv.Brand.BrandAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import momma_beta.momma_bv.R;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class BrandListViewHolder extends RecyclerView.ViewHolder
{

    TextView tv;
    ImageView img;
    RatingBar rb;
    public BrandListViewHolder(View itemView)
    {
        super(itemView);
        tv = (TextView)itemView.findViewById(R.id.brand_list_name);
        img = (ImageView)itemView.findViewById(R.id.brand_list_image);
        rb = (RatingBar) itemView.findViewById(R.id.brand_list_grade);
    }

}

package momma_beta.momma_bv.MyPage.Mypage_Adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import momma_beta.momma_bv.R;


/**
 * Created by idongsu on 2017. 5. 12..
 */

public class myBrandViewHolder extends RecyclerView.ViewHolder
{
    ImageView img;

    public myBrandViewHolder(View itemView)
    {
        super(itemView);
        img = (ImageView)itemView.findViewById(R.id.favorite_brand_img);
    }

}

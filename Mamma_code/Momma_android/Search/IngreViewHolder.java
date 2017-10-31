package momma_beta.momma_bv.Search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import momma_beta.momma_bv.R;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class IngreViewHolder extends RecyclerView.ViewHolder
{

    TextView tv,tv2;

    public IngreViewHolder(View itemView)
    {
        super(itemView);
        tv = (TextView)itemView.findViewById(R.id.ingredient_txt1);
//        tv2 = (TextView)itemView.findViewById(R.id.ingredient_txt2);
    }

}

package momma_beta.momma_bv.product.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import momma_beta.momma_bv.R;


/**
 * Created by idongsu on 2017. 5. 12..
 */

public class IngreViewHolder extends RecyclerView.ViewHolder
{

    TextView tv, tv1, tv2;

    public IngreViewHolder(View itemView)
    {
        super(itemView);

        tv = (TextView)itemView.findViewById(R.id.ingre_name_kr);
        tv1 = (TextView)itemView.findViewById(R.id.ingre_amount);
        tv2 = (TextView)itemView.findViewById(R.id.ingre_unit);
    }

}

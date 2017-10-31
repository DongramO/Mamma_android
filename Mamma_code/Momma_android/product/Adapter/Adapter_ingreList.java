package momma_beta.momma_bv.product.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.DetailIngredient;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.navigation_main.NavigationActivity;
import momma_beta.momma_bv.product.Adapter.IngreViewHolder;


/**
 * Created by idongsu on 2017. 5. 12..
 */

public class Adapter_ingreList extends RecyclerView.Adapter<IngreViewHolder>
{
    ArrayList<DetailIngredient> ingrelist;
    NavigationActivity mContext;
    View.OnClickListener clickListener;

    public Adapter_ingreList(ArrayList<DetailIngredient> ingrelist, View.OnClickListener clickListener)
    {
        this.ingrelist = ingrelist;
        this.clickListener = clickListener;
    }

    // 뷰홀더 객체 생성
    @Override
    public IngreViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingre_detail_list, parent, false);
        IngreViewHolder ingreviewholder = new IngreViewHolder(view); // 뷰홀더를 통해 메모리 할당
        view.setOnClickListener(clickListener);
        return ingreviewholder;
    }

    @Override
    public void onBindViewHolder(IngreViewHolder holder, int position)
    {
        holder.tv.setText(ingrelist.get(position).ingre_name);
        holder.tv1.setText(String.valueOf(ingrelist.get(position).content));
        holder.tv2.setText(ingrelist.get(position).content_unit);
    }



    @Override
    public int getItemCount()
    {
        return ingrelist !=null ? ingrelist.size():0;
    }
}

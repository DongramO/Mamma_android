package momma_beta.momma_bv.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.IngreInfo;
import momma_beta.momma_bv.R;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class IngreAdapter extends RecyclerView.Adapter<IngreViewHolder>
{
    ArrayList<IngreInfo> ingrelist;
    View.OnClickListener clickListener;

    public IngreAdapter(ArrayList<IngreInfo> ingrelist, View.OnClickListener clickListener)
    {
        this.ingrelist = ingrelist;
        this.clickListener = clickListener;
    }

    // 뷰홀더 객체 생성
    @Override
    public IngreViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_ingredientitem, parent, false);
        IngreViewHolder ingreViewHolder = new IngreViewHolder(view); // 뷰홀더를 통해 메모리 할당
        view.setOnClickListener(clickListener);
        return ingreViewHolder;
    }

    // 어떤 객체를 바인딩 할지 설정
    @Override
    public void onBindViewHolder(IngreViewHolder holder, int position)
    {

        holder.tv.setText(ingrelist.get(position).ingre_name_kr);
    }

    @Override
    public int getItemCount()
    {
        return ingrelist !=null ? ingrelist.size():0;
    }
}

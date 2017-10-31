package momma_beta.momma_bv.Search;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.ManuInfo;
import momma_beta.momma_bv.R;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class BrandAdapter extends RecyclerView.Adapter<BrandViewHolder>
{
    ArrayList<ManuInfo> manulist;
    View.OnClickListener clickListener;

    public BrandAdapter(ArrayList<ManuInfo> manulist, View.OnClickListener clickListener) {
        this.manulist = manulist;
        this.clickListener = clickListener;
    }

    // 뷰홀더 객체 생성
    @Override
    public BrandViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_branditem, parent, false);
        BrandViewHolder BrandViewHolder = new BrandViewHolder(view); // 뷰홀더를 통해 메모리 할당
        view.setOnClickListener(clickListener);
        return BrandViewHolder;
    }

    // 어떤 객체를 바인딩 할지 설정
    @Override
    public void onBindViewHolder(BrandViewHolder holder, int position)
    {

        holder.tv.setText(manulist.get(position).manu_name);
    }

    @Override
    public int getItemCount()
    {
        return manulist !=null ? manulist.size():0;
    }
}

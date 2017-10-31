package momma_beta.momma_bv.Brand.BrandAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.BrandListDryMilk;
import momma_beta.momma_bv.R;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class BrandListAdapter extends RecyclerView.Adapter<BrandListViewHolder>
{
    ArrayList<BrandListDryMilk> brandlist;
    View.OnClickListener clickListener;

    public BrandListAdapter(ArrayList<BrandListDryMilk> brandlist, View.OnClickListener clickListener) {
        this.brandlist = brandlist;
        this.clickListener = clickListener;
    }

    // 뷰홀더 객체 생성
    @Override
    public BrandListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.brand_list_result, parent, false);
        BrandListViewHolder BrandListViewHolder = new BrandListViewHolder(view); // 뷰홀더를 통해 메모리 할당
        view.setOnClickListener(clickListener);
        return BrandListViewHolder;
    }

    @Override
    public void onBindViewHolder(BrandListViewHolder holder, int position)
    {
        String[] array = brandlist.get(position).milk_name.split(" ");
        String modi_name="";

        for(int i=0; i<array.length; i++)
        {
            modi_name += array[i]+"\n";
        }
        holder.tv.setText(modi_name);
        holder.rb.setRating(Float.valueOf(brandlist.get(position).milk_grade));
        Glide.with(holder.img.getContext())
                .load(brandlist.get(position).milk_image)
                .into(holder.img);
    }

    // 어떤 객체를 바인딩 할지 설정

    @Override
    public int getItemCount()
    {
        return brandlist !=null ? brandlist.size():0;
    }
}

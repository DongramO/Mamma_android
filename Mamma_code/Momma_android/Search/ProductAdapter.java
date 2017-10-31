package momma_beta.momma_bv.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.MilkInfo;
import momma_beta.momma_bv.R;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>
{
    ArrayList<MilkInfo> milklist;
    View.OnClickListener clickListener;
    ProductViewHolder productViewHolder;
    Context context;

    public ProductAdapter(ArrayList<MilkInfo> milklist, View.OnClickListener clickListener, Context context)
    {
        this.milklist = milklist;
        this.clickListener = clickListener;
        this.context = context;
    }

    // 뷰홀더 객체 생성
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_product, parent, false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view); // 뷰홀더를 통해 메모리 할당
        view.setOnClickListener(clickListener);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position)
    {
        String[] array = milklist.get(position).milk_name.split(" ");
        String modi_name="";

        for(int i=0; i<array.length; i++)
        {
            modi_name += array[i]+"\n";
        }
        holder.tv.setText(modi_name);
        holder.rt.setRating(Float.valueOf(milklist.get(position).milk_grade));
        Glide.with(holder.img.getContext())
                .load(milklist.get(position).milk_image)
                .into(holder.img);

    }

    @Override
    public int getItemCount()
    {
        return milklist !=null ? 5:0;
    }
}

package momma_beta.momma_bv.product.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import momma_beta.momma_bv.MyPage.Mypage_Adapter.Branditem;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.ViewHolder.MyViewHolder;


public class AdapterProductFiltering extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {
    private ArrayList<Branditem> branditems;
    private int layoutid;
    public static int TYPE_HEADER = 0;
    public static int TYPE_ITEM = 1;
    public static int TYPE_FOOTER;
    public Context mcontext;
    ImageView click;

    public AdapterProductFiltering(ArrayList<Branditem> branditems, Context context) {
        this.branditems = branditems;
        this.mcontext = context;

        TYPE_FOOTER = branditems.size() + 1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == TYPE_HEADER)
        {
            layoutid = R.layout.activity_product_filtering_header;
        }
        else if (viewType == TYPE_ITEM)
        {
            layoutid = R.layout.favorite_brand_listitem;
        }
        else if (viewType == TYPE_FOOTER)
        {
            layoutid = R.layout.activity_product_filtering_footer;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutid, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view, viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        if (holder.holderid == TYPE_HEADER)
        {

        }
        else if(holder.holderid == TYPE_ITEM)
        {

        }
        else if(holder.holderid == TYPE_FOOTER)
        {
            holder.product_filtering_end_btn.setOnClickListener(this);
        }
    }

    @Override
    public int getItemCount() {
        return branditems != null ? branditems.size()+2 : 0;
    }

    private boolean isPositionHeader(int position)
    {
        return position == TYPE_HEADER;
    }

    private boolean isPositionFooter(int position) {
        return position == TYPE_FOOTER;
    }


    @Override
    public int getItemViewType(int position)
    {
        if (isPositionHeader(position))
        {
            return TYPE_HEADER;
        }
        else if(isPositionFooter(position))
        {
            return TYPE_FOOTER;
        }
        else
        {
            return TYPE_ITEM;
        }

    }


    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.product_click_layout:
                break;
            case R.id.product_review_layout:

                break;
            case R.id.product_grade_layout:
                Toast.makeText(mcontext, "별점 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_sugar_layout:
                Toast.makeText(mcontext, "당도 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.product_salt_layout:
                Toast.makeText(mcontext, "염도 클릭", Toast.LENGTH_SHORT).show();
                break;
            case R.id.f_product_end_btn:
                Toast.makeText(mcontext, "완료 클릭", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

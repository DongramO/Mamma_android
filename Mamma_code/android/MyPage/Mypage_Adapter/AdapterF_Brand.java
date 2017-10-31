package momma_beta.momma_bv.MyPage.Mypage_Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import momma_beta.momma_bv.Model.MyBookMarkInfo;
import momma_beta.momma_bv.R;
import momma_beta.momma_bv.ViewHolder.MyViewHolder;


public class AdapterF_Brand extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Branditem> branditems;
    private int layoutid;
    public static int TYPE_HEADER = 0;
    public static int TYPE_ITEM = 1;
    ArrayList<MyBookMarkInfo> bookmark;
//    ArrayList<MyBookMarkInfo> bookmark
    public AdapterF_Brand(ArrayList<Branditem> branditems)
    {
        this.branditems = branditems;
        this.bookmark = bookmark;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if(viewType == TYPE_HEADER) {
            layoutid = R.layout.favorite_brand_header;
        }
        else if(viewType == TYPE_ITEM){
            layoutid = R.layout.favorite_brand_listitem;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutid,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        if(holder.holderid == TYPE_HEADER)
        {
//            holder.brand_header_txt.setText("브랜드");
//            holder.brand_header_img.setImageResource(R.drawable.bookmark_star);
        }
        else if(holder.holderid == TYPE_ITEM)
        {
            Glide.with(holder.brandimg.getContext())
                    .load(branditems.get(0).brandimg)
                    .into(holder.brandimg);
        }
    }

    @Override
    public int getItemCount() {
        return branditems != null ? branditems.size()+1 : 0;
    }

    private boolean isPositionHeader(int position) {
        return position == TYPE_HEADER;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        else {
           return TYPE_ITEM;
        }
    }
}

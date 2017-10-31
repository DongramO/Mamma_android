package momma_beta.momma_bv.MyPage.Mypage_Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.ViewHolder.MyViewHolder;


public class AdapterF_Review extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Reviewitem> reviewitems;
    private int layoutid;
    public static int TYPE_HEADER = 0;
    public static int TYPE_ITEM = 1;


    public AdapterF_Review(ArrayList<Reviewitem> reviewitems) {
        this.reviewitems = reviewitems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER) {
            layoutid = R.layout.favorite_review_header;
        }
        else if(viewType == TYPE_ITEM){
            layoutid = R.layout.favorite_review_listitem;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutid,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(holder.holderid == TYPE_HEADER){
        }
        else if(holder.holderid == TYPE_ITEM){
            holder.reviewimg.setImageResource(reviewitems.get(position-1).reviewimg);
            holder.reviewnicktxt.setText(reviewitems.get(position-1).reviewnicknametxt);
            holder.singleline.setText(reviewitems.get(position-1).reviewtxt);
            holder.reviewrating.setRating(reviewitems.get(position-1).reviewrating);
        }
        else if(position == reviewitems.size()+1){
            holder.productbaseline.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return reviewitems != null ? reviewitems.size()+1 : 0;
    }

    private boolean isPositionHeader(int position) {
        return position == TYPE_HEADER;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        else {
           return TYPE_ITEM;
        }
    }
}

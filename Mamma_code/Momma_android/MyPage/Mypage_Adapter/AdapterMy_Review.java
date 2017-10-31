package momma_beta.momma_bv.MyPage.Mypage_Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.ViewHolder.MyViewHolder;


public class AdapterMy_Review extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Reviewitem> reviewitems;
    private int layoutid;


    public AdapterMy_Review(ArrayList<Reviewitem> reviewitems) {
        this.reviewitems = reviewitems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        layoutid = R.layout.favorite_review_listitem;
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutid,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, 1);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(position == reviewitems.size()){
            holder.productbaseline.setVisibility(View.INVISIBLE);
        }
            holder.reviewimg.setImageResource(reviewitems.get(position).reviewimg);
            holder.reviewnicktxt.setText(reviewitems.get(position).reviewnicknametxt);
            holder.singleline.setText(reviewitems.get(position).reviewtxt);
            holder.reviewrating.setRating(reviewitems.get(position).reviewrating);


    }

    @Override
    public int getItemCount() {
        return reviewitems != null ? reviewitems.size() : 0;
    }


}

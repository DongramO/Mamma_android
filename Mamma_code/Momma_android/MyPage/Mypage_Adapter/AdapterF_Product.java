package momma_beta.momma_bv.MyPage.Mypage_Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import momma_beta.momma_bv.ViewHolder.MyViewHolder;


public class AdapterF_Product extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<Productitem> productitems;
    private int layoutid;
    public static int TYPE_HEADER = 0;
    public static int TYPE_ITEM = 1;


    public AdapterF_Product(ArrayList<Productitem> productitems) {
        this.productitems = productitems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutid,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view, viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return productitems != null ? productitems.size()+1 : 0;
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

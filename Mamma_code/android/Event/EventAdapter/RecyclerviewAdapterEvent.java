package momma_beta.momma_bv.Event.EventAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import momma_beta.momma_bv.Parenting.Itemdata_parenting;
import momma_beta.momma_bv.R;


public class RecyclerviewAdapterEvent extends RecyclerView.Adapter<RecyclerviewAdapterEvent.ViewHolder> {
    private View.OnClickListener clickListener;
    private ArrayList<Itemdata_parenting> Itemdata_parenting;
    private Context context;
    private ArrayList<String> index = new ArrayList<>();
    public View view;

    public RecyclerviewAdapterEvent(ArrayList<Itemdata_parenting> Itemdata_parentings, Context context) {
        this.Itemdata_parenting = Itemdata_parentings;
        this.context = context;
    }


    @Override
    public RecyclerviewAdapterEvent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parenting_listitem, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerviewAdapterEvent.ViewHolder holder, int position) {
        holder.parenting_img.setBackgroundResource(Itemdata_parenting.get(position).parenting_img);
        holder.test_text.setText(Itemdata_parenting.get(position).test_text);

    }

    @Override
    public int getItemCount() {
        return (Itemdata_parenting != null) ? 5 : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView parenting_img;
        public TextView test_text;

        public ViewHolder(View itemView) {
            super(itemView);
            parenting_img = (ImageView)itemView.findViewById(R.id.parenting_info);
            test_text = (TextView)itemView.findViewById(R.id.test_text);

        }
    }
}

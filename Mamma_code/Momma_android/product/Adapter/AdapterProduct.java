package momma_beta.momma_bv.product.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import momma_beta.momma_bv.R;
import momma_beta.momma_bv.product.Itemdata_product;


public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {
    private View.OnClickListener clickListener;
    private ArrayList<Itemdata_product> Itemdata_products;
    private Context context;
    private ArrayList<String> index = new ArrayList<>();
    public View view;
    public String array[];
    public AdapterProduct(ArrayList<Itemdata_product> Itemdata_products, Context context, View.OnClickListener clickListener) {
        this.Itemdata_products = Itemdata_products;
        this.context = context;
        this.clickListener = clickListener;
        for(int i = 1; i<=this.Itemdata_products.size()+1; i++)
        {
            index.add(""+i);
        }
    }


    @Override
    public AdapterProduct.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_rank, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(clickListener);
//        if(view ==null)
//        {
//            viewHolder = new AdapterProduct.ViewHolder(view);
//            view.setTag(viewHolder);
//        }
//        else
//        {
//            viewHolder = (AdapterProduct.ViewHolder)view.getTag();
//        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterProduct.ViewHolder holder, int position) {

        if(position == 0)
        {
            holder.num1_text.setText(index.get(position));
            holder.num1_text.setVisibility(View.VISIBLE);
            holder.product_num1.setVisibility(View.VISIBLE);
            holder.crown.setVisibility(View.VISIBLE);
            holder.product_num2.setVisibility(View.INVISIBLE);
            holder.num2_text.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.num2_text.setText(index.get(position));
            holder.product_num2.setVisibility(View.VISIBLE);
            holder.num2_text.setVisibility(View.VISIBLE);
            holder.num1_text.setVisibility(View.INVISIBLE);
            holder.product_num1.setVisibility(View.INVISIBLE);
            holder.crown.setVisibility(View.INVISIBLE);
        }
        array = Itemdata_products.get(position).milkname.split(" ");
        String modi_name="";

        for(int i=0; i<array.length; i++)
        {
            modi_name += array[i]+"\n";
        }

        holder.product_main_txt.setText(modi_name);
        Glide.with(context)
                .load(Itemdata_products.get(position).milkimg)
                .into(holder.product_main_img);
        holder.product_main_rating.setRating(Itemdata_products.get(position).ratingBar);
        holder.baseline.setImageResource(R.drawable.line_2);
    }

    @Override
    public int getItemCount() {
        return (Itemdata_products != null) ? Itemdata_products.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView product_main_img, crown, baseline;
        public TextView product_main_txt, num1_text, num2_text;
        public RatingBar product_main_rating;
        public FrameLayout product_num1, product_num2;

        public ViewHolder(View itemView)
        {
            super(itemView);

            crown = (ImageView)itemView.findViewById(R.id.crown_num1);
            num1_text = (TextView)itemView.findViewById(R.id.num1_text);
            num2_text = (TextView)itemView.findViewById(R.id.num2_text);
            product_num1 = (FrameLayout)itemView.findViewById(R.id.product_num1);
            product_num2 = (FrameLayout)itemView.findViewById(R.id.product_num2);
            product_main_img = (ImageView)itemView.findViewById(R.id.product_rank_img);
            product_main_txt = (TextView)itemView.findViewById(R.id.product_rank_txt);
            product_main_rating = (RatingBar)itemView.findViewById(R.id.product_rank_rating);
            baseline = (ImageView)itemView.findViewById(R.id.product_rank_line);

        }
    }
}

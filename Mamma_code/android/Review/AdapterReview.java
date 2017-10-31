package momma_beta.momma_bv.Review;

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


public class AdapterReview extends RecyclerView.Adapter<AdapterReview.ViewHolder>
{
    private View.OnClickListener clickListener;
    private ArrayList<Itemdata_review> itemdata_review;
    private Context context;
    private ArrayList<String> index = new ArrayList<>();
    public View view;
    public AdapterReview(ArrayList<Itemdata_review> itemdata_review, Context context, View.OnClickListener clickListener)
    {
        this.itemdata_review = itemdata_review;
        this.context = context;
        this.clickListener = clickListener;
        for(int i = 1; i<=this.itemdata_review.size()+1; i++){
            index.add(""+i);
        }
    }

    @Override
    public AdapterReview.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activit_review_rank, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(clickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterReview.ViewHolder holder, int position)
    {

        if((position) == 0)
        {
            holder.num1_text.setText(index.get(position));
            holder.num1_text.setVisibility(View.VISIBLE);
            holder.product_num1.setVisibility(View.VISIBLE);
            holder.product_num2.setVisibility(View.INVISIBLE);
            holder.num2_text.setVisibility(View.INVISIBLE);
            holder.crown.setVisibility(View.VISIBLE);
            if(itemdata_review.get(position).image.equals("0"))
            {
                holder.product_main_img.setImageResource(R.drawable.ready_icon);
            }
            else
            {
                Glide.with(context)
                        .load(itemdata_review.get(position).image)
                        .into(holder.product_main_img);
            }

            holder.product_main_txt.setText(itemdata_review.get(position).nickname);
            holder.product_main_title.setText(itemdata_review.get(position).title);
            holder.product_main_rating.setRating(itemdata_review.get(position).ratingBar);
        }
        else
        {
            holder.product_main_txt.setText(itemdata_review.get(position).nickname);
            if(itemdata_review.get(position).image.equals("0"))
            {
                holder.product_main_img.setImageResource(R.drawable.ready_icon);
            }
            else
            {
                Glide.with(context)
                        .load(itemdata_review.get(position).image)
                        .into(holder.product_main_img);
            }
            holder.crown.setVisibility(View.INVISIBLE);
            holder.product_num2.setVisibility(View.VISIBLE);
            holder.product_num1.setVisibility(View.INVISIBLE);
            holder.num2_text.setText(index.get(position));
            holder.num2_text.setVisibility(View.VISIBLE);
            holder.product_main_title.setText(itemdata_review.get(position).title);
            holder.product_main_rating.setRating(itemdata_review.get(position).ratingBar);
        }

    }

    @Override
    public int getItemCount() {
        return (itemdata_review != null) ? itemdata_review.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView product_main_img, crown, baseline;
        public TextView product_main_txt, num1_text, num2_text, product_main_title;
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
            product_main_img = (ImageView)itemView.findViewById(R.id.favorite_review_img);
            product_main_txt = (TextView)itemView.findViewById(R.id.favorite_reviewnick_txt);
            product_main_rating = (RatingBar)itemView.findViewById(R.id.favorite_review_rating);
            product_main_title = (TextView) itemView.findViewById(R.id.favorite_singleline_txt);
            baseline = (ImageView)itemView.findViewById(R.id.product_rank_line);

        }
    }
}

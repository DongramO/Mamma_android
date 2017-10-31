package momma_beta.momma_bv.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import momma_beta.momma_bv.R;

import static momma_beta.momma_bv.MyPage.Mypage_Adapter.AdapterF_Brand.TYPE_HEADER;
import static momma_beta.momma_bv.MyPage.Mypage_Adapter.AdapterF_Brand.TYPE_ITEM;
import static momma_beta.momma_bv.product.Adapter.AdapterProductFiltering.TYPE_FOOTER;

/**
 * Created by pc on 2017-03-29.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public int holderid;

    public ImageView brand_header_img;
    public TextView brand_header_txt;
    //즐겨찾기 브랜드 헤더
    public ImageView brandimg;
    //즐겨찾기 브랜드 아이템


    public ImageView product_header_img;
    public TextView product_header_txt;
    //즐겨찾기 상품 헤더
    public ImageView productimg;
    public TextView producttxt;
    public RatingBar productrating;
    public ImageView productbaseline;
    //즐겨찾기 상품 아이템


    public ImageView review_header_img;
    public TextView review_header_txt;
    //즐겨찾기 리뷰 헤더
    public ImageView reviewimg;
    public TextView reviewnicktxt;
    public TextView singleline;
    public RatingBar reviewrating;
    public ImageView reviewbaseline;
    //즐겨찾기 리뷰 아이템


    public LinearLayout click, review, grade, sugar, salt;
    public Button product_filtering_end_btn;


    public MyViewHolder(View itemView, int viewType)
    {
        super(itemView);

        if(viewType == TYPE_HEADER)
        {
            brand_header_txt = (TextView)itemView.findViewById(R.id.favorite_brand_headtxt);
            brand_header_img = (ImageView)itemView.findViewById(R.id.favorite_brand_headimg);
            holderid = 0;
        }
        else if(viewType == TYPE_ITEM)
        {
            brandimg = (ImageView) itemView.findViewById(R.id.favorite_brand_img);
            holderid = 1;
        }
        // 즐겨찾기 브랜드


        if(viewType == TYPE_HEADER){
            product_header_txt = (TextView)itemView.findViewById(R.id.favorite_product_headtxt);
            product_header_img = (ImageView)itemView.findViewById(R.id.favorite_product_headimg);
            holderid = 0;
        }else if(viewType == TYPE_ITEM){
            productimg = (ImageView) itemView.findViewById(R.id.favorite_product_img);
            producttxt = (TextView) itemView.findViewById(R.id.favorite_product_txt);
            productrating = (RatingBar) itemView.findViewById(R.id.favorite_product_rating);
            productbaseline = (ImageView) itemView.findViewById(R.id.baseline);
            holderid = 1;
        }
        // 즐겨찾기 상품


        if(viewType == TYPE_HEADER){
//            review_header_txt = (TextView)itemView.findViewById(R.id.favorite_review_headtxt);
//            review_header_img = (ImageView)itemView.findViewById(R.id.favorite_review_headimg);
            holderid = 0;
        }else if(viewType == TYPE_ITEM){
            reviewimg = (ImageView) itemView.findViewById(R.id.favorite_review_img);
            reviewnicktxt = (TextView) itemView.findViewById(R.id.favorite_reviewnick_txt);
            singleline = (TextView) itemView.findViewById(R.id.favorite_singleline_txt);
            reviewrating = (RatingBar) itemView.findViewById(R.id.favorite_review_rating);
            reviewbaseline = (ImageView) itemView.findViewById(R.id.baseline2);
            holderid = 1;
        }
        //즐겨찾기 리뷰

        if(viewType == TYPE_HEADER){
            click = (LinearLayout) itemView.findViewById(R.id.product_click_layout);
            review = (LinearLayout) itemView.findViewById(R.id.product_review_layout);
            grade = (LinearLayout) itemView.findViewById(R.id.product_grade_layout);
            sugar = (LinearLayout) itemView.findViewById(R.id.product_sugar_layout);
            salt = (LinearLayout) itemView.findViewById(R.id.product_salt_layout);
            holderid = 0;
        }
        else if(viewType == TYPE_ITEM)
        {
            brandimg = (ImageView) itemView.findViewById(R.id.favorite_brand_img);
            holderid = 1;
        }
        else if(viewType == TYPE_FOOTER)
        {
            product_filtering_end_btn = (Button) itemView.findViewById(R.id.f_product_end_btn);
            holderid = TYPE_FOOTER;
        }

    }
}

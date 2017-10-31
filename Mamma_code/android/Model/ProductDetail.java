package momma_beta.momma_bv.Model;

import java.util.ArrayList;

/**
 * Created by idongsu on 2017. 5. 16..
 */

public class ProductDetail
{
    public ResultData result;

    public class ResultData
    {
        public DetailMilkInfo milk;
        public ArrayList<DetailIngredient> ingredient;
        public ArrayList<DetailReviewInfo> review;
    }
}

package momma_beta.momma_bv.Model;

import java.util.ArrayList;

/**
 * Created by idongsu on 2017. 5. 16..
 */

public class ReviewDetail
{
    public ResultData result;

    public class ResultData
    {
        public ReviewDetailInfo review;
        public CheckConfirm check;
        public ArrayList<ReviewComment> comment;
    }
}

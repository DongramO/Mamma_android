package momma_beta.momma_bv.Model;

import java.util.ArrayList;

/**
 * Created by idongsu on 2017. 5. 12..
 */

public class SearchResult
{
    public ResultData result;

    public class ResultData
    {
        public ArrayList<MilkInfo> drymilk;
        public ArrayList<IngreInfo> ingredient;
        public ArrayList<ManuInfo> manufactor;
    }
}

package momma_beta.momma_bv.Model;

import java.util.ArrayList;

/**
 * Created by idongsu on 2017. 5. 14..
 */

public class MypageInfo
{
    public ResultData result;
    public class ResultData
    {
        public String age;
        public BookmarkAmount bookmark;
        public ArrayList<Worrycontent> worry;
        public Mypage_myinfo myinfo;
    }
}

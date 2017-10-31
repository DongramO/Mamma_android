package momma_beta.momma_bv.MyPage.Mypage_Adapter;

/**
 * Created by pc on 2017-03-29.
 */

public class Reviewitem {

    int reviewimg;
    String reviewnicknametxt;
    String reviewtxt;
    float reviewrating;

    public Reviewitem(int reviewimg, String reviewnicknametxt, float reviewrating, String reviewtxt) {
        this.reviewimg = reviewimg;
        this.reviewnicknametxt = reviewnicknametxt;
        this.reviewrating = reviewrating;
        this.reviewtxt = reviewtxt;
    }
}

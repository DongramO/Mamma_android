package momma_beta.momma_bv.Review;

/**
 * Created by pc on 2017-02-01.
 */

public class Itemdata_review
{

    public String image;
    public float ratingBar;
    public String nickname;
    public String title;
    public int id;
    public String milkname;
    
    public Itemdata_review(String nickname,String title, String image, float ratingBar, int id, String milkname)
    {
        this.nickname = nickname;
        this.image = image;
        this.ratingBar = ratingBar;
        this.title = title;
        this.id = id;
        this.milkname = milkname;
    }

}

package momma_beta.momma_bv.product;

/**
 * Created by pc on 2017-02-01.
 */

public class Itemdata_product
{

    public String milkimg;
    public float ratingBar;
    public String milkname;
    public int id;
    public Itemdata_product(String milkname, String milkimg, float ratingBar, int id)
    {
        this.milkname = milkname;
        this.milkimg = milkimg;
        this.ratingBar = ratingBar;
        this.id = id;
    }

}

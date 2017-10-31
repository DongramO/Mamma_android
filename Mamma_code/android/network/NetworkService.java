package momma_beta.momma_bv.network;


import momma_beta.momma_bv.Model.BrandName;
import momma_beta.momma_bv.Model.FilterReview;
import momma_beta.momma_bv.Model.HomeList;
import momma_beta.momma_bv.Model.IngreDetailList;
import momma_beta.momma_bv.Model.IngreList;
import momma_beta.momma_bv.Model.LoginInfo;
import momma_beta.momma_bv.Model.LoginResult1;
import momma_beta.momma_bv.Model.MyBookMark;
import momma_beta.momma_bv.Model.MyReview;
import momma_beta.momma_bv.Model.MypageInfo;
import momma_beta.momma_bv.Model.MypageModifyResult;
import momma_beta.momma_bv.Model.ProductDetail;
import momma_beta.momma_bv.Model.ProductList;
import momma_beta.momma_bv.Model.ReviewDetail;
import momma_beta.momma_bv.Model.ReviewList;
import momma_beta.momma_bv.Model.ReviewWriteInfo;
import momma_beta.momma_bv.Model.SearchResult;
import momma_beta.momma_bv.Model.SignUpResult;
import momma_beta.momma_bv.Model.UserInfo;
import momma_beta.momma_bv.MyPage.Model.MyImage;
import momma_beta.momma_bv.Review.Review_model.Reviewbad;
import momma_beta.momma_bv.Review.Review_model.Reviewgood;
import momma_beta.momma_bv.product.Product_model.FilterProduct;
import momma_beta.momma_bv.product.Product_model.addbookmark;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;


public interface NetworkService
{
    @POST("/sign/in")
    Call<LoginResult1> getLoginResult(@Body LoginInfo loginInfo);

    @POST("/sign/up")
    Call<SignUpResult> getSignResult(@Body UserInfo userInfo);

    @Multipart
    @POST("/review/post/{milkname}/{nickname}")
    Call<ReviewWriteInfo> getWriteResult(@Part MultipartBody.Part file,
                                         @Part("good_comment") RequestBody good_comment,
                                         @Part("bad_comment") RequestBody bad_comment,
                                         @Part("title") RequestBody title,
                                         @Part("tip") RequestBody tip,
                                         @Part("review_grade") RequestBody review_grade,
                                         @Part("salt_grade") RequestBody slat_grade,
                                         @Part("brix_grade") RequestBody brix_grade,
                                         @Part("milk_id") RequestBody milk_id,
                                         @Part("milk_name") RequestBody milk_name,
                                         @Part("review_writer") RequestBody review_writer,
                                         @Part("image_flag") RequestBody image_flag,
                                         @Path("milkname") String milkname, @Path("nickname") String nickname );

    @Multipart
    @POST("/mypage/image/{nickname}/{kind}")
    Call<MyImage> getImage(@Part MultipartBody.Part file,
                           @Path("nickname") String nickname,
                           @Path("kind")String kind);

    @GET("/homeview")
    Call<HomeList> getHomeResult();

    @GET("/search/drymilk/{milk_name}")
    Call<SearchResult> getSearchResult(@Path("milk_name") String milk_name);

    @GET("/manufactor/{manu_name}")
    Call<BrandName> getBrandResult(@Path("manu_name") String manu_name);

    @GET("/ingredient_detail/elements/{ingre_name}")
    Call<IngreList> getIngreResult(@Path("ingre_name") String ingre_name);

    @GET("/mypage/{nickname}")
    Call<MypageInfo> getMypageResult(@Path("nickname") String nickname);

    @GET("/bookmark/my/list/{nickname}/{kind}")
    Call<MyBookMark> getMyBookmarkResult(@Path("nickname") String nickname, @Path("kind") String kind);

    @GET("/review/myreview/{nickname}")
    Call<MyReview> getMyReviewResult(@Path("nickname") String nickname);

    @GET("/product_list/drymilk")
    Call<ProductList> getProductlistResult();

    @GET("/review_list/review")
    Call<ReviewList> getReviewlistResult();

    @GET("/review_list/filter/review/{worry}/{count}/{kind}")
    Call<FilterReview> getFilterReviewResult(@Path("worry") String worry, @Path("count") int count, @Path("kind") String kind);

    @GET("/review/{writer}/{milkname}/{id}")
    Call<ReviewDetail> getReviewDetailInfoResult(@Path("writer") String writer, @Path("milkname") String milkname, @Path("id") int id);

    @GET("/product_detail/{milk_name}")
    Call <ProductDetail> getProductDetailInfoResult(@Path("milk_name") String milkname);

    @GET("/ingredient_detail/{milk_name}")
    Call <IngreDetailList> getProductIngreDetailResult(@Path("milk_name") String milkname);

    @POST("/mypage/update/{nickname}")
    Call <MypageModifyResult> getModifyResult(@Path("nickname") String nickname, @Body MypageModifyResult modifyInfo) ;

    @POST("/bookmark")
    Call<addbookmark> addBookmark(@Body addbookmark mark);

    @GET("/reviewgood/{nickname}/{review_id}")
    Call<Reviewgood> getReviewGoodResult(@Path("nickname") String nickname, @Path("review_id") int review_id);

    @GET("/reviewbad/{nickname}/{review_id}")
    Call<Reviewbad> getReviewBadResult(@Path("nickname") String nickname, @Path("review_id") int review_id);

    @GET("/product_list/filter/{kind}/{brand}")
    Call<FilterProduct> getFilterProductResult(@Path("kind") String kind, @Path("brand") String brand);
}

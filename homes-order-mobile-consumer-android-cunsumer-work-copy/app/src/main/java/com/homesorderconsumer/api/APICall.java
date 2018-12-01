package com.homesorderconsumer.api;

import com.homesorderconsumer.category.model.CategoryResponse;
import com.homesorderconsumer.checkout.model.CheckoutResponse;
import com.homesorderconsumer.checkout.model.DeliveryAddress;
import com.homesorderconsumer.checkout.model.EstimatePaymentMethodResponse;
import com.homesorderconsumer.product.model.ProductDetailResponse;
import com.homesorderconsumer.product.model.ProductRequest;
import com.homesorderconsumer.product.model.ProductResponse;
import com.homesorderconsumer.product.model.ReviewItemsResponse;
import com.homesorderconsumer.support.model.Support;
import com.homesorderconsumer.trackorder.TrackOrder;
import com.homesorderconsumer.trackorder.model.TrackOrderResponse;
import com.homesorderconsumer.user.cart.model.AddToCart;
import com.homesorderconsumer.user.cart.model.CartAddResponse;
import com.homesorderconsumer.user.cart.model.CartListResponse;
import com.homesorderconsumer.user.forgotpassword.model.ForgotPassword;
import com.homesorderconsumer.user.login.model.Login;
import com.homesorderconsumer.user.login.model.User;
import com.homesorderconsumer.user.myaccount.profile.model.ProfileUpdate;
import com.homesorderconsumer.user.myorder.model.CancelOrder;
import com.homesorderconsumer.user.myorder.model.MyOrderListResponse;
import com.homesorderconsumer.user.myorder.model.OrderDetailResponse;
import com.homesorderconsumer.user.myorder.model.ReviewPost;
import com.homesorderconsumer.user.preference.model.Country;
import com.homesorderconsumer.user.preference.model.FilterAreasResponse;
import com.homesorderconsumer.user.registration.model.Register;
import com.homesorderconsumer.user.resetpassword.model.ResetPassword;
import com.homesorderconsumer.user.wishlist.model.WishListAddResponse;
import com.homesorderconsumer.user.wishlist.model.WishListResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by mac on 2/21/18.
 */

public interface APICall {

    @POST("customer/me/register")
    Observable<Response<User>> register(@Body Register register);

    @POST("customer/login")
    Observable<Response<User>> login(@Body Login login);

    @POST("customer/forgotpassword")
    Observable<Response<GeneralResponse>> forgotPassword(@Body ForgotPassword forgotPassword);

    @GET("customer/category")
    Observable<Response<CategoryResponse>> category();

    @GET("customer/filterareas")
    Observable<Response<FilterAreasResponse>> filterAreas();

    @POST("customer/products/{token}")
    Observable<Response<ProductResponse>> products(@Path("token") String token,@Body
            ProductRequest productRequest);

    @POST("customer/products")
    Observable<Response<ProductResponse>> products(@Body
            ProductRequest productRequest);

    @POST("customer/me/passwordreset/{token}")
    Observable<Response<GeneralResponse>> passwordReset(@Path("token") String token, @Body ResetPassword resetPassword);

    @GET("customer/productbyid/{productid}")
    Observable<Response<ProductDetailResponse>> productByID(@Path("productid") int productid);

    @POST("customer/wishlist/add/{productid}/{token}")
    Observable<Response<WishListAddResponse>> wishListAdd(@Path("productid") int productid,@Path("token") String
            token);

    @GET("customer/wishlist/{token}")
    Observable<Response<WishListResponse>> wishList(@Path("token") String token);

    @GET("customer/wishlist/delete/{wishlistitemid}/{token}")
    Observable<Response<WishListAddResponse>> wishListDelete(@Path("wishlistitemid") String wishlistitemid, @Path("token") String
            token);

    @POST("customer/guest/addtocart/{cartId}")
    Observable<Response<CartAddResponse>> guestAddtocart(@Path("cartId") String cartId,@Body AddToCart addToCart);

    @POST("customer/guest/addtocart")
    Observable<Response<CartAddResponse>> guestAddtocart(@Body AddToCart addToCart);

    @GET("customer/guest/cartitems/{region}/{cartid}")
    Observable<Response<CartListResponse>> guestCartItems(@Path("region") String region,@Path
            ("cartid") String cartid);

    @GET("customer/guest/deletecartitem/{cartid}/{itemId}")
    Observable<Response<GeneralResponse>> guestDeleteCartItem(@Path("cartid") String cartid,@Path
            ("itemId") int itemId);

    @POST("customer/guest/updatecart/{cartId}/{itemid}")
    Observable<Response<GeneralResponse>> guestUpdateCart(@Path("cartId") String cartId,@Path
            ("itemid") int itemid, @Body AddToCart addToCart);

    @POST("customer/addtocart/{token}")
    Observable<Response<GeneralResponse>> addToCart(@Path("token") String token, @Body AddToCart
            addToCart);

    @GET("customer/cartitems/{region}/{token}")
    Observable<Response<CartListResponse>> cartItems(@Path("region") String region, @Path
            ("token") String token);

    @POST("customer/updatecart/{itemId}/{token}")
    Observable<Response<GeneralResponse>> updateCart(@Path("itemId") int itemId, @Path
            ("token") String token, @Body AddToCart addToCart);

    @GET("customer/deletecartitem/{itemid}/{token}")
    Observable<Response<GeneralResponse>> deleteCartItem(@Path("itemid") int itemid, @Path
            ("token") String token);

    @GET("customer/productreview/{productid}/{page}")
    Observable<Response<ReviewItemsResponse>> productReviewItems(@Path("productid") int productid, @Path
            ("page") int page);

    @POST("customer/addShippingaddress/{token}")
    Observable<Response<GeneralResponse>> addShippingAddress(@Path("token") String token, @Body
            DeliveryAddress deliveryAddress);

    @POST("customer/estimatepaymentmethod/{token}")
    Observable<Response<EstimatePaymentMethodResponse>> estimatePaymentMethod(@Path("token") String token, @Body
            DeliveryAddress deliveryAddress);

    @POST("customer/ordercheckout/{token}")
    Observable<Response<CheckoutResponse>> orderCheckout(@Path("token") String token, @Body
            DeliveryAddress deliveryAddress);

    @POST("customer/guest/addShippingaddress/{cartid}")
    Observable<Response<GeneralResponse>> guestAddShippingAddress(@Path("cartid") String cartid, @Body
            DeliveryAddress deliveryAddress);

    @POST("customer/guest/estimatepaymentmethod/{cartid}")
    Observable<Response<EstimatePaymentMethodResponse>> guestEstimatePaymentMethod(@Path("cartid") String cartid, @Body
            DeliveryAddress deliveryAddress);

    @POST("customer/guest/ordercheckout/{cartid}")
    Observable<Response<CheckoutResponse>> guestOrderCheckout(@Path("cartid") String cartid, @Body
            DeliveryAddress deliveryAddress);

    @GET("customer/orderslist/{token}")
    Observable<Response<MyOrderListResponse>> ordersList(@Path("token") String token);

    @GET("customer/ordersbyId/{orderId}/{token}")
    Observable<Response<OrderDetailResponse>> ordersById(@Path("orderId") String orderId, @Path
            ("token") String token);

    @POST("customer/review/post/{token}")
    Observable<Response<GeneralResponse>> reviewPost(@Path("token") String token, @Body
                                                     ReviewPost reviewPost);

    @POST("customer/review/post")
    Observable<Response<GeneralResponse>> guestReviewPost(@Body
            ReviewPost reviewPost);

    @POST("customer/contactus")
    Observable<Response<GeneralResponse>> contactUs(@Body Support support);

    @POST("customer/cancelorder/{orderId}/{token}")
    Observable<Response<GeneralResponse>> cancelOrder(@Path("orderId") String orderId, @Path
            ("token") String token,@Body CancelOrder cancelOrder);

    @POST("customer/me/profileupdate/{token}")
    Observable<Response<GeneralResponse>> profileUpdate(@Path("token") String token, @Body
                                                        ProfileUpdate profileUpdate);
    @GET
    Observable<Response<List<Country>>> areasList(@Url String url);

    @POST("customer/guest/trackorder")
    Observable<Response<TrackOrderResponse>> trackOrder(@Body TrackOrder trackOrder);

}



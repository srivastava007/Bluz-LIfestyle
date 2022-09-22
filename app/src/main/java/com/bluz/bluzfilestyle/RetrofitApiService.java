package com.bluz.bluzfilestyle;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApiService {

    @Headers({"Accept: application/json",
            "Content-type: application/json"})
    @POST("place") Call<ResponsePojo> responcePojo(@Body OrderPostPojo orderPostPojo);

    @Headers({"Authorization: Bearer y0C2qaqLaNyxB2GhF9aSUTk8DcZAW5IvFWzxjIqNQOuPqkc3dBTYrnaEHVXQ",
            "Accept: application/json",
            "Content-type: application/json"})
    @POST("myorders") Call<MyOrderResponsePojo> myOrderResponsePojo(@Body GetMyOrdersPojo getMyOrdersPojo);

    @Headers({"Authorization: Bearer y0C2qaqLaNyxB2GhF9aSUTk8DcZAW5IvFWzxjIqNQOuPqkc3dBTYrnaEHVXQ",
            "Accept: application/json",
            "Content-type: application/json"})
    @POST("orderDetails")
    Call<OrderDetailResponsePojo> orderDetailsResponsePojo(@Body GetOrderDetailPojo getOrderDetailPojo);

    @Headers({"Authorization: Bearer y0C2qaqLaNyxB2GhF9aSUTk8DcZAW5IvFWzxjIqNQOuPqkc3dBTYrnaEHVXQ",
            "Accept: application/json",
            "Content-type: application/json"})
    @POST("cancelorder")
    Call<CancelOrderResponsePojo> cancelOrderResponsePojo(@Body CancelOrderPostPojo cancelOrderPostPojo);
}

package app.eazyhomebrunei.com;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;


    public interface edittprofileinterface {

        String IMAGEURL = "https://www.eazyhomebrunei.com/Android_Api/";
        @FormUrlEncoded

//    @Headers("Token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMTciLCJ0aW1lU3RhbXAiOiIyMDIwLTExLTEyIDA0OjU3OjA5In0.kscqGlDPXbUHVi0HTh307y96LflYuY_perqVTiyL-hE")

        @POST("UpdateProfile")
        Call<String> getImageData(
                @Header("Token") String token,
                @Field("address") String address,
                   @Field("email") String email,
                @Field("name") String name




        );

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Login;

import Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import org.apache.http.client.ClientProtocolException;

/**
 *
 * @author Dell
 */
public class GoogleLG {
    public static String getToken(final String code) throws ClientProtocolException, IOException {
    String link = "https://oauth2.googleapis.com/token";
    String response = org.apache.http.client.fluent.Request.Post(link)
         .bodyForm(
                    org.apache.http.client.fluent.Form.form()
                    .add("code", code)
                    .add("client_id", Constants.GOOGLE_CLIENT_ID)
                 .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                 .add("redirect_uri",Constants.GOOGLE_REDIRECT_URL)
                 .add("grant_type",Constants.GOOGLE_GRANT_TYPE)
                 .build()
         ).execute().returnContent().asString();
    
    JsonObject jobj = new Gson().fromJson(response,JsonObject.class);
    
    String accessToken = jobj.get("access_token").toString().replaceAll("\"","");
    return accessToken; 
  }
  
   public static User getUserInfo(final String accessToken)throws ClientProtocolException,IOException{
       String link ="https://people.googleapis.com/v1/people/me?personFields=names,emailAddresses,genders,birthdays,addresses,phoneNumbers,photos" ;
       
        String response = org.apache.http.client.fluent.Request.Get(link)       
        .addHeader("Authorization", "Bearer " + accessToken)
            .execute()
            .returnContent()
            .asString();
        
        System.out.println("Google API Response: " + response);

        
        User user = new User();
        
        JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
        
        String k = jsonObject.get("resourceName").getAsString().split("/")[1];
        user.setUserID(k.hashCode());
        // fullname 
        JsonArray names = jsonObject.getAsJsonArray("names");
        if(names!=null&&names.size()>0){
            JsonObject nameObject = names.get(0).getAsJsonObject();
            user.setFullname(nameObject.get("displayName").getAsString());
        }
        
        // phone 
        JsonArray phoneNumbers = jsonObject.getAsJsonArray("phoneNumbers");
        if (phoneNumbers != null && phoneNumbers.size() > 0) {
            JsonObject phoneObject = phoneNumbers.get(0).getAsJsonObject();
            user.setPhone(phoneObject.get("value").getAsString());
        }
        // email = username 
        
        JsonArray emailAddresses = jsonObject.getAsJsonArray("emailAddresses");
        if(emailAddresses!= null && emailAddresses.size()>0){
            JsonObject emailObject = emailAddresses.get(0).getAsJsonObject();
            user.setUsername(emailObject.get("value").getAsString());
        }
        // password
        user.setPassword("");
        
        // genders
        JsonArray genders = jsonObject.getAsJsonArray("genders");
        if(genders!=null && genders.size()>0){
            JsonObject genderObject = genders.get(0).getAsJsonObject();
            user.setGender(genderObject.get("value").getAsString());
        }
        //status 
        user.setStatus(true);
        //role
        //user.setRole(1);
        // address
        user.setAddress("null");
        return user;
   }
}

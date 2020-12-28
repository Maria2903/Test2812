package com.test.m281220;

import android.content.Context;
import android.content.SharedPreferences;

public class SPreferences {

    protected final String TOKEN = "token";
    protected final String LOGIN = "login";
    protected final String NAME = "name";
    protected final String PHONE = "phone";
    protected final String EMAIL = "email";
    protected final String PSEVDONIM = "psevdonim";
    protected final String ID = "ID";
    protected final String PARTNER_ID = "PARTNER_ID";
    protected final String PARTNER_LINK = "PARTNER_LINK";
    protected final String BALANCE = "BALANCE";
    protected final String WALLET_ADDRESS = "WALLET_ADDRESS";




    private Context context;
    private SharedPreferences sp;

    public SPreferences(Context context){
        this.context = context;
        sp = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

    }

    public void deleteAllShared(){
        sp =  context.getSharedPreferences("PREFEREBCE", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }



    //------token------
    public void setToken(String token){
        sp.edit().putString(TOKEN, token).commit();
    }
    public String getToken(){
        return sp.getString(TOKEN, "");
    }
    //-----------------



    //------login------
    public void setLogin(String login){
        sp.edit().putString(LOGIN, login).commit();
    }
    public String getLogin(){
        return sp.getString(LOGIN, "");
    }
    //-----------------



    //------username------
    public void setName(String name){
        sp.edit().putString(NAME, name).commit();
    }
    public String getName(){
        return sp.getString(NAME, "");
    }
    //-----------------



    //------phone------
    public void setPhone(String phone){
        sp.edit().putString(PHONE, phone).commit();
    }
    public String getPhone(){
        return sp.getString(PHONE, "");
    }
    //-----------------

    //------email------
    public void setEmail(String email){
        sp.edit().putString(EMAIL, email).commit();
    }
    public String getEmail(){
        return sp.getString(EMAIL, "");
    }


    //-----psevdonim-------
    public void setPsevdonim(String psevdonim){
        sp.edit().putString(PSEVDONIM, psevdonim).commit();
    }
    public String getPsevdonim(){
        return sp.getString(PSEVDONIM, "");
    }


    //-----id-------
    public void setId(Integer id){
        sp.edit().putInt(ID, id).commit();
    }
    public Integer getId(){
        return sp.getInt(ID, 0);
    }


    //-----partner_id-------
    public void setPartnerId(Integer partner_id){
        sp.edit().putInt(PARTNER_ID, partner_id).commit();
    }
    public Integer getPartnerId(){
        return sp.getInt(PARTNER_ID, 0);
    }


    //-----partner_link-------
    public void setPartnerLink(String partner_link){
        sp.edit().putString(PARTNER_LINK, partner_link).commit();
    }
    public String getPartnerLink(){
        return sp.getString(PARTNER_LINK, "");
    }



    //-----balance-------
    public void setBalance(Float balance){
      sp.edit().putFloat(BALANCE, balance).commit();
    }
    public Float getBalance(){
        return sp.getFloat(BALANCE, 0);
    }

    //-----wallet_address-------
    public void setWalletAddress(String wallet_address){
        sp.edit().putString(WALLET_ADDRESS, wallet_address).commit();
    }
    public String getWalletAddress(){
        return sp.getString(WALLET_ADDRESS, "");
    }

}
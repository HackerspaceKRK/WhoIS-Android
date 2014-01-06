package pl.hskrk.whois.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Filip on 06.01.14.
 */
public class WhoIS {

    @SerializedName("date")
    public String date;

    @SerializedName("total_devices_count")
    public int devicesCount;

    @SerializedName("users")
    public ArrayList<String> users;
}

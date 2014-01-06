package pl.hskrk.whois.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import pl.hskrk.whois.R;
import pl.hskrk.whois.data.WhoIS;

/**
 * Created by Filip on 02.01.14.
 */
public class PresenceDisplayer extends AppWidgetProvider {
    final static String TAG = "PDisplayer";

    Context context;
    int ids[];

    class DataUpdater extends AsyncTask<String, String, String> {

        URL sauce;
        WhoIS who;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "updater pre");
            try{
                sauce = new URL(context.getResources().getString(R.string.whois_json));
            }catch (MalformedURLException exc){}
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URLConnection urlConnection = sauce.openConnection();
                urlConnection.setConnectTimeout(1000);
                InputStream stream = urlConnection.getInputStream();

                java.util.Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
                String data = s.hasNext() ? s.next() : "";

                if(data != null){
                    Gson g = new Gson();
                    who = g.fromJson(data, WhoIS.class);
                }
            } catch (Exception ex) {
                Log.d(TAG, "Exception: "+ex);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, "updater post");
           updateWidgetData(who);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "updating");
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.context = context;
        this.ids = appWidgetIds;

        updateWidgetData(null);
        new DataUpdater().execute();
    }

    void updateWidgetData(WhoIS who){
        Log.d(TAG, "updating widget data");
        int peopleInHackerspace = 0;

        if(who != null){
            peopleInHackerspace = who.devicesCount;
            Log.d(TAG, "data not null");
        }else{
            Log.d(TAG, "data null");
        }

        RemoteViews remoteViews =
                new RemoteViews(context.getPackageName(), R.layout.widget_presencedisplayer);

        ComponentName thisWidget = new ComponentName(context, PresenceDisplayer.class);

        remoteViews.setTextViewText(R.id.peopleInside,
                                    String.valueOf(peopleInHackerspace));

        if(peopleInHackerspace > 0)
            remoteViews.setImageViewResource(R.id.lightbulb, R.drawable.lightbulb_on);
        else
            remoteViews.setImageViewResource(R.id.lightbulb,  R.drawable.lightbulb_off);

        AppWidgetManager.getInstance(context)
                        .updateAppWidget(thisWidget, remoteViews);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}

package tegdev.optotypes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by edgar on 10/07/2018.
 */

public class ControlForService {

    public static Context context = null;
    public static boolean run = false;
    public static BackGroundServiceForUpdate backGroundServiceForUpdate = null;

    public static void runAndStopService (){

        if (!run){
            run = true;
            context.startService(new Intent(context, ServiceForUpdate.class));
        }else{
            run = false;
            context.stopService(new Intent(context, ServiceForUpdate.class));
        }
    }

    public static boolean exitsConnection (){

        URL url = null;
        int responseCode;
        boolean connect = false;
        ServerPath serverPath = new ServerPath();
        String path = serverPath.getHttp() + serverPath.getIpAdddress() + serverPath.getPathAddress()+ "patients";

        try{

            url = new URL (path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK){
                connect = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return connect;

    }



}

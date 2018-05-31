package tegdev.optotypes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Edgar on 30/05/2018.
 */

public class ClientProjector {

    public void sendMessage (String message){

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(message);
    }

    class BackgroundTask extends AsyncTask {

        ///String ip = "192.168.1.3";
        String ip = ConfgConnect.getIpShowTest();
        String port = ConfgConnect.getPortConecction();
        Socket socket;
        PrintWriter write;

        @Override
        protected Object doInBackground(Object[] params) {

            try {

                String message = (String) params[0];
                socket = new Socket(ip, Integer.parseInt(port));
                write = new PrintWriter(socket.getOutputStream());
                write.write(message);
                write.flush();
                write.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
                Log.d("message: ", "no Socket");
            }

            return null;
        }
    }


}

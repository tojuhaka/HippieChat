package tojuhaka.hippiechatutils;



import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by tojuhaka on 5/21/2017.
 */


public class Sender {
    private static final String BASE_URL = "https://hipchat.domain.com/v2/room/testing_room/message";
    private static final String token = "<token here>";

    public static void sendMessage(Context context, Message message) throws IOException, JSONException {
        AsyncHttpClient client = new AsyncHttpClient();

        JSONObject jsonParams = new JSONObject();
        jsonParams.put("message", message.getText());

        StringEntity entity = new StringEntity(jsonParams.toString());

        client.addHeader("Authorization", "Bearer "+token);
        client.post(context, BASE_URL, entity, "application/json", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                System.out.println("Success!");
                System.out.print(statusCode);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.out.print(statusCode);
                e.printStackTrace(System.out);
            }

        });


    }

}

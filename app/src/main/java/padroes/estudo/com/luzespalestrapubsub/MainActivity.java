package padroes.estudo.com.luzespalestrapubsub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubException;


public class MainActivity extends AppCompatActivity {

    private Pubnub pubnub;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progress = (ProgressBar) findViewById(R.id.progress);

        pubnub = new Pubnub(
                "pub-c-128c8884-d4b7-4c72-bfd5-273a0a73c35c",
                "sub-c-c5dee452-6049-11e7-b272-02ee2ddab7fe");

        try {
            pubnub.subscribe("luzes_arduino", new Callback() {
                        @Override
                        public void connectCallback(String channel, Object message) {
                            Log.e("my_channel", "Hello from the PubNub Java SDK");
                        }

                        @Override
                        public void disconnectCallback(String channel, Object message) {}

                        public void reconnectCallback(String channel, Object message) {}

                        @Override
                        public void successCallback(String channel, Object message) {
                            progress.setProgress(Integer.parseInt(message.toString()));
                        }
                    }
            );
        } catch (PubnubException e) {
            Log.e("my_channel", "erro: " + e.toString());
        }

        /*
        //VERSAO MAIS NOVA. 4.6.5. MAS NA PRÁTICA NÃO FUNCIONOU
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-c5dee452-6049-11e7-b272-02ee2ddab7fe");
        pnConfiguration.setPublishKey("pub-c-128c8884-d4b7-4c72-bfd5-273a0a73c35c");
        pnConfiguration.setSecure(false);

        pubnub = new PubNub(pnConfiguration);

        pubnub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {
                if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                    Log.e("tag", "disconnect");
                }
                else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
                    Log.e("tag", "connected");
                }
                else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {
                    Log.e("tag", "reconnected");
                }
                else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {
                    Log.e("tag", "error");
                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                Log.e("tag", "message");
                Log.e("tag", message.getMessage().toString());
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        });

        pubnub.subscribe().channels(Arrays.asList("luzes_arduino")).execute();*/
    }
}

package com.coms3091mc3.projectmanager.ui.inbox;

import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.coms3091mc3.projectmanager.R;
import com.coms3091mc3.projectmanager.databinding.FragmentChatBinding;
import com.coms3091mc3.projectmanager.utils.Const;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatFragment extends Fragment {

    private FragmentChatBinding binding;
    private WebSocketClient cc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InboxViewModel inboxViewModel =
                new ViewModelProvider(this).get(InboxViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
//        binding.setModal(new ChatDataModel(getContext()));
        View root = binding.getRoot();

//        final TextView textView = binding.textInbox;
//        inboxViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

//        String url = Const.API_SERVER + "/user/" + Const.user.getUserID() + "/teams";
        TextView teamName = root.findViewById(R.id.teamName);
        Button btnMsg = root.findViewById(R.id.btnMsgSend);
        teamName.setText("Team Name");

        TextView conversation = root.findViewById(R.id.txtConvo);

        btnMsg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Draft[] drafts = {
                        new Draft_6455()
                };

                /**
                 * If running this on an android device, make sure it is on the same network as your
                 * computer, and change the ip address to that of your computer.
                 * If running on the emulator, you can use localhost.
                 */
                String w = "ws://10.0.2.2:8080/websocket/" + Const.user.getUsername();

                try {
                    Log.d("Socket:", "Trying socket");
                    cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                        @Override
                        public void onMessage(String message) {
                            Log.d("", "run() returned: " + message);
//                            String s = t1.getText().toString();
//                            t1.setText(s + "\nServer:" + message);
                        }

                        @Override
                        public void onOpen(ServerHandshake handshake) {
                            Log.d("OPEN", "run() returned: " + "is connecting");
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.d("CLOSE", "onClose() returned: " + reason);
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("Exception:", e.toString());
                        }
                    };
                } catch (URISyntaxException e) {
                    Log.d("Exception:", e.getMessage().toString());
                    e.printStackTrace();
                }
                cc.connect();

            }
        });
        String url = "ws://10.0.2.2:8080/websocket/" + Const.user.getUsername();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
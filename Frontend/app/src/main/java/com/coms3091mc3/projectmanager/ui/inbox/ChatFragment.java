package com.coms3091mc3.projectmanager.ui.inbox;

import android.os.Bundle;
import android.provider.Telephony;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    EditText msg;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InboxViewModel inboxViewModel =
                new ViewModelProvider(this).get(InboxViewModel.class);

        binding = FragmentChatBinding.inflate(inflater, container, false);
//        binding.setModal(new ChatDataModel(getContext()));
        View root = binding.getRoot();

//        final TextView textView = binding.textInbox;
//        inboxViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        TextView teamName = root.findViewById(R.id.teamName);
        Button btnMsg = root.findViewById(R.id.btnMsgSend);
        teamName.setText(getArguments().getString("teamName"));
        msg = root.findViewById(R.id.etMsg);

        TextView conversation = root.findViewById(R.id.txtConvo);
        conversation.setMovementMethod(new ScrollingMovementMethod());

        Draft[] drafts = {
                new Draft_6455()
        };

//        String w = "ws://10.0.2.2:8080/chat/" + Const.user.getUsername();
//        String w = "localhost:8080/websocket/" + Const.user.getUsername();
        String w = Const.CHAT_SERVER + Const.user.getUsername() + "/" + getArguments().getInt("teamID");
        try {
            Log.d("Socket:", "Trying socket");
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("chatbox", "run() returned: " + message);
                    String s = conversation.getText().toString();
//                    conversation.setText(s + "\n" + message);
                    conversation.append("\n" + message);
                    Editable editable = conversation.getEditableText();
                    Selection.setSelection(editable, editable.length());
//                    conversation.scrollBy(0,100);
//                    conversation.scroll
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("chat_fragment", "run() returned: " + "is connecting");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("chat_fragment", "onClose() returned: " + reason);
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

        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(msg.getText().length() > 0){
                        cc.send(msg.getText().toString());
                        msg.setText("");
                    }
                } catch (Exception e) {
                    Log.d("ExceptionSendMessage:", e.getMessage());
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
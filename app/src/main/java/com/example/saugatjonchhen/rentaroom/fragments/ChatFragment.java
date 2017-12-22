package com.example.saugatjonchhen.rentaroom.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.saugatjonchhen.rentaroom.MainActivity;
import com.example.saugatjonchhen.rentaroom.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    View view;
    private TextView messages_heading;
    public FrameLayout chat_loggedInFrame, chat_loggedOutFrame;
    private Button login;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        messages_heading = view.findViewById(R.id.messages_heading);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(), "fonts/AvenirNextLTPro-Demi.otf");
        messages_heading.setTypeface(type);
        login = view.findViewById(R.id.btn_login_chat);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.chat_btn_click();
            }
        });

        chat_loggedInFrame = view.findViewById(R.id.chat_loggedInFrame);
        chat_loggedOutFrame = view.findViewById(R.id.chat_loggedOutFrame);
            if ((MainActivity.SaveSharedPreference.getUserName(getActivity()).length() == 0)) {
                chat_loggedOutFrame.setVisibility(View.VISIBLE);
                chat_loggedInFrame.setVisibility(View.GONE);
            }else{
                chat_loggedInFrame.setVisibility(View.VISIBLE);
                chat_loggedOutFrame.setVisibility(View.GONE);
            }
        return view;
    }

}

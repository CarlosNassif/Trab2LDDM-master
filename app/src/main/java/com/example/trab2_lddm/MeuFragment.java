package com.example.trab2_lddm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MeuFragment extends Fragment {
    private TextView txtContent;
    private static final String nContent = "content";
    private String mNodeContent;

    public MeuFragment() {}

    public static MeuFragment newFrag(String content) {
        MeuFragment fragment = new MeuFragment();
        Bundle args = new Bundle();
        args.putString(nContent, content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNodeContent = getArguments().getString(nContent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment, container, false);
        txtContent = v.findViewById(R.id.nodeContent);
        txtContent.setText(mNodeContent);
        return v;
    }
}
package com.ficheralezzi.fantasygo.ModalitaNearPvE.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.ficheralezzi.fantasygo.R;
import com.ficheralezzi.fantasygo.Utils.CharacterEntity;
import com.ficheralezzi.fantasygo.Utils.CoverFlowAdapter;

import java.util.ArrayList;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class ModalitaNearPvEActivity extends AppCompatActivity{
    private static final String TAG = "ModalitàNearPvEActivity";

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<CharacterEntity> mData = new ArrayList<>(0);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modnearpve);

        mData.add(new CharacterEntity(R.drawable.deadpool_baby, "Deadpool"));
        mData.add(new CharacterEntity(R.drawable.spiderman_baby, "Spiderman"));

        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);

        mAdapter = new CoverFlowAdapter(this);
        mAdapter.setData(mData);

        mCoverFlow.setAdapter(mAdapter);

        Log.i(TAG, "is Endless: " + mCoverFlow.isEndlessRightNow());

        Log.i(TAG, "Count: " + String.valueOf(mCoverFlow.getCount()));

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO CoverFlow item clicked
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                //TODO CoverFlow stopped to position
            }

            @Override
            public void onScrolling() {
                //TODO CoverFlow began scrolling
            }
        });
    }
}

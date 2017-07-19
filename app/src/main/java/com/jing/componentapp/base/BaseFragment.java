package com.jing.componentapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jing.componentapp.tools.LogTool;

import butterknife.ButterKnife;

/**
 * Created by liujing on 2017/7/18.
 */

public abstract class BaseFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    protected BaseCompatActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogTool.d(TAG, "onAttach");
        activity = (BaseCompatActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTool.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogTool.d(TAG, "onCreateView");
        return inflater.inflate(getLayoutResId(), container, false);
    }

    protected abstract int getLayoutResId();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogTool.d(TAG, "onViewCreated");
        ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogTool.d(TAG, "onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogTool.d(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogTool.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogTool.d(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogTool.d(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogTool.d(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogTool.d(TAG, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogTool.d(TAG, "onDetach");
    }

    public void openActivity(Class<?> cls) {
        openActivity(cls, null, 0);
    }

    public void openActivity(Class<?> cls, int flag) {
        openActivity(cls, null, flag);
    }

    public void openActivity(Class<?> cls, Bundle bundle) {
        openActivity(cls, bundle, 0);
    }

    public void openActivity(Class<?> cls, Bundle bundle, int flag) {
        activity.openActivity(cls, bundle, flag);
    }
}

package com.sixin.police.market.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sixin.police.market.R;
import com.sixin.police.market.base.BaseFragment;
import com.sixin.police.market.utils.AppUtils;
import com.sixin.police.market.widget.RatingBar;
import com.sixin.police.market.widget.submitbutton.SubmitButton;

import butterknife.BindView;

//TODO 按钮动画在转动的时候退出引发的问题
//TODO 给EditText添加小叉
public class AppCommitCommentFragment extends BaseFragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "AppCommitCommentFg";
    private static final String TAG_CANCEL_REQUEST_COMMIT_COMMENT = "tag_cancel_request_commit_comment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Nullable
    @BindView(R.id.btn_submit_comment)
    SubmitButton mBtnSubmitComment;

    @Nullable
    @BindView(R.id.rb_score)
    RatingBar mRbScore;

    @Nullable
    @BindView(R.id.edt_input_comment)
    EditText mEdtInputComment;

    private Handler mHandler = new Handler();

    private float commentScore;

    public AppCommitCommentFragment() {
        // Required empty public constructor
    }

    public static AppCommitCommentFragment newInstance(String param1, String param2) {
        AppCommitCommentFragment fragment = new AppCommitCommentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container) {
        super.initView(inflater, container);
        setContentView(R.layout.fragment_app_commit_comment, inflater, container);
    }

    @Override
    public void lazyLoad() {
        mBtnSubmitComment.setOnClickListener(this);
        mRbScore.setOnRatingBarChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        //TODO 是否要安装了该应用才能够评论
        //TODO 对于评论的内容重复是否允许提交的问题
        //TODO 刷新评论页面的内容
        //TODO 图片修改成webP格式的
        String commentContent = mEdtInputComment.getText().toString();

        if (commentScore <= 0) {
            Toast.makeText(AppUtils.getContext(), getResources().getString(R.string.please_input_comment_score), Toast.LENGTH_SHORT).show();
            mBtnSubmitComment.reset();
            return;
        }

        if (TextUtils.isEmpty(mEdtInputComment.getText().toString())) {
            Toast.makeText(AppUtils.getContext(), getResources().getString(R.string.please_input_comment_conent), Toast.LENGTH_SHORT).show();
            mBtnSubmitComment.reset();
            return;
        }

        if (commentContent.length() > 500) {
            Toast.makeText(AppUtils.getContext(), getResources().getString(R.string.overrun_of_characters), Toast.LENGTH_SHORT).show();
            mBtnSubmitComment.reset();
            return;
        }

        commitCommentInfo();

    }

    private void commitCommentInfo() {
        OkGo.<String>post("http://apis.juhe.cn/idcard/index")
                .tag(TAG_CANCEL_REQUEST_COMMIT_COMMENT)
                .params("cardno", "340222199408203214")
                .params("key", "0ff82212dabb512571c849d9c8944c9b")
                .params("dtype","")
                .execute(new StringCallback() {

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        Log.d(TAG, "---------:" + response.getException().getMessage());
                        toastRequestErrorInfo(response);
                        //判空操作一定要加
                        if (mBtnSubmitComment != null) {
                            mBtnSubmitComment.doResult(false);
                        }
                        resetSubmitButton();

                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(TAG, "-------:" + response.body());
                        if (mBtnSubmitComment != null) {
                            mBtnSubmitComment.doResult(true);
                        }
                        Toast.makeText(AppUtils.getContext(), getResources().getString(R.string.comment_commit_success), Toast.LENGTH_SHORT).show();
                        resetSubmitButton();
                    }
                });

    }

    private void resetSubmitButton() {
        if (mHandler != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mBtnSubmitComment != null) {
                        mBtnSubmitComment.reset();
                    }
                }
            }, 1500);
        }
    }

    @Override
    public void onRatingChanged(RatingBar simpleRatingBar, float rating, boolean fromUser) {
        commentScore = rating;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消网络请求
        OkGo.getInstance().cancelTag(TAG_CANCEL_REQUEST_COMMIT_COMMENT);
        //取消handler中未处理的任务
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}

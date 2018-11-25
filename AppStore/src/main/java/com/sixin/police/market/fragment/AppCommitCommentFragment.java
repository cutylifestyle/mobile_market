package com.sixin.police.market.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sixin.police.market.R;
import com.sixin.police.market.base.BaseFragment;
import com.sixin.police.market.bean.appdetailinfo.appcommentinfo.AppCommitCommentEvent;
import com.sixin.police.market.utils.AppUtils;
import com.sixin.police.market.widget.RatingBar;
import com.sixin.police.market.widget.submitbutton.SubmitButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

//TODO 按钮动画在转动的时候退出引发的问题
//TODO 给EditText添加小叉
public class AppCommitCommentFragment extends BaseFragment implements View.OnClickListener, RatingBar.OnRatingBarChangeListener, TextWatcher {

    private static final String TAG = "AppCommitCommentFg";
    private static final String TAG_CANCEL_REQUEST_COMMIT_COMMENT = "tag_cancel_request_commit_comment";

    @Nullable
    @BindView(R.id.btn_submit_comment)
    SubmitButton mBtnSubmitComment;

    @Nullable
    @BindView(R.id.rb_score)
    RatingBar mRbScore;

    @Nullable
    @BindView(R.id.edt_input_comment)
    EditText mEdtInputComment;

    @Nullable
    @BindView(R.id.tv_number_tip)
    TextView mTvNumberTip;

    private Handler mHandler = new Handler();

    private float commentScore;

    private Response<String> mErrorResponse;

    public AppCommitCommentFragment() {
        // Required empty public constructor
    }

    public static AppCommitCommentFragment newInstance() {
        AppCommitCommentFragment fragment = new AppCommitCommentFragment();
        return fragment;
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
        mEdtInputComment.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        //TODO 是否要安装了该应用才能够评论
        //TODO 对于评论的内容重复是否允许提交的问题
        //TODO 刷新评论页面的内容
        //TODO 修改toast的提示位置
        String commentContent = mEdtInputComment.getText().toString();

        if (commentScore <= 0) {
            toastGravityCenter(getStr(R.string.please_input_comment_score));
            mBtnSubmitComment.reset();
            return;
        }

        if (TextUtils.isEmpty(mEdtInputComment.getText().toString())) {
            toastGravityCenter(getStr(R.string.please_input_comment_conent));
            mBtnSubmitComment.reset();
            return;
        }

        if (commentContent.length() > 500) {
            toastGravityCenter(getStr(R.string.overrun_of_characters));
            mBtnSubmitComment.reset();
            return;
        }

        commitCommentInfo();

    }

    private void commitCommentInfo() {
        //TODO 这个控件存在BUG
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
                        mErrorResponse = response;
                        //判空操作一定要加
                        if (mBtnSubmitComment != null) {
                            mBtnSubmitComment.doResult(false);
                        }
                        resetSubmitButton(false);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d(TAG, "-------:" + response.body());
                        //这里之所以将一些UI方面的重置放置到resetSubmitButton方法中，
                        //因为这些操作会导致UI卡顿
                        if (mBtnSubmitComment != null) {
                            mBtnSubmitComment.doResult(true);
                        }
                        resetSubmitButton(true);
                    }
                });

    }

    /**
     * @param needExtraOperation 是否需要进行额外的操作，除了重置按钮的状态
     * */
    private void resetSubmitButton(final boolean needExtraOperation) {
        if (mHandler != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mBtnSubmitComment != null) {
                        mBtnSubmitComment.reset();
                    }
                    if (needExtraOperation) {
                        //通知评论页面刷新数据
                        AppCommitCommentEvent appCommitCommentEvent = new AppCommitCommentEvent();
                        appCommitCommentEvent.setRefresh(true);
                        EventBus.getDefault().post(appCommitCommentEvent);

                        if (mEdtInputComment != null) {
                            mEdtInputComment.setText(null);
                        }
                        if (mRbScore != null) {
                            mRbScore.setRating(0);
                        }
                        toastGravityCenter(getStr(R.string.comment_commit_success));
                    }else{
                        toastRequestErrorInfo(mErrorResponse);
                    }
                }
            }, 500);
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int textLength = charSequence.toString().length();
        if (textLength > 500) {
            mTvNumberTip.setTextColor(Color.RED);
        }else{
            mTvNumberTip.setTextColor(getResources().getColor(R.color.sky_blue));
        }
        mTvNumberTip.setText(textLength+getString(R.string.max_char_numbers));
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

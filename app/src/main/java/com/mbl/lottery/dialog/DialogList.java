package com.mbl.lottery.dialog;


import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.core.utils.RecyclerUtils;
import com.mbl.lottery.R;
import com.mbl.lottery.adapter.PickerAdapter;
import com.mbl.lottery.callback.DialogCallback;
import com.mbl.lottery.model.ItemViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogList extends Dialog {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    PickerAdapter mAdapter;
    private DialogCallback mDelegate;
    private List<ItemViewModel> mList;

    public DialogList(@NonNull Context context, String title, List<ItemViewModel> list, DialogCallback callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = View.inflate(getContext(), R.layout.dialog_list, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        mDelegate = callback;
        mList = list;
        tvTitle.setText(title);
        mAdapter = new PickerAdapter(getContext(), mList) {
            @Override
            public void onBindViewHolder(@NonNull PickerAdapter.HolderView holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(v -> {
                    mDelegate.onClickItem(mAdapter.getListFilter().get(position));
                    dismiss();
                });
            }
        };
        RecyclerUtils.setupVerticalRecyclerView(getContext(), recyclerView);
        recyclerView.setAdapter(mAdapter);

    }



    @Override
    public void show() {
        super.show();
    }

    @OnClick({R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                dismiss();
                break;

        }
    }
}
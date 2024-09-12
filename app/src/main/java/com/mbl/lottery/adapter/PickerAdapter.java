package com.mbl.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mbl.lottery.R;
import com.mbl.lottery.model.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.HolderView> implements Filterable {
    private List<ItemViewModel> mList;
    private List<ItemViewModel> mListFilter;
    private Context mContext;

    public PickerAdapter(Context context, List<ItemViewModel> items) {
        mList = items;
        mListFilter = items;
        mContext = context;
    }

    public void setListFilter(List<ItemViewModel> list) {
        mListFilter = list;
    }

    public List<ItemViewModel> getListFilter() {
        return mListFilter;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListFilter = mList;
                } else {
                    List<ItemViewModel> filteredList = new ArrayList<>();
                    for (ItemViewModel row : mList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getText().toLowerCase().contains(charString.toLowerCase())
                                || row.getValue().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mListFilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListFilter = (List<ItemViewModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PickerAdapter.HolderView(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holder, int position) {
        holder.bindView(mListFilter.get(position));
    }

    @Override
    public int getItemCount() {
        return mListFilter.size();
    }

    public class HolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_reason)
        TextView tvReason;

        HolderView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public ItemViewModel getItem(int position) {
            return mListFilter.get(position);
        }

        public void bindView(Object model) {
            ItemViewModel item = (ItemViewModel) model;
            tvReason.setText(item.getText());
        }
    }
}

package com.mbl.lottery.printer.together.add;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.core.base.log.Logger;
import com.core.base.viper.ViewFragment;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mbl.lottery.BuildConfig;
import com.mbl.lottery.R;
import com.mbl.lottery.model.DrawModel;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.model.LineModel;
import com.mbl.lottery.model.ProductModel;
import com.mbl.lottery.model.request.TogetherTicketAddRequest;
import com.mbl.lottery.model.response.TogetherTicketSearchResponse;
import com.mbl.lottery.printer.detail.DetailFragment;
import com.mbl.lottery.utils.BitmapUtils;
import com.mbl.lottery.utils.Constants;
import com.mbl.lottery.utils.DialogHelper;
import com.mbl.lottery.utils.DrawViewUtils;
import com.mbl.lottery.utils.MediaUltis;
import com.mbl.lottery.utils.NumberUtils;
import com.mbl.lottery.utils.SharedPref;
import com.mbl.lottery.utils.Toast;
import com.mbl.lottery.utils.Utils;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.internal.Util;

public class AddTogetherFragment extends ViewFragment<AddTogetherContract.Presenter> implements AddTogetherContract.View {
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.tv_count)
    TextView tv_count;
    @BindView(R.id.tv_product)
    TextView tv_product;
    @BindView(R.id.tv_system)
    TextView tv_system;
    @BindView(R.id.tv_draw)
    TextView tv_draw;
    @BindView(R.id.tv_amount)
    TextView tv_amount;
    @BindView(R.id.btn_ok)
    Button btn_ok;
    @BindView(R.id.ll_product)
    LinearLayout ll_product;
    @BindView(R.id.ll_bag)
    LinearLayout ll_bag;
    @BindView(R.id.image_before)
    public SimpleDraweeView image_before;
    private LineAdapter mLineAdapterA;
    private List<LineModel> mLineModels;
    private List<LineModel> mReturnLineModels;
    public int mCount = 45;
    int mBag = 7;
    int productID = Constants.PRODUCT_MEGA;
    public int mSelectCount = 0;
    DrawModel mDrawModel;
    String fileName = "";
    EmployeeModel employeeModel;
    int price = 0;
    String numberOfLine = "";
    String mode = "LOAD";

    public static AddTogetherFragment getInstance() {
        return new AddTogetherFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_together;
    }

    @Override
    public void initLayout() {
        super.initLayout();
        mLineModels = new ArrayList<>();

        SharedPref sharedPref = new SharedPref(getActivity());
        employeeModel = sharedPref.getEmployeeModel();

        Intent intent = getActivity().getIntent();

        TogetherTicketSearchResponse ticket = (TogetherTicketSearchResponse) intent.getSerializableExtra(Constants.ORDER_MODEL);
        if (ticket != null) {
            mode = "VIEW";
            tv_amount.setText(NumberUtils.formatPriceNumber(ticket.getPrice()));
            productID = ticket.getProductID();
            mBag = ticket.getSystematic();
            if (ticket.getProductID() == Constants.PRODUCT_MEGA) {
                mCount = 45;
                tv_product.setText("Mega 6/45");
            } else {
                mCount = 55;
                tv_product.setText("Power 6/55");
            }
            for (int i = 1; i < mCount + 1; i++) {
                LineModel lineModel = new LineModel();
                if (ticket.getNumberOfLines().contains(Utils.padLeft(i))) {
                    lineModel.setSelected(true);
                } else {
                    lineModel.setSelected(false);
                }
                lineModel.setLine(Utils.padLeft(i));
                mLineModels.add(lineModel);
            }
            tv_system.setText("Bao " + ticket.getSystematic());
            mDrawModel = new DrawModel();
            mDrawModel.setDrawCode(ticket.getDrawCode());
            mDrawModel.setDrawDate(ticket.getDrawDate());
            tv_draw.setText("Kỳ: #" + mDrawModel.getDrawCode() + " - " + mDrawModel.getDrawDate());
            Uri imageuri = Uri.parse(BuildConfig.IMAGE_BROWSER_URL + ticket.getImgBefore());
            image_before.setImageURI(imageuri);
//            Picasso
//                    .get()
//                    .load(BuildConfig.IMAGE_URL + mItemModels.get(mIndexItem).getImgBefore())
//                    .into(image_before);
            if(ticket.getQuantity() > 0){
                btn_ok.setVisibility(View.GONE);
            }
        } else {
            mLineModels = Utils.initLine(mCount);
            mReturnLineModels = new ArrayList<>();
            price = Utils.getAmountMegaBySystematic(mBag);
            mPresenter.getDrawMega();
        }

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);
        recycle.setLayoutManager(flexboxLayoutManager);
        mLineAdapterA = new LineAdapter(getContext(), mLineModels) {
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(view1 -> {
                    LineModel lineModel = mLineModels.get(position);
                    boolean isCheck = ArrayUtils.contains(mReturnLineModels.toArray(), lineModel);
                    if (mSelectCount != mBag) {
                        if (!isCheck) {
                            ((HolderView) holder).tvNumber.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.line_text_cycle_bg));
                            ((HolderView) holder).tvNumber.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                            mSelectCount += 1;
                            tv_count.setText("Bạn đã chọn " + mSelectCount + "/" + mBag + " bộ số");

                            mReturnLineModels.add(lineModel);
                        } else {
                            ((HolderView) holder).tvNumber.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.line_text_cycle));
                            ((HolderView) holder).tvNumber.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                            mSelectCount -= 1;
                            tv_count.setText("Bạn đã chọn " + mSelectCount + "/" + mBag + " bộ số");
                            mReturnLineModels.remove(lineModel);
                        }
                    } else {
                        if (isCheck) {
                            ((HolderView) holder).tvNumber.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.line_text_cycle));
                            ((HolderView) holder).tvNumber.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                            mSelectCount -= 1;
                            tv_count.setText("Bạn đã chọn " + mSelectCount + "/" + mBag + " bộ số");
                            mReturnLineModels.remove(lineModel);
                        }
                    }
                });
            }

        };
        recycle.setAdapter(mLineAdapterA);
    }

    @OnClick({R.id.btn_ok, R.id.iv_back, R.id.ll_product, R.id.ll_bag, R.id.image_before})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                ok();
                break;
            case R.id.iv_back:
                mPresenter.back();
                break;
            case R.id.ll_product:
                pickProduct();
                break;
            case R.id.ll_bag:
                pickBag();
                break;
            case R.id.image_before:
                capturePermission();
                break;
        }
    }

    void ok() {
        if (fileName.isEmpty()) {
            Toast.showToast(getContext(), "Bạn chưa cập nhật ảnh vé");
            return;
        }
        if (mDrawModel == null) {
            Toast.showToast(getContext(), "Không lấy được thông tin kỳ quay số mở thưởng");
            return;
        }

        TogetherTicketAddRequest req = new TogetherTicketAddRequest();
        req.setCreatedID(employeeModel.getiD());
        req.setDrawCode(mDrawModel.getDrawCode());
        req.setDrawDate(mDrawModel.getDrawDate());
        req.setProductID(productID);
        req.setImgBefore(fileName);
        req.setSystematic(mBag);
        req.setPrice(price);
        req.setNumberOfLines(numberOfLine);
        mPresenter.addTogether(req);
    }


    @Override
    public void showImage(String file) {
        fileName = file;
    }

    @Override
    public void showDraw(List<DrawModel> drawModels) {
        if (drawModels.size() > 0) {
            mDrawModel = drawModels.get(0);
            tv_draw.setText("Kỳ: #" + mDrawModel.getDrawCode() + " - " + mDrawModel.getDrawDate());
        }
    }

    private void pickProduct() {
        List<ProductModel> productModels = new ArrayList<>();

        ProductModel productModel = new ProductModel();

        productModel = new ProductModel();
        productModel.setId(Constants.PRODUCT_POWER);
        productModel.setName(Utils.getProductName(Constants.PRODUCT_POWER));
        productModels.add(productModel);

        productModel = new ProductModel();
        productModel.setId(Constants.PRODUCT_MEGA);
        productModel.setName(Utils.getProductName(Constants.PRODUCT_MEGA));
        productModels.add(productModel);

        PopupMenu popupMenu = new PopupMenu(requireActivity(), ll_product, Gravity.END);

        for (ProductModel productModel1 : productModels) {
            popupMenu.getMenu().add(1, productModel1.getId(), 0, productModel1.getName());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String value = item.getTitle().toString();
                tv_product.setText(value);
                productID = item.getItemId();
                if (productID == Constants.PRODUCT_MEGA) {
                    price = Utils.getAmountMegaBySystematic(mBag);
                    mPresenter.getDrawMega();
                    mCount = 45;
                } else {
                    price = Utils.getAmountPowerBySystematic(mBag);
                    mPresenter.getDrawPower();
                    mCount = 55;
                }

                mSelectCount = 0;
                tv_count.setText("Bạn đã chọn " + mSelectCount + "/" + mBag + " bộ số");

                tv_amount.setText(NumberUtils.formatPriceNumber(price));
                mLineModels.clear();
                mLineModels = Utils.initLine(mCount);
                mLineAdapterA.clear();
                mLineAdapterA.addItems(mLineModels);
                return true;
            }
        });

        popupMenu.show();
    }

    private void pickBag() {
        List<ProductModel> productModels = new ArrayList<>();

        ProductModel productModel = new ProductModel();

        productModel = new ProductModel();
        productModel.setId(5);
        productModel.setName("Bao 5");
        productModels.add(productModel);

        for (int i = 7; i < 16; i++) {
            productModel = new ProductModel();
            productModel.setId(i);
            productModel.setName("Bao " + i);
            productModels.add(productModel);
        }

        productModel = new ProductModel();
        productModel.setId(18);
        productModel.setName("Bao 18");
        productModels.add(productModel);

        PopupMenu popupMenu = new PopupMenu(requireActivity(), ll_bag, Gravity.END);

        for (ProductModel productModel1 : productModels) {
            popupMenu.getMenu().add(1, productModel1.getId(), 0, productModel1.getName());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String value = item.getTitle().toString();
                tv_system.setText(value);

                mBag = item.getItemId();
                mSelectCount = 0;
                if (productID == Constants.PRODUCT_MEGA) {
                    price = Utils.getAmountMegaBySystematic(mBag);
                } else {
                    price = Utils.getAmountPowerBySystematic(mBag);
                }
                tv_amount.setText(NumberUtils.formatPriceNumber(price));
                tv_count.setText("Bạn đã chọn " + mSelectCount + "/" + mBag + " bộ số");
                return true;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                attemptSendMedia(data.getData().getPath());
            }
        }
    }

    @SuppressLint("CheckResult")
    private void attemptSendMedia(String path_media) {
        File file = new File(path_media);
        Uri picUri = Uri.fromFile(new File(path_media));
        List<LineModel> lineModels = new ArrayList<>();
        image_before.setImageURI(picUri);
        List<LineModel> lineModels1 = Utils.sortLine(mReturnLineModels);
        if (mReturnLineModels.size() == mBag) {
            numberOfLine = "";
            for (int i = 0; i < lineModels1.size(); i++) {
                if (numberOfLine.isEmpty()) {
                    numberOfLine = lineModels1.get(i).getLine();
                } else {
                    numberOfLine = numberOfLine + "," + lineModels1.get(i).getLine();
                }
            }
            LineModel item = new LineModel();
            item.setLine(numberOfLine);
            lineModels.add(item);
        } else {
            Toast.showToast(getContext(), "Bạn chưa chọn đủ bộ số. Số bộ số cần chọn: " + mBag);
            return;
        }

        Observable.fromCallable(() -> {
                    Uri uri = Uri.fromFile(new File(path_media));
                    DrawViewUtils drawViewUtils = new DrawViewUtils(getContext());

                    return drawViewUtils.processingBitmapBefore(uri,
                            Utils.getProductName(productID) + " Bao " + mBag,
                            lineModels,
                            mDrawModel.getDrawDate(),
                            mDrawModel.getDrawCode(), "");

                }).subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .map(bitmap ->
                        BitmapUtils.saveImage(bitmap, file.getParent(), "lkl_" + file.getName(), Bitmap.CompressFormat.JPEG, 50)
                )
                .observeOn(AndroidSchedulers.mainThread()).subscribe(
                        isSavedImage -> {
                            String path = file.getParent() + File.separator + "lkl_" + file.getName();
                            String type;
                            type = Constants.IMAGE_BEFORE;

                            mPresenter.postImage(path);
                            if (file.exists())
                                file.delete();
                        },
                        onError -> Logger.e("error save image")
                );
    }

    private void capturePermission() {
        CharSequence info;
        boolean IsAmount = false;

        List<LineModel> lineModels = new ArrayList<>();
        String draw = "Kỳ #" + mDrawModel.getDrawCode() + ", Ngày quay:" + mDrawModel.getDrawDate();
        List<LineModel> lineModels1 = Utils.sortLine(mReturnLineModels);
        if (mReturnLineModels.size() == mBag) {
            numberOfLine = "";
            for (int i = 0; i < lineModels1.size(); i++) {
                if (numberOfLine.isEmpty()) {
                    numberOfLine = lineModels1.get(i).getLine();
                } else {
                    numberOfLine = numberOfLine + "," + lineModels1.get(i).getLine();
                }
            }
            LineModel item = new LineModel();
            item.setLine(numberOfLine);
            lineModels.add(item);
        } else {
            Toast.showToast(getContext(), "Bạn chưa chọn đủ bộ số. Số bộ số cần chọn: " + mBag);
            return;
        }

        info = Utils.getInfoImageBefore(requireActivity(), lineModels, Utils.getProductName(productID) + " Bao " + mBag, draw, IsAmount);


        PermissionUtils.permission(PermissionConstants.CAMERA)
                .rationale(DialogHelper::showRationaleDialog)
                .callback(new PermissionUtils.FullCallback() {
                    @Override
                    public void onGranted(List<String> permissionsGranted) {
                        MediaUltis.captureImage(AddTogetherFragment.this, info, "", false);
                    }

                    @Override
                    public void onDenied(List<String> permissionsDeniedForever,
                                         List<String> permissionsDenied) {
                        if (!permissionsDeniedForever.isEmpty()) {
                            DialogHelper.showOpenAppSettingDialog();
                        }
                    }
                })
                .request();
    }
}

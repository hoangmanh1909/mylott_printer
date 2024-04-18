package com.mbl.lottery.home.drawresult;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.core.base.viper.ViewFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mbl.lottery.R;
import com.mbl.lottery.login.LoginFragment;
import com.mbl.lottery.model.request.AddDrawResultRequest;
import com.mbl.lottery.utils.Toast;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFragment  extends ViewFragment<AddContract.Presenter> implements AddContract.View {
    @BindView(R.id.number1)
    TextInputEditText number1;
    @BindView(R.id.number2)
    TextInputEditText number2;
    @BindView(R.id.number3)
    TextInputEditText number3;
    @BindView(R.id.number4)
    TextInputEditText number4;
    @BindView(R.id.number5)
    TextInputEditText number5;
    @BindView(R.id.number6)
    TextInputEditText number6;
    @BindView(R.id.number7)
    TextInputEditText number7;
    @BindView(R.id.bonus)
    TextInputLayout bonus;
    @BindView(R.id.tv_title)
    TextView tv_title;

    int product = 1;

    public static AddFragment getInstance() {
        return new AddFragment();
    }

    @Override
    public void initLayout() {
        super.initLayout();
        product = mPresenter.getProduct();
        if(product == 2){
            bonus.setVisibility(View.VISIBLE);
            tv_title.setText("Nhập kết quả Power");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_draw_result;
    }

    @OnClick({R.id.btn_add})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_add:
                add();
                break;
        }
    }

    void add(){
        String n1 = number1.getText().toString();
        String n2 = number2.getText().toString();
        String n3 = number3.getText().toString();
        String n4 = number4.getText().toString();
        String n5 = number5.getText().toString();
        String n6 = number6.getText().toString();
        String n7 = number7.getText().toString();
        if(TextUtils.isEmpty(n1) || TextUtils.isEmpty(n2) || TextUtils.isEmpty(n3) || TextUtils.isEmpty(n4)
                || TextUtils.isEmpty(n5) || TextUtils.isEmpty(n6)){
            Toast.showToast(getContext(),"Vui lòng nhập đủ bộ số");
            return;
        }
        if(product == 2 && TextUtils.isEmpty(n7)){
            Toast.showToast(getContext(),"Vui lòng nhập bộ số power 2");
            return;
        }
        AddDrawResultRequest req = new AddDrawResultRequest();
        String result = n1 + "," + n2 + "," + n3+ "," + n4+ "," + n5+ "," + n6;
        req.setResult(result);
        req.setProductID(product);
        req.setBonus(number7.getText().toString());
        mPresenter.resultAdd(req);
    }

    @Override
    public void showSuccess() {

    }
}

package com.bwei.iloginorregui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.adapter.SeekAdapter;
import com.bwei.iloginorregui.bean.Result;
import com.bwei.iloginorregui.bean.sbean.FindCommodityListByLabel;
import com.bwei.iloginorregui.core.DataCall;
import com.bwei.iloginorregui.core.exception.ApiException;
import com.bwei.iloginorregui.presenter.SeekPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeekActivity extends AppCompatActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.sangang)
    ImageView sangang;
    @BindView(R.id.soushuokuang)
    EditText soushuokuang;
    @BindView(R.id.soushuo)
    TextView soushuo;
    @BindView(R.id.xrecycler_view)
    XRecyclerView xrecyclerView;
    private SeekAdapter adapter;
    private SeekPresenter seekPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);
        ButterKnife.bind(this);
        seekPresenter = new SeekPresenter(new ListLable());
        xrecyclerView.setLoadingListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        xrecyclerView.setLayoutManager(gridLayoutManager);
        adapter = new SeekAdapter(this);
        xrecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new SeekAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int cid) {
                Toast.makeText(getBaseContext(), cid + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SeekActivity.this, DetailsActivity.class);
                intent.putExtra("goodsId", cid);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.sangang, R.id.soushuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sangang:
                break;
            case R.id.soushuo:
                seekPresenter.reqeust(soushuokuang.getText().toString(), 1, 5);
                break;
        }
    }

    @Override
    public void onRefresh() {
//        if (seekPresenter.isRuning()) {
//            xrecyclerView.refreshComplete();
//            return;
//        }
//        seekPresenter.reqeust(true,soushuokuang.getText().toString());
    }

    @Override
    public void onLoadMore() {
//        if (seekPresenter.isRuning()) {
//            xrecyclerView.loadMoreComplete();
//            return;
//        }
//        seekPresenter.reqeust(false,soushuokuang.getText().toString());
    }

    private class ListLable implements DataCall<Result<List<FindCommodityListByLabel>>> {

        @Override
        public void success(Result<List<FindCommodityListByLabel>> result) {
            adapter.addItem(result.getResult());
            adapter.notifyDataSetChanged();
        }

        @Override
        public void fail(ApiException e) {

        }
    }


}

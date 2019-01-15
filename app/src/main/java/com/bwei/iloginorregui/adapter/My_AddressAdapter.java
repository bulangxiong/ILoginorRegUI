package com.bwei.iloginorregui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwei.iloginorregui.R;
import com.bwei.iloginorregui.bean.dbean.My_AddessBean;

import java.util.ArrayList;
import java.util.List;

public class My_AddressAdapter extends RecyclerView.Adapter<My_AddressAdapter.VH> {
    private List<My_AddessBean> list=new ArrayList<>();
    private Context context;

    public My_AddressAdapter(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
    public void addItem(List<My_AddessBean> beans){
        if (beans!=null){
            list.addAll(beans);
        }
    }
    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.me_item_addess, viewGroup, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        My_AddessBean bean = list.get(i);
        vh.me_addess_name.setText(bean.getRealName());
        vh.me_addess_phone.setText(bean.getPhone());
        vh.me_address_dizi.setText(bean.getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends RecyclerView.ViewHolder{

        private final TextView me_addess_name;
        private final TextView me_addess_phone;
        private final TextView me_address_dizi;
        private final CheckBox me_address_morendiz;
        private final TextView me_address_delete;
        private final TextView me_address_update;

        public VH(@NonNull View itemView) {
            super(itemView);
            me_addess_name = itemView.findViewById(R.id.me_addess_name);
            me_addess_phone = itemView.findViewById(R.id.me_addess_phone);
            me_address_dizi = itemView.findViewById(R.id.me_address_dizi);
            me_address_delete = itemView.findViewById(R.id.me_address_delete);
            me_address_morendiz = itemView.findViewById(R.id.me_address_morendiz);
            me_address_update = itemView.findViewById(R.id.me_address_update);
        }
    }
}

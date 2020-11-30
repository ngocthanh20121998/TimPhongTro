package com.thanh.timphongtro;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class AdapterInfoPT extends BaseAdapter {
    private final Context context;
    private final int layout;
    private final ArrayList<InfoPhongTro> listPT;


    public AdapterInfoPT(Context context, int layout, ArrayList<InfoPhongTro> listPT) {
        this.context = context;
        this.layout = layout;
        this.listPT = listPT;
    }

    @Override
    public int getCount() {
        return listPT.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(layout,null);
        TextView tvTieuDe = convertView.findViewById(R.id.textViewTieuDe);
        TextView tvGia = convertView.findViewById(R.id.textViewGia);
        TextView tvDienTich = convertView.findViewById(R.id.textViewDienTich);

        InfoPhongTro infoPhongTro = listPT.get(position);
        tvTieuDe.setText(infoPhongTro.tieuDe);
        tvGia.setText(String.format("Giá: %s Đồng",infoPhongTro.giaPhong));
        tvDienTich.setText(String.format("Diện tích: %s m2",infoPhongTro.dienTich));

        return convertView;
    }
}

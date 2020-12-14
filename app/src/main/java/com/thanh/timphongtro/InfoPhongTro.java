package com.thanh.timphongtro;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class InfoPhongTro implements Parcelable {
    public Long id;
    public String idUser;
    public String hinh;
    public String tieuDe;
    public String giaPhong;
    public String dienTich;
    public String sdt;
    public String diaChi;
    public String moTa;

    public InfoPhongTro(){
    }

    public InfoPhongTro(Long id, String idUser, String hinh, String tieuDe, String giaPhong, String dienTich, String sdt, String diaChi, String moTa) {
        this.id = id;
        this.idUser = idUser;
        this.hinh = hinh;
        this.tieuDe = tieuDe;
        this.giaPhong = giaPhong;
        this.dienTich = dienTich;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.moTa = moTa;
    }

    protected InfoPhongTro(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        idUser = in.readString();
        hinh = in.readString();
        tieuDe = in.readString();
        giaPhong = in.readString();
        dienTich = in.readString();
        sdt = in.readString();
        diaChi = in.readString();
        moTa = in.readString();
    }

    public static final Creator<InfoPhongTro> CREATOR = new Creator<InfoPhongTro>() {
        @Override
        public InfoPhongTro createFromParcel(Parcel in) {
            return new InfoPhongTro(in);
        }

        @Override
        public InfoPhongTro[] newArray(int size) {
            return new InfoPhongTro[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(idUser);
        dest.writeString(hinh);
        dest.writeString(tieuDe);
        dest.writeString(giaPhong);
        dest.writeString(dienTich);
        dest.writeString(sdt);
        dest.writeString(diaChi);
        dest.writeString(moTa);
    }
}

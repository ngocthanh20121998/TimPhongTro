package com.thanh.timphongtro;

import java.util.Date;

public class InfoPhongTro {
    public Long id;
    public String idUser;
    public String hinh;
    public String tieuDe;
    public String giaPhong;
    public String dienTich;
    public String sdt;
    public String diaChi;
    public String moTa;
    public Date ngayDang;

    public InfoPhongTro(){
    }

    public InfoPhongTro(Long id, String idUser, String hinh, String tieuDe, String giaPhong, String dienTich, String sdt, String diaChi, String moTa, Date ngayDang) {
        this.id = id;
        this.idUser = idUser;
        this.hinh = hinh;
        this.tieuDe = tieuDe;
        this.giaPhong = giaPhong;
        this.dienTich = dienTich;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.moTa = moTa;
        this.ngayDang = ngayDang;
    }
}

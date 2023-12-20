/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rev.projectuaspbol;

import java.util.ArrayList;

/**
 *
 * @author adyat
 */
public class Peserta {
    private String noIdentitas;
    private String nama;
    private String nim;
    private String password;
    private String alamat;
    private String tipe;
    private double tagihan;
    private ArrayList <Matakuliah> matkulList;

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoIdentitas() {
        return noIdentitas;
    }

    public void setNoIdentitas(String noIdentitas) {
        this.noIdentitas = noIdentitas;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getTagihan() {
        return tagihan;
    }

    public void setTagihan(double tagihan) {
        this.tagihan = tagihan;
    }

    public ArrayList<Matakuliah> getMatkulList() {
        return matkulList;
    }

    public void setMatkulList(ArrayList<Matakuliah> matkulList) {
        this.matkulList = matkulList;
    }

    public Peserta(String noIdentitas, String nama) {
        this.noIdentitas = noIdentitas;
        this.nama = nama;
    }

    public Peserta(String noId, String nama, String nim, String password, String alamat, String tipe) {
        this.noIdentitas = noId;
        this.nama = nama;
        this.nim = nim;
        this.password = password;
        this.alamat = alamat;
        this.tipe = tipe;
    }

    @Override
    public String toString() {
        return "Peserta{" + "nama=" + nama + ", nim=" + nim + ", password=" + password + ", alamat=" + alamat + ", tipe=" + tipe + '}';
    }
    
    
    
    
}

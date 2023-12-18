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
public abstract class Matakuliah {
    String nama;
    String kode;
    ArrayList<Peserta> peserta;
    public Matakuliah(String nama, String kode) {
        this.nama = nama;
        this.kode = kode;
    }
}



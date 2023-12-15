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

    public Matakuliah(String nama, String kode) {
        this.nama = nama;
        this.kode = kode;
    }
}

class Pad extends Matakuliah {
    ArrayList<Peserta> peserta;

    public Pad(String nama, String kode) {
        super(nama, kode);
        this.peserta  = new ArrayList<>();
    }
}

class Alpro extends Matakuliah {
    ArrayList<Peserta> peserta;

    public Alpro(String nama, String kode) {
        super(nama, kode);
        this.peserta  = new ArrayList<>();
    }
}

class Sdnl extends Matakuliah {
    ArrayList<Peserta> peserta;

    public Sdnl(String nama, String kode) {
        super(nama, kode);
        this.peserta  = new ArrayList<>();
    }
}

class IntApp extends Matakuliah {
    ArrayList<Peserta> peserta;

    public IntApp(String nama, String kode) {
        super(nama, kode);
        this.peserta  = new ArrayList<>();
    }
}

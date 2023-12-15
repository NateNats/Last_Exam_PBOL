/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rev.projectuaspbol;

/**
 *
 * @author adyat
 */
public abstract class Peserta {
    String noIdentitas;
    String nama;
    double tagihan;

    public Peserta(String noIdentitas, String nama) {
        this.noIdentitas = noIdentitas;
        this.nama = nama;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rev.projectuaspbol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author adyat
 */
public class dataHandler {

    String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE";
    String userid = "hr";
    String password = "vito123";

    Connection conn;

    public void getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(jdbcUrl, userid, password);
            System.out.println("Koneksi berhasil");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(dataHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Tidak bisa tutup koneksi");
        }
    }

    public void tambahDataPeminjam(Peserta dataPeserta) {
        try {
            getConnection();

            String query = "INSERT INTO PESERTA (PESERTAID,NIMPESERTA,NAMAPESERTA,ALAMATPESERTA,PASSWORDPESERTA,TIPEPESERTA) VALUES (?, ?, ?, ?,?,?)";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, generateRandomId(4));
                preparedStatement.setString(2, dataPeserta.getNim());
                preparedStatement.setString(3, dataPeserta.getNama());
                preparedStatement.setString(4, dataPeserta.getAlamat());
                preparedStatement.setString(5, dataPeserta.getPassword());
                preparedStatement.setString(6, dataPeserta.getTipe());

                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "AKUN BERHASIL DITAMBAHKAN");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public ResultSet cariPeserta(String nim, String password) {
        try {
            getConnection();
            String query = "SELECT * FROM PESERTA WHERE NIMPESERTA = ? AND PASSWORDPESERTA = ?";

            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, password);
                
                ResultSet hasil = preparedStatement.executeQuery();
                return hasil;
            }
        } catch (SQLException ex) {
            
        }
        
        return null;
    }

    public static String generateRandomId(int length) {
        String characters = "0123456789";
        StringBuilder randomId = new StringBuilder("P");

        Random random = new Random();
        for (int i = 1; i < length; i++) {  // Mulai dari indeks 1 karena indeks 0 sudah diisi "P"
            int index = random.nextInt(characters.length());
            randomId.append(characters.charAt(index));
        }

        return randomId.toString();
    }

    public static void main(String[] args) throws SQLException {
        dataHandler dh = new dataHandler();
        dh.getConnection();
        System.out.println(generateRandomId(4));

    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import beans.Marca;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class MarcaDAO {
    
    public static List<Marca> getMarcas() {
        List<Marca> list = new ArrayList<>();
        SqlSession conn = null;
        
        try {
            conn = MyBatisUtils.getSession();
            list = conn.selectList("Marca.getMarcas");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        
        return list;
    }

    public static List<Marca> getMarcasPorNombre(String nombre) {
        List<Marca> list = new ArrayList<>();
        
        if (nombre != null && !nombre.isEmpty()) {
            SqlSession conn = null;
            try {
                conn = MyBatisUtils.getSession();
                list = conn.selectList("Marca.buscarPorNombre", nombre);
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        }
        
        return list;
    }

    public static int registrarMarca(String nombre) {
        int filas = 0;
        
        if (nombre != null && !nombre.isEmpty()) {
            SqlSession conn = null;
            try {
                conn = MyBatisUtils.getSession();
                filas = conn.insert("Marca.registrar", nombre);
                conn.commit(); // MUY IMPORTANTE... INSERT, UPDATE, DELETE
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.close();
                }
            }
        }
        
        return filas;
    }
    
}

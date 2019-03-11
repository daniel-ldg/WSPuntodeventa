package model.dao;

import beans.Cliente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;

public class ClienteDAO {
    
    public static List<Cliente> buscarTodos() {
        List<Cliente> list = new ArrayList<>();
        try (SqlSession conn = MyBatisUtils.getSession()) {
            list = conn.selectList("Cliente.buscarTodos");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<Cliente> buscarPorNombre(String nombre) {
        List<Cliente> list = new ArrayList<>();
        try (SqlSession conn = MyBatisUtils.getSession()) {
            list = conn.selectList("Cliente.buscarPorNombre", nombre);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}

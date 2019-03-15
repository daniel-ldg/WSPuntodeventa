package model.dao;

import beans.Cliente;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
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
    
    private static String genToken() {
        return String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
    }

    public static int registrarCliente(String nombre, String email, String contraseña, String direccion, String telefono, String usuario) {
        int filasAfectadas = 0;
        if (nombre != null && email != null && contraseña != null && direccion != null && telefono != null && usuario != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                HashMap<String, Object> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("email", email);
                params.put("contraseña", contraseña);
                params.put("direccion", direccion);
                params.put("telefono", telefono);
                params.put("usuario", usuario);
                params.put("token", genToken());
                filasAfectadas = conn.insert("Cliente.registrarClienteMap", params);
                conn.commit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return filasAfectadas;
    }
    
    public static int registrarCliente(Cliente cliente) {
        int filasAfectadas = 0;
        if (cliente != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                cliente.setToken(genToken());
                filasAfectadas = conn.insert("Cliente.registrarClienteObject", cliente);
                conn.commit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return filasAfectadas;
    }
}

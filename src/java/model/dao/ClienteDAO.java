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

    public static int registrarCliente(String nombre, String email, String contraseña,
            String direccion, String telefono, String usuario, String token) {
        int filasAfectadas = 0;
        if (nombre != null && email != null && contraseña != null && direccion != null
                && telefono != null && usuario != null && token != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                HashMap<String, Object> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("email", email);
                params.put("contraseña", contraseña);
                params.put("direccion", direccion);
                params.put("telefono", telefono);
                params.put("usuario", usuario);
                params.put("token", token);
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
                filasAfectadas = conn.insert("Cliente.registrarClienteObject", cliente);
                conn.commit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return filasAfectadas;
    }
    
    public static boolean sePuedeRegistrarTelefono(Cliente cliente) {
        boolean resultado = false;
        if (cliente != null && cliente.getTelefono() != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                resultado = conn.selectOne("Cliente.sePuedeRegistrarTelefono", cliente);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }
    
    public static boolean sePuedeRegistrarUsuario(Cliente cliente) {
        boolean resultado = false;
        if (cliente != null && cliente.getUsuario() != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                resultado = conn.selectOne("Cliente.sePuedeRegistrarUsuario", cliente.getUsuario());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }
    
    public static boolean existeIdCliente(Integer idCliente) {
        boolean resultado = false;
        if (idCliente != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                resultado = conn.selectOne("Cliente.existeIdCliente", idCliente);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }
    
    public static boolean sePuedeActualizarTelefono(Cliente cliente) {
        boolean resultado = false;
        if (cliente != null && cliente.getTelefono() != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                resultado = conn.selectOne("Cliente.sePuedeActualizarTelefono", cliente);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }
    
    public static boolean sePuedeActualizarUsuario(Cliente cliente) {
        boolean resultado = false;
        if (cliente != null && cliente.getUsuario() != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                resultado = conn.selectOne("Cliente.sePuedeActualizarUsuario", cliente);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }

    public static int actualizarCliente(Cliente cliente) {
        int filasAfectadas = 0;
        if (cliente != null && cliente.getIdCliente() != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                filasAfectadas = conn.update("Cliente.actualizarCliente", cliente);
                conn.commit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return filasAfectadas;
    }
    
    public static boolean clienteTieneVentas(Integer idCliente) {
        boolean resultado = true;
        if (idCliente != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                resultado = conn.selectOne("Cliente.clienteTieneVentas", idCliente);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return resultado;
    }
    
    public static int eliminarCliente(Integer idCliente) {
        int filasAfectadas = 0;
        if (idCliente != null) {
            try (SqlSession conn = MyBatisUtils.getSession()) {
                filasAfectadas = conn.delete("Cliente.eliminarCliente", idCliente);
                conn.commit();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return filasAfectadas;
    }
}

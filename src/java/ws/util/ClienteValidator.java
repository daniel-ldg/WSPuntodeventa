package ws.util;

import beans.Cliente;
import model.dao.ClienteDAO;

public class ClienteValidator {
    
    public static final int NOMBRE_VACIO = 1;
    public static final int CORREO_VACIO = 2;
    public static final int DIRECCION_VACIA = 3;
    public static final int CONTRASEÑA_VACIA = 4;
    public static final int CONTRASEÑA_INVALIDA = 5;
    public static final int TELEFONO_VACIO = 6;
    public static final int TELEFONO_OCUPADO = 7;
    public static final int NOMBRE_USUARIO_OCUPADO = 8;
    public static final int CORREO_INVALIDO = 9;
    public static final int TELEFONO_INVALIDO = 10;
    public static final int NOMBRE_USUARIO_VACIO = 11;
    public static final int ID_CLIENTE_NO_ENCONTRADO = 12;
    public static final int CLIENTE_TIENE_VENTAS = 13;
    
    private static void checkValoresVacios(Cliente cliente) throws ValidatorException {
        if (cliente.getNombre().isEmpty()) {
            throw new ValidatorException(NOMBRE_VACIO, "El nombre del cliente no puede estar vacío");
        }
        if (cliente.getEmail().isEmpty()) {
            throw new ValidatorException(CORREO_VACIO, "El correo del cliente no puede estar vacío");
        }
        if (cliente.getDireccion().isEmpty()) {
            throw new ValidatorException(DIRECCION_VACIA, "La dirección del cliente no puede estar vacía");
        }
        if (cliente.getContraseña().isEmpty()) {
            throw new ValidatorException(CONTRASEÑA_VACIA, "La contraseña no puede estar vacía");
        }
        if (cliente.getTelefono().isEmpty()) {
            throw new ValidatorException(TELEFONO_VACIO, "El número de teléfono no puede estar vacío");
        }
        if (cliente.getUsuario().isEmpty()) {
            throw new ValidatorException(NOMBRE_USUARIO_VACIO, "El número de nombre de usuario no puede estar vacío");
        }
    }
    
    private static void checkValoresInvalidos(Cliente cliente) throws ValidatorException {
        if (!cliente.getContraseña().matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$")) {
            throw new ValidatorException(CONTRASEÑA_INVALIDA, "La contraseña debe tener una longitud entre 8 y 16 y "
                    + "debe contener al menos 1 mayúscula y 1 digito");
        }
        if (!cliente.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new ValidatorException(CORREO_INVALIDO, "El valor de correo no es un correo válido");
        }
        if (!cliente.getTelefono().matches("^\\d{10}$")) {
            throw new ValidatorException(TELEFONO_INVALIDO, "El valor del teléfono debe contener 10 dígitos");
        }
    }
    
    public static void validarRegistro(Cliente cliente) throws ValidatorException {
        checkValoresVacios(cliente);
        
        checkValoresInvalidos(cliente);
        
        if (!ClienteDAO.sePuedeRegistrarTelefono(cliente)) {
            throw new ValidatorException(TELEFONO_OCUPADO, "No se puede registrar el cliente porque ya "
                    + "existe un cliente con el mismo teléfono");
        }
        
        if (!ClienteDAO.sePuedeRegistrarUsuario(cliente)) {
            throw new ValidatorException(NOMBRE_USUARIO_OCUPADO, "No se puede registrar el cliente porque ya "
                    + "existe el nombre de usuario en la base de datos");
        }
    }
    
    public static void validarActualizacion(Cliente cliente) throws ValidatorException {
        checkValoresVacios(cliente);
        
        checkValoresInvalidos(cliente);
        
        if (!ClienteDAO.existeIdCliente(cliente.getIdCliente())) {
            throw new ValidatorException(ID_CLIENTE_NO_ENCONTRADO, "Cliente no encontrado en la base de datos");
        }
        if (!ClienteDAO.sePuedeActualizarTelefono(cliente)) {
            throw new ValidatorException(TELEFONO_OCUPADO, "El número de teléfono esta asociado con otro usuario");
        }
        
        if (!ClienteDAO.sePuedeActualizarUsuario(cliente)) {
            throw new ValidatorException(NOMBRE_USUARIO_OCUPADO, "El nombre de usuario esta ocupado por otro usuario");
        }
    }
    
    public static void validarElimminacion(Integer idCliente) throws ValidatorException {
        if (!ClienteDAO.existeIdCliente(idCliente)) {
            throw new ValidatorException(ID_CLIENTE_NO_ENCONTRADO, "Cliente no encontrado en la base de datos");
        }
        if (ClienteDAO.clienteTieneVentas(idCliente)) {
            throw new ValidatorException(CLIENTE_TIENE_VENTAS, "Cliente tiene ventas registradas, no se puede eliminar");
        }
    }
    
}

package kemel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class inicio extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    inicio frame = new inicio();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public inicio() {
        setTitle("Kemel.Online");
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\jlloz\\eclipse-workspace\\kemel\\icons\\Capa 1.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1223, 749);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(95, 28, 35));
        panel.setBounds(0, 0, 223, 710);
        contentPane.add(panel);
        panel.setLayout(null);

        JButton btnClientes = new JButton("CLIENTES");
        btnClientes.setBounds(67, 309, 89, 23);
        panel.add(btnClientes);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\jlloz\\eclipse-workspace\\kemel\\icons\\Capa 1.png"));
        lblNewLabel.setBounds(14, 33, 195, 195);
        panel.add(lblNewLabel);

        JButton btnPedidos = new JButton("PEDIDOS");
        btnPedidos.setBounds(67, 359, 89, 23);
        panel.add(btnPedidos);

        JButton btnFacturas = new JButton("FACTURAS");
        btnFacturas.setBounds(67, 416, 89, 23);
        panel.add(btnFacturas);

        JButton btnHistorial = new JButton("HISTORIAL");
        btnHistorial.setBounds(67, 479, 89, 23);
        panel.add(btnHistorial);

        JLabel lblUsuario = new JLabel(login.NombreUsuario);
        lblUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsuario.setIcon(new ImageIcon("C:\\Users\\jlloz\\eclipse-workspace\\kemel\\icons\\avatar-de-usuario.png"));
        lblUsuario.setBounds(1061, 11, 136, 23);
        contentPane.add(lblUsuario);

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBounds(284, 59, 877, 640);
        contentPane.add(textPane);

        // Evento para cargar clientes
        btnClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cargarClientes(textPane);
            }
        });
    }

    private void cargarClientes(JTextPane textPane) {
        ConectorSQL conector = new ConectorSQL();
        conector.conectar();
        Connection conexion = conector.getConexion();

        try {
            // Consulta para obtener los datos de la tabla cliente
            String query = "SELECT cliente_id, usuario, nombre, telefono, correo, direccion FROM cliente";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Construir el texto con los datos de los clientes
            StringBuilder clientes = new StringBuilder();
            while (rs.next()) {
                int id = rs.getInt("cliente_id");
                String usuario = rs.getString("usuario");
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String correo = rs.getString("correo");
                String direccion = rs.getString("direccion");

                clientes.append("ID: ").append(id)
                        .append(", Usuario: ").append(usuario)
                        .append(", Nombre: ").append(nombre)
                        .append(", Teléfono: ").append(telefono)
                        .append(", Correo: ").append(correo)
                        .append(", Dirección: ").append(direccion)
                        .append("\n");
            }

            // Mostrar los datos en el JTextPane
            textPane.setText(clientes.toString());
        } catch (Exception ex) {
            // Mostrar un mensaje de error en caso de problemas
            textPane.setText("Error al cargar clientes: " + ex.getMessage());
        } finally {
            // Cerrar la conexión a la base de datos
            conector.desconectar();
        }
    }

}


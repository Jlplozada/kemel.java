package kemel;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField userName;
    private JPasswordField userPassword;
    
    // Variable estática para almacenar el nombre de usuario
    public static String NombreUsuario = "";

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    login frame = new login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public login() {
        setResizable(false);
        setType(Type.UTILITY);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(95, 28, 35));
        panel.setBounds(214, 0, 220, 261);
        contentPane.add(panel);
        panel.setLayout(null);

        userPassword = new JPasswordField();
        userPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
        userPassword.setEchoChar('*');
        userPassword.setHorizontalAlignment(SwingConstants.CENTER);
        userPassword.setBounds(25, 135, 166, 20);
        panel.add(userPassword);

        userName = new JTextField();
        userName.setFont(new Font("Tahoma", Font.BOLD, 11));
        userName.setBounds(25, 65, 166, 20);
        panel.add(userName);
        userName.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblNewLabel = new JLabel("USUARIO");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(51, 40, 106, 14);
        panel.add(lblNewLabel);

        JLabel lblContrasea = new JLabel("CONTRASEÑA");
        lblContrasea.setHorizontalAlignment(SwingConstants.CENTER);
        lblContrasea.setForeground(Color.WHITE);
        lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblContrasea.setBounds(51, 111, 106, 14);
        panel.add(lblContrasea);

        JButton loginButton = new JButton("INICIAR");
        loginButton.setBounds(51, 184, 106, 23);
        panel.add(loginButton);
        
        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\jlloz\\eclipse-workspace\\kemel\\icons\\Capa 1.png"));
        lblNewLabel_1.setBounds(0, 0, 214, 261);
        contentPane.add(lblNewLabel_1);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validarUsuario();
            }
        });
    }

    private void validarUsuario() {
        String usuario = userName.getText();
        String clave = new String(userPassword.getPassword());

        ConectorSQL conector = new ConectorSQL();
        conector.conectar();
        try (Connection conexion = conector.getConexion()) {
            if (conexion != null) {
                String query = "SELECT * FROM dbo.usuarios WHERE usuario = ? AND clave = ?";
                PreparedStatement ps = conexion.prepareStatement(query);
                ps.setString(1, usuario);
                ps.setString(2, clave);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    NombreUsuario = usuario; 
                    JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    inicio inicioFrame = new inicio(); 
                    inicioFrame.setVisible(true);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error en la conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            conector.desconectar();
        }
    }
}

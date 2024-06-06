package com.mycompany.fct_project2;

import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ConfigDialog2 extends JDialog {

    private boolean loginSuccessful = false;
    private String url;
    private String user;
    private String password;

    public ConfigDialog2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jTextArea1.setText("jdbc:postgresql://192.168.1.10:5432/DB_FCT"); // URL por defecto
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 2, 18)); 
        jLabel1.setText("User:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 18)); 
        jLabel2.setText("Password:");

        jTextArea3.setPreferredSize(new java.awt.Dimension(200, 30)); // Ajusta el tamaño del JTextArea
        jScrollPane3.setViewportView(jTextArea3);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 18)); 
        jLabel4.setText("Url:");

        jTextArea2.setPreferredSize(new java.awt.Dimension(200, 30)); // Ajusta el tamaño del JTextArea
        jScrollPane2.setViewportView(jTextArea2);

        jTextArea1.setPreferredSize(new java.awt.Dimension(200, 30)); // Ajusta el tamaño del JTextArea
        jScrollPane1.setViewportView(jTextArea1);

        jButtonLogin = new JButton(); // Inicializa el botón jButtonLogin
        jButtonLogin.setText("Login"); // Configura el texto del botón
        jButtonLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(47, 47, 47)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonLogin))))
                    .addContainerGap(92, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButtonLogin)
                    .addGap(33, 33, 33))
        );

        pack();
    }

    private void jButtonLoginActionPerformed(ActionEvent evt) {
        url = jTextArea1.getText().trim();
        user = jTextArea2.getText().trim();
        password = jTextArea3.getText().trim();
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, user, password); // Inicializar la variable dentro del bloque try
            if (validateUser(connection, user, password)) {
                loginSuccessful = true;
                dispose(); // Cierra el diálogo de configuración
            } else {
                JOptionPane.showMessageDialog(ConfigDialog2.this, "Usuario o contraseña incorrectos", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(ConfigDialog2.this, "Error al conectar a la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE));
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean validateUser(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }



    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    private JButton jButtonLogin;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JTextArea jTextArea1;
    private JTextArea jTextArea2;
    private JTextArea jTextArea3;
}
package com.mycompany.fct_project2;

import javax.swing.*;

public class Main2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConfigDialog2 configDialog = new ConfigDialog2(null, true);
            configDialog.setVisible(true);
            if (configDialog.isLoginSuccessful()) {
                String url = configDialog.getUrl();
                String user = configDialog.getUser();
                String password = configDialog.getPassword();
                try {
                    EmpresaManager2 view = new EmpresaManager2(url, user, password);
                    view.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Login fallido. Saliendo de la aplicación.");
            }
        });
    }
}

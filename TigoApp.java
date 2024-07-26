package herencia_laboratorio2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TigoApp {

    private static Tigo tigo = new Tigo();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tigo Plan Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        frame.add(panel, BorderLayout.SOUTH);

        JButton agregarPlanButton = new JButton("Agregar Plan");
        JButton pagoPlanButton = new JButton("Pago Plan");
        JButton agregarAmigoButton = new JButton("Agregar Amigo");
        JButton listarButton = new JButton("Listar");
        JButton salirButton = new JButton("Salir");

        panel.add(agregarPlanButton);
        panel.add(pagoPlanButton);
        panel.add(agregarAmigoButton);
        panel.add(listarButton);
        panel.add(salirButton);

        agregarPlanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numeroTel = getInput("Ingrese el numero de telefono:", true, false);
                if (numeroTel == null) {
                    return;
                }

                String nombre = getInput("Ingrese el nombre del cliente:", false, false);
                if (nombre == null) {
                    return;
                }

                String extra = getInput("Ingrese el email (para IPHONE) o pin (para SAMSUNG):", false, false);
                if (extra == null) {
                    return;
                }

                String tipo = getInput("Ingrese el tipo de plan (IPHONE/SAMSUNG):", false, true);
                if (tipo == null) {
                    return;
                }

                tigo.agregarPlan(numeroTel, nombre, extra, tipo);
                outputArea.append("Plan agregado\n");
            }
        });

        pagoPlanButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numeroTel = getInput("Ingrese el numero de telefono:", true, false);
                if (numeroTel == null) {
                    return;
                }

                String minsInput = getInput("Ingrese los minutos consumidos:", true, false);
                if (minsInput == null) {
                    return;
                }

                String msgsInput = getInput("Ingrese los mensajes enviados:", true, false);
                if (msgsInput == null) {
                    return;
                }

                int mins = Integer.parseInt(minsInput);
                int msgs = Integer.parseInt(msgsInput);

                double pago = tigo.pagoPlan(Integer.parseInt(numeroTel), mins, msgs);
                outputArea.append("Pago mensual: " + pago + "\n");
            }
        });

        agregarAmigoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numeroTel = getInput("Ingrese el número de telefono:", true, false);
                if (numeroTel == null) {
                    return;
                }

                String pin = getInput("Ingrese el pin del amigo:", false, false);
                if (pin == null) {
                    return;
                }

                tigo.agregarAmigo(Integer.parseInt(numeroTel), pin);
                outputArea.append("Amigo agregado\n");
            }
        });

        listarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                int iphoneCount = 0;
                int samsungCount = 0;

                if (tigo.getPlanes().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No hay planes agregados.", "Listado de Planes", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (Plan plan : tigo.getPlanes()) {
                        sb.append(plan.toString()).append("\n");
                        if (plan instanceof PlanIPhone) {
                            iphoneCount++;
                        } else if (plan instanceof PlanSamsung) {
                            samsungCount++;
                        }
                    }
                    String message = "Listado de planes:\n" + sb.toString()
                            + "\nTotal IPHONE: " + iphoneCount
                            + "\nTotal SAMSUNG: " + samsungCount;
                    JOptionPane.showMessageDialog(frame, message, "Listado de Planes", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        salirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    private static String getInput(String message, boolean numericOnly, boolean validatePlanType) {
        while (true) {
            String input = JOptionPane.showInputDialog(message);
            if (input == null) {
                return null; // User pressed cancel
            }
            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar los datos correspondientes.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (numericOnly && !input.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Debe ingresar solo números.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (validatePlanType && !(input.equalsIgnoreCase("IPHONE") || input.equalsIgnoreCase("SAMSUNG"))) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un tipo de plan válido (IPHONE/SAMSUNG).", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                return input;
            }
        }
    }
}

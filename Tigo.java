/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herencia_laboratorio2;

import java.util.ArrayList;

/**
 *
 * @author Josue
 */
public class Tigo {

    private ArrayList<Plan> planes;

    public Tigo() {
        this.planes = new ArrayList<>();
    }

    public void agregarPlan(String numeroTel, String nombre, String extra, String tipo) {
        if (!busqueda(Integer.parseInt(numeroTel), extra, tipo)) {
            if (tipo.equalsIgnoreCase("IPHONE")) {
                PlanIPhone nuevoPlan = new PlanIPhone(numeroTel, nombre, extra);
                planes.add(nuevoPlan);
            } else if (tipo.equalsIgnoreCase("SAMSUNG")) {
                PlanSamsung nuevoPlan = new PlanSamsung(numeroTel, nombre, extra);
                planes.add(nuevoPlan);
            } else {
                System.out.println("Tipo de plan no valido. Debe ser 'IPHONE' o 'SAMSUNG'.");
            }
        } else {
            System.out.println("El numero de telefono o el dato adicional ya existe.");
        }
    }

    public boolean busqueda(int numeroTel, String datoExtra, String tipo) {
        for (Plan plan : planes) {
            if (plan.getNumeroTel().equals(String.valueOf(numeroTel))) {
                return true;
            }
            if (tipo.equalsIgnoreCase("IPHONE") && plan instanceof PlanIPhone) {
                if (((PlanIPhone) plan).getEmail().equals(datoExtra)) {
                    return true;
                }
            } else if (tipo.equalsIgnoreCase("SAMSUNG") && plan instanceof PlanSamsung) {
                if (((PlanSamsung) plan).getPin().equals(datoExtra)) {
                    return true;
                }
            }
        }
        return false;
    }

    public double pagoPlan(int numeroTel, int mins, int msgs) {
        for (Plan plan : planes) {
            if (plan.getNumeroTel().equals(String.valueOf(numeroTel))) {
                return plan.pagoMensual(mins, msgs);
            }
        }
        System.out.println("Numero de telefono no encontrado.");
        return 0.0;
    }

    public void agregarAmigo(int numeroTel, String pin) {
        boolean found = false;
        for (Plan plan : planes) {
            if (plan.getNumeroTel().equals(String.valueOf(numeroTel)) && plan instanceof PlanSamsung) {
                ((PlanSamsung) plan).agregarPinAmigo(pin);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Numero de telefono no encontrado o el plan no es Samsung.");
        }
    }

    public void lista() {
        StringBuilder sb = new StringBuilder();
        int countSamsung = 0;
        int countIPhone = 0;

        if (planes.isEmpty()) {
            System.out.println("No hay planes agregados.");
            return;
        }

        for (Plan plan : planes) {
            sb.append(plan.toString()).append("\n");
            if (plan instanceof PlanSamsung) {
                countSamsung++;
            } else if (plan instanceof PlanIPhone) {
                countIPhone++;
            }
        }

        sb.append("\nTotal de planes Samsung: ").append(countSamsung).append("\n");
        sb.append("Total de planes iPhone: ").append(countIPhone).append("\n");
        System.out.println(sb.toString());
    }

    public ArrayList<Plan> getPlanes() {
        return planes;
    }
}

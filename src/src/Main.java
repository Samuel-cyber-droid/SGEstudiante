import java.util.*;
import java.lang.management.*;

class Estudiante {
    private String idEstudiante;
    private String nombre;
    private String apellido;
    private int edad;
    private String carrera;

    public Estudiante(String idEstudiante, String nombre, String apellido, int edad, String carrera){
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.carrera = carrera;
    }

    // Creacion de getters y setters

    public String getIdEstudiante() {
        return idEstudiante;
    }
    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public String getCarrera() {
        return carrera;
    }
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return "ID: " + idEstudiante + ", Nombre: " + nombre + " " + apellido + ", Edad: " + edad + ", Carrera: " + carrera;
    }
}

class SistemaGestionEstudiantes {
    private Map<String, Estudiante> estudiantes;
    private Map<String, Integer> contadorOperaciones;
    private Map<String, Long> consumoMemoria;

    public SistemaGestionEstudiantes(){
        estudiantes = new HashMap<>();
        contadorOperaciones = new HashMap<>();
        consumoMemoria = new HashMap<>();

        //inicializacion de contadores
        contadorOperaciones.put("alta", 0);
        contadorOperaciones.put("baja", 0);
        contadorOperaciones.put("modificaci[on", 0);
        contadorOperaciones.put("busqueda", 0);
        contadorOperaciones.put("listado", 0);
    }

    private long getUsedMemory(){
        Runtime runtime = Runtime.getRuntime();
        return (runtime.totalMemory() - runtime.freeMemory()) / 1024;
    }

    public boolean altaEstudiante(String idEstudiante, String nombre, String apellido, int edad, String carrera){
        long memoriaInicio = getUsedMemory();

        if (estudiantes.containsKey(idEstudiante)){
            System.out.println("Error, Ya existe un estudiante con ese ID");
            return false;
        }
    }
}


//puchuumin was here 23

public class Main {
    public static void main(String[] args) {
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
    }
}
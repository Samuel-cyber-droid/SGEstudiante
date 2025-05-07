import java.util.*;
import java.lang.management.*;

class Estudiante {
    private String idEstudiante;
    private String nombre;
    private String apellido;
    private int edad;
    private String carrera;

    public Estudiante(String idEstudiante, String nombre, String apellido, int edad, String carrera) {
        this.idEstudiante = idEstudiante;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.carrera = carrera;
    }

    // Getters y Setters
    public String getIdEstudiante() { return idEstudiante; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getEdad() { return edad; }
    public String getCarrera() { return carrera; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setEdad(int edad) { this.edad = edad; }
    public void setCarrera(String carrera) { this.carrera = carrera; }

    @Override
    public String toString() {
        return "ID: " + idEstudiante + ", Nombre: " + nombre + " " + apellido +
                ", Edad: " + edad + ", Carrera: " + carrera;
    }
}

class SistemaGestionEstudiantes {
    private Map<String, Estudiante> estudiantes;
    private Map<String, Integer> contadorOperaciones;
    private Map<String, Long> consumoMemoria;

    public SistemaGestionEstudiantes() {
        estudiantes = new HashMap<>();
        contadorOperaciones = new HashMap<>();
        consumoMemoria = new HashMap<>();

        // Inicializar contadores
        String[] operaciones = {"alta", "baja", "modificacion", "busqueda", "listado"};
        for (String op : operaciones) {
            contadorOperaciones.put(op, 0);
            consumoMemoria.put(op, 0L);
        }
    }

    private long getUsedMemory() {
        // Forzar GC para medición más precisa
        System.gc();
        //System.runFinalization();
        return ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / 1024; // KB
    }

    private void registrarOperacion(String operacion, long memoriaUsada) {
        contadorOperaciones.put(operacion, contadorOperaciones.get(operacion) + 1);
        consumoMemoria.put(operacion, consumoMemoria.get(operacion) + memoriaUsada);
    }

    public boolean altaEstudiante(String idEstudiante, String nombre, String apellido, int edad, String carrera) {
        long memoriaInicio = getUsedMemory();

        if (estudiantes.containsKey(idEstudiante)) {
            System.out.println("Error: Ya existe un estudiante con ese ID.");
            return false;
        }

        Estudiante nuevoEstudiante = new Estudiante(idEstudiante, nombre, apellido, edad, carrera);
        estudiantes.put(idEstudiante, nuevoEstudiante);

        // Operación adicional para hacer visible el uso de memoria
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            tempList.add("temp_" + i);
        }

        long memoriaFin = getUsedMemory();
        registrarOperacion("alta", memoriaFin - memoriaInicio);
        System.out.println("Estudiante agregado con éxito.");
        return true;
    }

    public boolean bajaEstudiante(String idEstudiante) {
        long memoriaInicio = getUsedMemory();

        if (!estudiantes.containsKey(idEstudiante)) {
            System.out.println("Error: No existe un estudiante con ese ID.");
            return false;
        }

        estudiantes.remove(idEstudiante);

        // Operación adicional para hacer visible el uso de memoria
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            tempList.add("temp_" + i);
        }

        long memoriaFin = getUsedMemory();
        registrarOperacion("baja", memoriaFin - memoriaInicio);
        System.out.println("Estudiante eliminado con éxito.");
        return true;
    }

    public boolean modificacionEstudiante(String idEstudiante, String nombre, String apellido, Integer edad, String carrera) {
        long memoriaInicio = getUsedMemory();

        if (!estudiantes.containsKey(idEstudiante)) {
            System.out.println("Error: No existe un estudiante con ese ID.");
            return false;
        }

        Estudiante estudiante = estudiantes.get(idEstudiante);

        if (nombre != null) estudiante.setNombre(nombre);
        if (apellido != null) estudiante.setApellido(apellido);
        if (edad != null) estudiante.setEdad(edad);
        if (carrera != null) estudiante.setCarrera(carrera);

        // Operación adicional para hacer visible el uso de memoria
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            tempList.add("temp_" + i);
        }

        long memoriaFin = getUsedMemory();
        registrarOperacion("modificacion", memoriaFin - memoriaInicio);
        System.out.println("Estudiante modificado con éxito.");
        return true;
    }

    public List<Estudiante> busquedaEstudiante(String idEstudiante, String nombre, String apellido) {
        long memoriaInicio = getUsedMemory();
        List<Estudiante> resultados = new ArrayList<>();

        if (idEstudiante != null && !idEstudiante.isEmpty()) {
            if (estudiantes.containsKey(idEstudiante)) {
                resultados.add(estudiantes.get(idEstudiante));
            }
        } else {
            for (Estudiante estudiante : estudiantes.values()) {
                boolean coincide = true;

                if (nombre != null && !nombre.isEmpty() &&
                        !estudiante.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    coincide = false;
                }

                if (apellido != null && !apellido.isEmpty() &&
                        !estudiante.getApellido().toLowerCase().contains(apellido.toLowerCase())) {
                    coincide = false;
                }

                if (coincide) {
                    resultados.add(estudiante);
                }
            }
        }

        // Operación adicional para hacer visible el uso de memoria
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < resultados.size(); i++) {
            tempList.add("temp_" + i);
        }

        long memoriaFin = getUsedMemory();
        registrarOperacion("busqueda", memoriaFin - memoriaInicio);
        return resultados;
    }

    public void listadoEstudiantes() {
        long memoriaInicio = getUsedMemory();

        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        System.out.println("\nListado de estudiantes:");
        for (Estudiante estudiante : estudiantes.values()) {
            System.out.println(estudiante);
        }

        // Operación adicional para hacer visible el uso de memoria
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < estudiantes.size(); i++) {
            tempList.add("temp_" + i);
        }

        long memoriaFin = getUsedMemory();
        registrarOperacion("listado", memoriaFin - memoriaInicio);
    }

    public void mostrarEstadisticas() {
        System.out.println("\nEstadísticas del sistema:");
        System.out.println("Conteo de operaciones realizadas:");
        contadorOperaciones.forEach((op, count) ->
                System.out.println(op.toUpperCase() + ": " + count));

        System.out.println("\nUso de memoria acumulado por operación (KB):");
        consumoMemoria.forEach((op, mem) ->
                System.out.println(op.toUpperCase() + ": " + mem + " KB"));
    }
}

public class Main {
    public static void main(String[] args) {
        SistemaGestionEstudiantes sistema = new SistemaGestionEstudiantes();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Sistema de Gestión de Estudiantes ---");
            System.out.println("1. Alta de estudiante");
            System.out.println("2. Baja de estudiante");
            System.out.println("3. Modificación de estudiante");
            System.out.println("4. Búsqueda de estudiante");
            System.out.println("5. Listado de estudiantes");
            System.out.println("6. Mostrar estadísticas");
            System.out.println("7. Salir");

            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("\nAlta de estudiante");
                    System.out.print("ID del estudiante: ");
                    String idEst = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Apellido: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Edad: ");
                    int edad = Integer.parseInt(scanner.nextLine());
                    System.out.print("Carrera: ");
                    String carrera = scanner.nextLine();
                    sistema.altaEstudiante(idEst, nombre, apellido, edad, carrera);
                    break;

                case "2":
                    System.out.println("\nBaja de estudiante");
                    System.out.print("ID del estudiante a eliminar: ");
                    String idBaja = scanner.nextLine();
                    sistema.bajaEstudiante(idBaja);
                    break;

                case "3":
                    System.out.println("\nModificación de estudiante");
                    System.out.print("ID del estudiante a modificar: ");
                    String idMod = scanner.nextLine();
                    System.out.println("Deje en blanco los campos que no desea modificar");
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Nuevo apellido: ");
                    String nuevoApellido = scanner.nextLine();
                    System.out.print("Nueva edad: ");
                    String edadStr = scanner.nextLine();
                    Integer nuevaEdad = edadStr.isEmpty() ? null : Integer.parseInt(edadStr);
                    System.out.print("Nueva carrera: ");
                    String nuevaCarrera = scanner.nextLine();

                    sistema.modificacionEstudiante(idMod,
                            nuevoNombre.isEmpty() ? null : nuevoNombre,
                            nuevoApellido.isEmpty() ? null : nuevoApellido,
                            nuevaEdad,
                            nuevaCarrera.isEmpty() ? null : nuevaCarrera);
                    break;

                case "4":
                    System.out.println("\nBúsqueda de estudiante");
                    System.out.println("Puede buscar por ID, nombre o apellido (deje en blanco lo que no desea usar)");
                    System.out.print("ID: ");
                    String idBusq = scanner.nextLine();
                    System.out.print("Nombre: ");
                    String nombreBusq = scanner.nextLine();
                    System.out.print("Apellido: ");
                    String apellidoBusq = scanner.nextLine();

                    List<Estudiante> resultados = sistema.busquedaEstudiante(
                            idBusq.isEmpty() ? null : idBusq,
                            nombreBusq.isEmpty() ? null : nombreBusq,
                            apellidoBusq.isEmpty() ? null : apellidoBusq
                    );

                    if (resultados.isEmpty()) {
                        System.out.println("No se encontraron estudiantes.");
                    } else {
                        System.out.println("\nResultados de la búsqueda:");
                        for (Estudiante e : resultados) {
                            System.out.println(e);
                        }
                    }
                    break;

                case "5":
                    sistema.listadoEstudiantes();
                    break;

                case "6":
                    sistema.mostrarEstadisticas();
                    break;

                case "7":
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
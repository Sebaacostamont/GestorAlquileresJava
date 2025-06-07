package com.Sebaacostam3.gestoralquileres.app;

import com.Sebaacostam3.gestoralquileres.servicio.GestorAlquileres;
import com.Sebaacostam3.gestoralquileres.modelo.Propiedad;
import com.Sebaacostam3.gestoralquileres.modelo.Inquilino;
import com.Sebaacostam3.gestoralquileres.modelo.ContratoAlquiler;
import com.Sebaacostam3.gestoralquileres.modelo.Pago;
import com.Sebaacostam3.gestoralquileres.modelo.Incidencia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException; // Importación útil para el manejo de entrada, aunque no usada directamente en este código, al escalar será más usado.
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clase principal que proporciona una interfaz de consola para interactuar
 * con el sistema de gestión de alquileres. Permite realizar operaciones CRUD
 * sobre las diferentes entidades (Propiedades, Inquilinos, Contratos, Pagos, Incidencias).
 */
public class Main {

    // Instancia del gestor de lógica de negocio
    private static GestorAlquileres gestor = new GestorAlquileres();
    // Objeto Scanner para leer la entrada del usuario
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Punto de entrada principal de la aplicación de consola.
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        mostrarMenuPrincipal();
    }

    /**
     * Muestra el menú principal de la aplicación y maneja la navegación
     * a los diferentes módulos de gestión.
     */
    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n--- Gestor de Alquileres - Menú Principal ---");
            System.out.println("1. Gestión de Propiedades");
            System.out.println("2. Gestión de Inquilinos");
            System.out.println("3. Gestión de Contratos de Alquiler");
            System.out.println("4. Gestión de Pagos");
            System.out.println("5. Gestión de Incidencias");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero(); // Lee la opción ingresada por el usuario

            switch (opcion) {
                case 1:
                    menuGestionPropiedades();
                    break;
                case 2:
                    menuGestionInquilinos();
                    break;
                case 3:
                    menuGestionContratos();
                    break;
                case 4:
                    menuGestionPagos();
                    break;
                case 5:
                    menuGestionIncidencias();
                    break;
                case 0:
                    System.out.println("Saliendo del Gestor de Alquileres. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 0);

        scanner.close(); // Cierra el scanner al finalizar la aplicación para liberar recursos
    }

    // --- Métodos de utilidad para la lectura de entrada del usuario ---

    /**
     * Lee una entrada de tipo entero del usuario, manejando entradas inválidas.
     * @return El número entero ingresado por el usuario.
     */
    private static int leerEntero() {
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Por favor, ingrese un número entero.");
            scanner.next(); // Descarta la entrada incorrecta para evitar un bucle infinito
            System.out.print("Re-ingrese su opción: ");
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Consume el salto de línea pendiente después de nextInt()
        return valor;
    }

    /**
     * Lee una entrada de tipo double (decimal) del usuario, manejando entradas inválidas.
     * @return El número decimal ingresado por el usuario.
     */
    private static double leerDoble() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Entrada inválida. Por favor, ingrese un número decimal.");
            scanner.next(); // Descarta la entrada incorrecta
            System.out.print("Re-ingrese el valor: ");
        }
        double valor = scanner.nextDouble();
        scanner.nextLine(); // Consume el salto de línea pendiente
        return valor;
    }

    /**
     * Lee una línea completa de texto del usuario.
     * @return La cadena de texto ingresada por el usuario.
     */
    private static String leerLinea() {
        return scanner.nextLine();
    }

    /**
     * Lee una fecha del usuario en formato DD-MM-YYYY, manejando errores de formato.
     * @return La fecha ingresada como un objeto LocalDate.
     */
    private static LocalDate leerFecha() {
        LocalDate fecha = null;
        boolean fechaValida = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define el formato de fecha esperado

        while (!fechaValida) {
            System.out.print("Ingrese la fecha (DD-MM-YYYY): ");
            String fechaStr = leerLinea();
            try {
                fecha = LocalDate.parse(fechaStr, formatter); // Intenta parsear la fecha con el formato definido
                fechaValida = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Use DD-MM-YYYY."); // Mensaje de error para formato incorrecto
            }
        }
        return fecha;
    }

    // --- Menús de Gestión por Entidad ---

    /**
     * Muestra el menú para la gestión de propiedades y maneja las operaciones correspondientes.
     */
    private static void menuGestionPropiedades() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Propiedades ---");
            System.out.println("1. Agregar Propiedad");
            System.out.println("2. Listar Propiedades");
            System.out.println("3. Buscar Propiedad por ID");
            System.out.println("4. Actualizar Propiedad");
            System.out.println("5. Eliminar Propiedad");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    agregarPropiedad();
                    break;
                case 2:
                    listarPropiedades();
                    break;
                case 3:
                    buscarPropiedadPorId();
                    break;
                case 4:
                    actualizarPropiedad();
                    break;
                case 5:
                    eliminarPropiedad();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // --- Métodos de CRUD para Propiedad ---

    /**
     * Solicita al usuario los datos para agregar una nueva propiedad y la registra a través del gestor.
     */
    private static void agregarPropiedad() {
        System.out.println("\n--- Agregar Nueva Propiedad ---");
        System.out.print("Dirección: ");
        String direccion = leerLinea();
        System.out.print("Tipo (Ej. Casa, Apartamento, Local): ");
        String tipo = leerLinea();
        System.out.print("Metros Cuadrados: ");
        double metrosCuadrados = leerDoble();
        System.out.print("Precio de Alquiler Mensual: ");
        double precioMensual = leerDoble();

        // Se crea un nuevo objeto Propiedad. El ID se deja nulo para que el gestor lo genere.
        // El estado 'alquilada' se inicializa a false, asumiendo que una propiedad nueva no está alquilada.
        Propiedad nuevaPropiedad = new Propiedad(null, direccion, tipo, metrosCuadrados, false, precioMensual);
        gestor.agregarPropiedad(nuevaPropiedad);
    }

    /**
     * Lista todas las propiedades registradas en el sistema.
     */
    private static void listarPropiedades() {
        System.out.println("\n--- Listado de Propiedades ---");
        List<Propiedad> propiedades = gestor.listarPropiedades();
        if (propiedades.isEmpty()) {
            System.out.println("No hay propiedades registradas.");
        } else {
            for (Propiedad p : propiedades) {
                System.out.println("ID: " + p.getId() +
                        ", Dirección: " + p.getDireccion() +
                        ", Tipo: " + p.getTipo() +
                        ", M2: " + p.getMetrosCuadrados() +
                        ", Precio: " + p.getPrecioMensual() +
                        ", Alquilada: " + (p.isAlquilada() ? "Sí" : "No"));
            }
        }
    }

    /**
     * Solicita un ID de propiedad y muestra la información de la propiedad encontrada.
     */
    private static void buscarPropiedadPorId() {
        System.out.println("\n--- Buscar Propiedad por ID ---");
        System.out.print("Ingrese el ID de la propiedad a buscar: ");
        String id = leerLinea();
        Optional<Propiedad> propiedadOpt = gestor.buscarPropiedadPorId(id);
        if (propiedadOpt.isPresent()) {
            Propiedad p = propiedadOpt.get();
            System.out.println("Propiedad encontrada:");
            System.out.println("ID: " + p.getId() +
                    ", Dirección: " + p.getDireccion() +
                    ", Tipo: " + p.getTipo() +
                    ", M2: " + p.getMetrosCuadrados() +
                    ", Precio: " + p.getPrecioMensual() +
                    ", Alquilada: " + (p.isAlquilada() ? "Sí" : "No"));
        } else {
            System.out.println("Propiedad con ID '" + id + "' no encontrada.");
        }
    }

    /**
     * Solicita el ID de una propiedad a actualizar y luego los nuevos datos.
     * Permite mantener datos existentes si el usuario deja un campo vacío.
     */
    private static void actualizarPropiedad() {
        System.out.println("\n--- Actualizar Propiedad ---");
        System.out.print("Ingrese el ID de la propiedad a actualizar: ");
        String id = leerLinea();
        Optional<Propiedad> propiedadOpt = gestor.buscarPropiedadPorId(id);

        if (propiedadOpt.isPresent()) {
            Propiedad propiedadExistente = propiedadOpt.get(); // Se obtiene la referencia al objeto existente
            System.out.println("Propiedad actual: " + propiedadExistente.getDireccion());

            // Se solicitan los nuevos datos. Si se dejan vacíos, se mantienen los valores actuales.
            System.out.print("Nueva Dirección (" + propiedadExistente.getDireccion() + "): ");
            String nuevaDireccion = leerLinea();
            if (!nuevaDireccion.isEmpty()) {
                propiedadExistente.setDireccion(nuevaDireccion);
            }

            System.out.print("Nuevo Tipo (" + propiedadExistente.getTipo() + "): ");
            String nuevoTipo = leerLinea();
            if (!nuevoTipo.isEmpty()) {
                propiedadExistente.setTipo(nuevoTipo);
            }

            System.out.print("Nuevos Metros Cuadrados (" + propiedadExistente.getMetrosCuadrados() + "): ");
            String m2Str = leerLinea();
            if (!m2Str.isEmpty()) {
                try {
                    propiedadExistente.setMetrosCuadrados(Double.parseDouble(m2Str));
                } catch (NumberFormatException e) {
                    System.out.println("Valor de metros cuadrados inválido, se mantiene el anterior.");
                }
            }

            System.out.print("Nuevo Precio Mensual (" + propiedadExistente.getPrecioMensual() + "): ");
            String precioStr = leerLinea();
            if (!precioStr.isEmpty()) {
                try {
                    propiedadExistente.setPrecioMensual(Double.parseDouble(precioStr));
                } catch (NumberFormatException e) {
                    System.out.println("Valor de precio mensual inválido, se mantiene el anterior.");
                }
            }

            // Se crea un nuevo objeto Propiedad con los datos actualizados (o existentes si no se modificaron).
            // Es importante pasar el ID y el estado 'alquilada' actual, ya que el gestor espera un objeto completo.
            Propiedad propiedadConDatosActualizados = new Propiedad(
                    propiedadExistente.getId(),
                    propiedadExistente.getDireccion(),
                    propiedadExistente.getTipo(),
                    propiedadExistente.getMetrosCuadrados(),
                    propiedadExistente.isAlquilada(), // Se mantiene el valor actual de 'alquilada'
                    propiedadExistente.getPrecioMensual()
            );

            gestor.actualizarPropiedad(propiedadConDatosActualizados);

        } else {
            System.out.println("Propiedad con ID '" + id + "' no encontrada para actualizar.");
        }
    }

    /**
     * Solicita el ID de una propiedad a eliminar y la elimina a través del gestor.
     */
    private static void eliminarPropiedad() {
        System.out.println("\n--- Eliminar Propiedad ---");
        System.out.print("Ingrese el ID de la propiedad a eliminar: ");
        String id = leerLinea();
        gestor.eliminarPropiedad(id);
    }

    // --- Menús de Gestión para Inquilinos ---

    /**
     * Muestra el menú para la gestión de inquilinos y maneja las operaciones correspondientes.
     */
    private static void menuGestionInquilinos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Inquilinos ---");
            System.out.println("1. Agregar Inquilino");
            System.out.println("2. Listar Inquilinos");
            System.out.println("3. Buscar Inquilino por ID");
            System.out.println("4. Actualizar Inquilino");
            System.out.println("5. Eliminar Inquilino");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    agregarInquilino();
                    break;
                case 2:
                    listarInquilinos();
                    break;
                case 3:
                    buscarInquilinoPorId();
                    break;
                case 4:
                    actualizarInquilino();
                    break;
                case 5:
                    eliminarInquilino();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // --- Métodos de CRUD para Inquilino ---

    /**
     * Solicita al usuario los datos para agregar un nuevo inquilino y lo registra a través del gestor.
     */
    private static void agregarInquilino() {
        System.out.println("\n--- Agregar Nuevo Inquilino ---");
        System.out.print("Nombre: ");
        String nombre = leerLinea();
        System.out.print("Apellido: ");
        String apellido = leerLinea();
        System.out.print("Teléfono: ");
        String telefono = leerLinea();
        System.out.print("DNI: ");
        String dni = leerLinea();
        System.out.print("Email: ");
        String email = leerLinea();

        // Se crea un nuevo objeto Inquilino. El ID se deja nulo para que el gestor lo genere.
        Inquilino nuevoInquilino = new Inquilino(null, nombre, apellido, telefono, dni, email);
        gestor.agregarInquilino(nuevoInquilino);
    }

    /**
     * Lista todos los inquilinos registrados en el sistema.
     */
    private static void listarInquilinos() {
        System.out.println("\n--- Listado de Inquilinos ---");
        List<Inquilino> inquilinos = gestor.listarInquilinos();
        if (inquilinos.isEmpty()) {
            System.out.println("No hay inquilinos registrados.");
        } else {
            for (Inquilino i : inquilinos) {
                System.out.println("ID: " + i.getId() +
                        ", Nombre: " + i.getNombre() +
                        ", Apellido: " + i.getApellido() +
                        ", DNI: " + i.getDni() +
                        ", Teléfono: " + i.getTelefono() +
                        ", Email: " + i.getEmail());
            }
        }
    }

    /**
     * Solicita un ID de inquilino y muestra la información del inquilino encontrado.
     */
    private static void buscarInquilinoPorId() {
        System.out.println("\n--- Buscar Inquilino por ID ---");
        System.out.print("Ingrese el ID del inquilino a buscar: ");
        String id = leerLinea();
        Optional<Inquilino> inquilinoOpt = gestor.buscarInquilinoPorId(id);
        if (inquilinoOpt.isPresent()) {
            Inquilino i = inquilinoOpt.get();
            System.out.println("Inquilino encontrado:");
            System.out.println("ID: " + i.getId() +
                    ", Nombre: " + i.getNombre() +
                    ", Apellido: " + i.getApellido() +
                    ", DNI: " + i.getDni() +
                    ", Teléfono: " + i.getTelefono() +
                    ", Email: " + i.getEmail());
        } else {
            System.out.println("Inquilino con ID '" + id + "' no encontrado.");
        }
    }

    /**
     * Solicita el ID de un inquilino a actualizar y luego los nuevos datos.
     * Permite mantener datos existentes si el usuario deja un campo vacío.
     */
    private static void actualizarInquilino() {
        System.out.println("\n--- Actualizar Inquilino ---");
        System.out.print("Ingrese el ID del inquilino a actualizar: ");
        String id = leerLinea();
        Optional<Inquilino> inquilinoOpt = gestor.buscarInquilinoPorId(id);

        if (inquilinoOpt.isPresent()) {
            Inquilino inquilinoExistente = inquilinoOpt.get();
            System.out.println("Inquilino actual: " + inquilinoExistente.getNombre() + " " + inquilinoExistente.getApellido());

            System.out.print("Nuevo Nombre (" + inquilinoExistente.getNombre() + "): ");
            String nuevoNombre = leerLinea();
            if (!nuevoNombre.isEmpty()) {
                inquilinoExistente.setNombre(nuevoNombre);
            }

            System.out.print("Nuevo Apellido (" + inquilinoExistente.getApellido() + "): ");
            String nuevoApellido = leerLinea();
            if (!nuevoApellido.isEmpty()) {
                inquilinoExistente.setApellido(nuevoApellido);
            }

            // El DNI no tiene setter en el modelo Inquilino, por lo que no se pide actualización aquí.

            System.out.print("Nuevo Teléfono (" + inquilinoExistente.getTelefono() + "): ");
            String nuevoTelefono = leerLinea();
            if (!nuevoTelefono.isEmpty()) {
                inquilinoExistente.setTelefono(nuevoTelefono);
            }

            System.out.print("Nuevo Email (" + inquilinoExistente.getEmail() + "): ");
            String nuevoEmail = leerLinea();
            if (!nuevoEmail.isEmpty()) {
                inquilinoExistente.setEmail(nuevoEmail);
            }

            // Se crea un nuevo objeto Inquilino con los datos actualizados (o existentes).
            Inquilino inquilinoConDatosActualizados = new Inquilino(
                    inquilinoExistente.getId(),
                    inquilinoExistente.getNombre(),
                    inquilinoExistente.getApellido(),
                    inquilinoExistente.getTelefono(),
                    inquilinoExistente.getDni(), // El DNI se mantiene el existente
                    inquilinoExistente.getEmail()
            );

            gestor.actualizarInquilino(inquilinoConDatosActualizados);

        } else {
            System.out.println("Inquilino con ID '" + id + "' no encontrado para actualizar.");
        }
    }

    /**
     * Solicita el ID de un inquilino a eliminar y lo elimina a través del gestor.
     */
    private static void eliminarInquilino() {
        System.out.println("\n--- Eliminar Inquilino ---");
        System.out.print("Ingrese el ID del inquilino a eliminar: ");
        String id = leerLinea();
        gestor.eliminarInquilino(id);
    }


    /**
     * Muestra el menú para la gestión de contratos de alquiler y maneja las operaciones correspondientes.
     */
    private static void menuGestionContratos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Contratos de Alquiler ---");
            System.out.println("1. Crear Contrato");
            System.out.println("2. Listar Contratos (Todos)");
            System.out.println("3. Listar Contratos Activos");
            System.out.println("4. Buscar Contrato por ID");
            System.out.println("5. Actualizar Contrato");
            System.out.println("6. Finalizar Contrato");
            System.out.println("7. Eliminar Contrato"); // Si decides mantener la eliminación en el gestor
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    crearContrato();
                    break;
                case 2:
                    listarContratos();
                    break;
                case 3:
                    listarContratosActivos();
                    break;
                case 4:
                    buscarContratoPorId();
                    break;
                case 5:
                    actualizarContrato();
                    break;
                case 6:
                    finalizarContrato();
                    break;
                case 7: // Si decides mantener la eliminación en el gestor
                    eliminarContrato();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // --- Métodos de CRUD para ContratoAlquiler ---

    /**
     * Solicita al usuario los IDs de propiedad e inquilino, y los datos del contrato,
     * luego crea un nuevo contrato a través del gestor.
     * Requiere que la propiedad y el inquilino existan previamente.
     */
    private static void crearContrato() {
        System.out.println("\n--- Crear Nuevo Contrato de Alquiler ---");
        System.out.print("Ingrese ID de la Propiedad: ");
        String propiedadId = leerLinea();
        System.out.print("Ingrese ID del Inquilino: ");
        String inquilinoId = leerLinea();

        // Es crucial obtener las instancias reales de Propiedad e Inquilino para vincularlas al contrato.
        Optional<Propiedad> propOpt = gestor.buscarPropiedadPorId(propiedadId);
        Optional<Inquilino> inquilinoOpt = gestor.buscarInquilinoPorId(inquilinoId);

        if (!propOpt.isPresent()) {
            System.out.println("Error: Propiedad con ID '" + propiedadId + "' no encontrada.");
            return;
        }
        if (!inquilinoOpt.isPresent()) {
            System.out.println("Error: Inquilino con ID '" + inquilinoId + "' no encontrado.");
            return;
        }

        Propiedad propiedad = propOpt.get();
        Inquilino inquilino = inquilinoOpt.get();

        System.out.print("Fecha de Inicio del Contrato: ");
        LocalDate fechaInicio = leerFecha();
        System.out.print("Fecha de Fin del Contrato: ");
        LocalDate fechaFin = leerFecha();
        System.out.print("Valor Mensual del Alquiler: ");
        double valorMensual = leerDoble();

        // Se crea un nuevo objeto ContratoAlquiler. El ID se deja nulo para que el gestor lo genere.
        // Se pasan las instancias reales de Propiedad e Inquilino.
        ContratoAlquiler nuevoContrato = new ContratoAlquiler(
                null, // ID nulo, será generado por el gestor
                fechaInicio,
                fechaFin,
                propiedad, // Instancia real de la propiedad
                inquilino, // Instancia real del inquilino
                valorMensual,
                true // Un contrato recién creado se considera activo
        );

        gestor.crearContrato(nuevoContrato);
    }

    /**
     * Lista todos los contratos de alquiler registrados en el sistema.
     */
    private static void listarContratos() {
        System.out.println("\n--- Listado de Contratos de Alquiler (Todos) ---");
        List<ContratoAlquiler> contratos = gestor.listarContratos();
        if (contratos.isEmpty()) {
            System.out.println("No hay contratos registrados.");
        } else {
            for (ContratoAlquiler c : contratos) {
                System.out.println("ID: " + c.getId() +
                        ", Propiedad: " + c.getPropiedad().getDireccion() + " (ID: " + c.getPropiedad().getId() + ")" +
                        ", Inquilino: " + c.getInquilino().getNombre() + " " + c.getInquilino().getApellido() + " (ID: " + c.getInquilino().getId() + ")" +
                        ", Inicio: " + c.getFechaInicio() +
                        ", Fin: " + c.getFechaFin() +
                        ", Valor: " + c.getValorMensual() +
                        ", Activo: " + (c.isActivo() ? "Sí" : "No"));
            }
        }
    }

    /**
     * Lista solo los contratos de alquiler que están actualmente activos.
     */
    private static void listarContratosActivos() {
        System.out.println("\n--- Listado de Contratos de Alquiler (Activos) ---");
        List<ContratoAlquiler> contratosActivos = gestor.listarContratosActivos();
        if (contratosActivos.isEmpty()) {
            System.out.println("No hay contratos activos.");
        } else {
            for (ContratoAlquiler c : contratosActivos) {
                System.out.println("ID: " + c.getId() +
                        ", Propiedad: " + c.getPropiedad().getDireccion() + " (ID: " + c.getPropiedad().getId() + ")" +
                        ", Inquilino: " + c.getInquilino().getNombre() + " " + c.getInquilino().getApellido() + " (ID: " + c.getInquilino().getId() + ")" +
                        ", Inicio: " + c.getFechaInicio() +
                        ", Fin: " + c.getFechaFin() +
                        ", Valor: " + c.getValorMensual() +
                        ", Activo: " + (c.isActivo() ? "Sí" : "No"));
            }
        }
    }

    /**
     * Solicita un ID de contrato y muestra la información del contrato encontrado.
     */
    private static void buscarContratoPorId() {
        System.out.println("\n--- Buscar Contrato por ID ---");
        System.out.print("Ingrese el ID del contrato a buscar: ");
        String id = leerLinea();
        Optional<ContratoAlquiler> contratoOpt = gestor.buscarContratoPorId(id);
        if (contratoOpt.isPresent()) {
            ContratoAlquiler c = contratoOpt.get();
            System.out.println("Contrato encontrado:");
            System.out.println("ID: " + c.getId() +
                    ", Propiedad: " + c.getPropiedad().getDireccion() + " (ID: " + c.getPropiedad().getId() + ")" +
                    ", Inquilino: " + c.getInquilino().getNombre() + " " + c.getInquilino().getApellido() + " (ID: " + c.getInquilino().getId() + ")" +
                    ", Inicio: " + c.getFechaInicio() +
                    ", Fin: " + c.getFechaFin() +
                    ", Valor: " + c.getValorMensual() +
                    ", Activo: " + (c.isActivo() ? "Sí" : "No"));
        } else {
            System.out.println("Contrato con ID '" + id + "' no encontrado.");
        }
    }

    /**
     * Solicita el ID de un contrato a actualizar y luego los nuevos datos para fecha de fin y valor mensual.
     * La fecha de inicio, propiedad e inquilino no pueden ser modificados.
     */
    private static void actualizarContrato() {
        System.out.println("\n--- Actualizar Contrato ---");
        System.out.print("Ingrese el ID del contrato a actualizar: ");
        String id = leerLinea();
        Optional<ContratoAlquiler> contratoOpt = gestor.buscarContratoPorId(id);

        if (contratoOpt.isPresent()) {
            ContratoAlquiler contratoExistente = contratoOpt.get();
            System.out.println("Contrato actual: " + contratoExistente.getId() +
                    " (Propiedad: " + contratoExistente.getPropiedad().getDireccion() +
                    ", Inquilino: " + contratoExistente.getInquilino().getNombre() + ")");

            System.out.print("Nueva Fecha de Fin (" + contratoExistente.getFechaFin() + ") (DD-MM-YYYY): ");
            String fechaFinStr = leerLinea();
            LocalDate nuevaFechaFin = null;
            if (!fechaFinStr.isEmpty()) {
                try {
                    nuevaFechaFin = LocalDate.parse(fechaFinStr, DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Usar formatter para parsear
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha inválido, se mantiene la anterior.");
                    nuevaFechaFin = contratoExistente.getFechaFin(); // Mantener la anterior si hay error
                }
            } else {
                nuevaFechaFin = contratoExistente.getFechaFin(); // Si se deja vacío, mantener la anterior
            }

            System.out.print("Nuevo Valor Mensual (" + contratoExistente.getValorMensual() + "): ");
            String valorStr = leerLinea();
            double nuevoValorMensual = contratoExistente.getValorMensual();
            if (!valorStr.isEmpty()) {
                try {
                    nuevoValorMensual = Double.parseDouble(valorStr);
                } catch (NumberFormatException e) {
                    System.out.println("Valor mensual inválido, se mantiene el anterior.");
                }
            }

            // Se crea un nuevo objeto ContratoAlquiler con los datos actualizados (o existentes).
            // La fecha de inicio y las referencias a propiedad e inquilino no cambian para un contrato existente.
            ContratoAlquiler contratoConDatosActualizados = new ContratoAlquiler(
                    contratoExistente.getId(),
                    contratoExistente.getFechaInicio(), // La fecha de inicio no debería cambiar
                    nuevaFechaFin,
                    contratoExistente.getPropiedad(), // Las referencias a Propiedad e Inquilino NO cambian
                    contratoExistente.getInquilino(),
                    nuevoValorMensual,
                    contratoExistente.isActivo() // Se mantiene el estado actual de activo
            );

            gestor.actualizarContrato(contratoConDatosActualizados);

        } else {
            System.out.println("Contrato con ID '" + id + "' no encontrado para actualizar.");
        }
    }

    /**
     * Solicita el ID de un contrato a finalizar y lo marca como inactivo a través del gestor.
     */
    private static void finalizarContrato() {
        System.out.println("\n--- Finalizar Contrato ---");
        System.out.print("Ingrese el ID del contrato a finalizar: ");
        String id = leerLinea();
        gestor.finalizarContrato(id);
    }

    /**
     * Solicita el ID de un contrato a eliminar y lo elimina a través del gestor.
     * Esta operación también elimina los pagos asociados.
     */
    private static void eliminarContrato() {
        System.out.println("\n--- Eliminar Contrato ---");
        System.out.print("Ingrese el ID del contrato a eliminar: ");
        String id = leerLinea();
        gestor.eliminarContrato(id);
    }

    // --- Menús de Gestión para Pagos ---

    /**
     * Muestra el menú para la gestión de pagos y maneja las operaciones correspondientes.
     */
    private static void menuGestionPagos() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Pagos ---");
            System.out.println("1. Registrar Pago");
            System.out.println("2. Listar Pagos (Todos)");
            System.out.println("3. Listar Pagos por Contrato");
            System.out.println("4. Buscar Pago por ID");
            System.out.println("5. Confirmar Pago");
            System.out.println("6. Actualizar Pago");
            System.out.println("7. Eliminar Pago");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    registrarPago();
                    break;
                case 2:
                    listarPagos();
                    break;
                case 3:
                    listarPagosPorContrato();
                    break;
                case 4:
                    buscarPagoPorId();
                    break;
                case 5:
                    confirmarPago();
                    break;
                case 6:
                    actualizarPago();
                    break;
                case 7:
                    eliminarPago();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // --- Métodos de CRUD para Pago ---

    /**
     * Solicita al usuario el ID de un contrato y los datos de un pago,
     * luego registra el nuevo pago a través del gestor.
     * Requiere que el contrato exista previamente.
     */
    private static void registrarPago() {
        System.out.println("\n--- Registrar Nuevo Pago ---");
        System.out.print("Ingrese ID del Contrato de Alquiler: ");
        String contratoId = leerLinea();

        Optional<ContratoAlquiler> contratoOpt = gestor.buscarContratoPorId(contratoId);
        if (!contratoOpt.isPresent()) {
            System.out.println("Error: Contrato con ID '" + contratoId + "' no encontrado.");
            return;
        }
        ContratoAlquiler contrato = contratoOpt.get(); // Obtiene la instancia real del contrato

        System.out.print("Fecha de Pago: ");
        LocalDate fechaPago = leerFecha();
        System.out.print("Monto del Pago: ");
        double monto = leerDoble();
        System.out.print("Periodo Correspondiente (Ej. 2024-05): ");
        String periodo = leerLinea();

        // Se crea un nuevo objeto Pago. El ID se deja nulo para que el gestor lo genere.
        // El estado 'confirmado' se inicializa a false por defecto para un pago recién registrado.
        Pago nuevoPago = new Pago(null, contrato, fechaPago, monto, false, periodo);
        gestor.registrarPago(nuevoPago);
    }

    /**
     * Lista todos los pagos registrados en el sistema.
     */
    private static void listarPagos() {
        System.out.println("\n--- Listado de Pagos ---");
        List<Pago> pagos = gestor.listarPagos();
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados.");
        } else {
            for (Pago p : pagos) {
                System.out.println("ID: " + p.getId() +
                        ", Contrato: " + p.getContrato().getId() +
                        ", Fecha: " + p.getFechaPago() +
                        ", Monto: " + p.getValorPago() +
                        ", Periodo: " + p.getPeriodoCorrespondiente() +
                        ", Confirmado: " + (p.isConfirmado() ? "Sí" : "No"));
            }
        }
    }

    /**
     * Solicita un ID de contrato y lista todos los pagos asociados a ese contrato.
     */
    private static void listarPagosPorContrato() {
        System.out.println("\n--- Listar Pagos por Contrato ---");
        System.out.print("Ingrese el ID del contrato para listar sus pagos: ");
        String contratoId = leerLinea();

        Optional<ContratoAlquiler> contratoOpt = gestor.buscarContratoPorId(contratoId);
        if (!contratoOpt.isPresent()) {
            System.out.println("Error: Contrato con ID '" + contratoId + "' no encontrado.");
            return;
        }

        List<Pago> pagos = gestor.listarPagosPorContrato(contratoId);
        if (pagos.isEmpty()) {
            System.out.println("No hay pagos registrados para el contrato '" + contratoId + "'.");
        } else {
            System.out.println("Pagos para el contrato '" + contratoId + "':");
            for (Pago p : pagos) {
                System.out.println("  ID: " + p.getId() +
                        ", Fecha: " + p.getFechaPago() +
                        ", Monto: " + p.getValorPago() +
                        ", Periodo: " + p.getPeriodoCorrespondiente() +
                        ", Confirmado: " + (p.isConfirmado() ? "Sí" : "No"));
            }
        }
    }

    /**
     * Solicita un ID de pago y muestra la información del pago encontrado.
     */
    private static void buscarPagoPorId() {
        System.out.println("\n--- Buscar Pago por ID ---");
        System.out.print("Ingrese el ID del pago a buscar: ");
        String id = leerLinea();
        Optional<Pago> pagoOpt = gestor.buscarPagoPorId(id);
        if (pagoOpt.isPresent()) {
            Pago p = pagoOpt.get();
            System.out.println("Pago encontrado:");
            System.out.println("ID: " + p.getId() +
                    ", Contrato: " + p.getContrato().getId() +
                    ", Fecha: " + p.getFechaPago() +
                    ", Monto: " + p.getValorPago() +
                    ", Periodo: " + p.getPeriodoCorrespondiente() +
                    ", Confirmado: " + (p.isConfirmado() ? "Sí" : "No"));
        } else {
            System.out.println("Pago con ID '" + id + "' no encontrado.");
        }
    }

    /**
     * Solicita el ID de un pago y lo marca como confirmado a través del gestor.
     */
    private static void confirmarPago() {
        System.out.println("\n--- Confirmar Pago ---");
        System.out.print("Ingrese el ID del pago a confirmar: ");
        String id = leerLinea();
        gestor.confirmarPago(id);
    }

    /**
     * Solicita el ID de un pago a actualizar y luego los nuevos datos para fecha, monto y período.
     * Permite mantener datos existentes si el usuario deja un campo vacío.
     */
    private static void actualizarPago() {
        System.out.println("\n--- Actualizar Pago ---");
        System.out.print("Ingrese el ID del pago a actualizar: ");
        String id = leerLinea();
        Optional<Pago> pagoOpt = gestor.buscarPagoPorId(id);

        if (pagoOpt.isPresent()) {
            Pago pagoExistente = pagoOpt.get();
            System.out.println("Pago actual para contrato " + pagoExistente.getContrato().getId() + ":");
            System.out.println("  Fecha: " + pagoExistente.getFechaPago() + ", Monto: " + pagoExistente.getValorPago() + ", Periodo: " + pagoExistente.getPeriodoCorrespondiente());

            System.out.print("Nueva Fecha de Pago (" + pagoExistente.getFechaPago() + ") (DD-MM-YYYY): ");
            String fechaStr = leerLinea();
            LocalDate nuevaFechaPago = pagoExistente.getFechaPago(); // Valor por defecto
            if (!fechaStr.isEmpty()) {
                try {
                    nuevaFechaPago = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Usar formatter
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha inválido, se mantiene la anterior.");
                }
            }

            System.out.print("Nuevo Monto (" + pagoExistente.getValorPago() + "): ");
            String montoStr = leerLinea();
            double nuevoMonto = pagoExistente.getValorPago(); // Valor por defecto
            if (!montoStr.isEmpty()) {
                try {
                    nuevoMonto = Double.parseDouble(montoStr);
                } catch (NumberFormatException e) {
                    System.out.println("Monto inválido, se mantiene el anterior.");
                }
            }

            System.out.print("Nuevo Periodo Correspondiente (" + pagoExistente.getPeriodoCorrespondiente() + "): ");
            String nuevoPeriodo = leerLinea();
            if (nuevoPeriodo.isEmpty()) {
                nuevoPeriodo = pagoExistente.getPeriodoCorrespondiente(); // Mantener el existente si está vacío
            }

            // Se crea un nuevo objeto Pago con los datos actualizados (o existentes).
            // La referencia al contrato y el estado de confirmación no cambian automáticamente.
            Pago pagoConDatosActualizados = new Pago(
                    pagoExistente.getId(),
                    pagoExistente.getContrato(), // La referencia al contrato NO cambia
                    nuevaFechaPago,
                    nuevoMonto,
                    pagoExistente.isConfirmado(), // Se mantiene el estado actual de confirmado
                    nuevoPeriodo
            );

            gestor.actualizarPago(pagoConDatosActualizados);

        } else {
            System.out.println("Pago con ID '" + id + "' no encontrado para actualizar.");
        }
    }

    /**
     * Solicita el ID de un pago a eliminar y lo elimina a través del gestor.
     */
    private static void eliminarPago() {
        System.out.println("\n--- Eliminar Pago ---");
        System.out.print("Ingrese el ID del pago a eliminar: ");
        String id = leerLinea();
        gestor.eliminarPago(id);
    }

    // --- Menús de Gestión para Incidencias ---

    /**
     * Muestra el menú para la gestión de incidencias y maneja las operaciones correspondientes.
     */
    private static void menuGestionIncidencias() {
        int opcion;
        do {
            System.out.println("\n--- Gestión de Incidencias ---");
            System.out.println("1. Reportar Incidencia");
            System.out.println("2. Listar Incidencias (Todas)");
            System.out.println("3. Listar Incidencias por Propiedad");
            System.out.println("4. Buscar Incidencia por ID");
            System.out.println("5. Actualizar Incidencia");
            System.out.println("6. Eliminar Incidencia");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1:
                    reportarIncidencia();
                    break;
                case 2:
                    listarIncidencias();
                    break;
                case 3:
                    listarIncidenciasPorPropiedad();
                    break;
                case 4:
                    buscarIncidenciaPorId();
                    break;
                case 5:
                    actualizarIncidencia();
                    break;
                case 6:
                    eliminarIncidencia();
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);
    }

    // --- Métodos de CRUD para Incidencia ---

    /**
     * Solicita al usuario el ID de la propiedad afectada y los datos de la incidencia,
     * luego reporta la nueva incidencia a través del gestor.
     * Requiere que la propiedad exista previamente.
     */
    private static void reportarIncidencia() {
        System.out.println("\n--- Reportar Nueva Incidencia ---");
        System.out.print("Ingrese ID de la Propiedad afectada: ");
        String propiedadId = leerLinea();

        Optional<Propiedad> propOpt = gestor.buscarPropiedadPorId(propiedadId);
        if (!propOpt.isPresent()) {
            System.out.println("Error: Propiedad con ID '" + propiedadId + "' no encontrada.");
            return;
        }
        Propiedad propiedad = propOpt.get(); // Obtiene la instancia real de la propiedad

        System.out.print("Descripción de la incidencia: ");
        String descripcion = leerLinea();
        System.out.print("Fecha de Reporte (DD-MM-YYYY): "); // Ajuste el mensaje para reflejar el formato de leerFecha()
        LocalDate fechaReporte = leerFecha();
        System.out.print("Costo Estimado (0 si desconocido): ");
        double costoEstimado = leerDoble();

        // Se crea un nuevo objeto Incidencia. El ID se deja nulo para que el gestor lo genere.
        // El estado inicial es "Pendiente".
        Incidencia nuevaIncidencia = new Incidencia(null, propiedad, descripcion, fechaReporte, "Pendiente", costoEstimado);
        gestor.reportarIncidencia(nuevaIncidencia);
    }

    /**
     * Lista todas las incidencias registradas en el sistema.
     */
    private static void listarIncidencias() {
        System.out.println("\n--- Listado de Incidencias ---");
        List<Incidencia> incidencias = gestor.listarIncidencias();
        if (incidencias.isEmpty()) {
            System.out.println("No hay incidencias registradas.");
        } else {
            for (Incidencia i : incidencias) {
                System.out.println("ID: " + i.getId() +
                        ", Propiedad: " + i.getPropiedad().getDireccion() + " (ID: " + i.getPropiedad().getId() + ")" +
                        ", Descripción: " + i.getDescripcion() +
                        ", Fecha Reporte: " + i.getFecha() +
                        ", Costo Estimado: " + i.getCostoEstimado() +
                        ", Estado: " + i.getEstado());
            }
        }
    }

    /**
     * Solicita un ID de propiedad y lista todas las incidencias asociadas a esa propiedad.
     */
    private static void listarIncidenciasPorPropiedad() {
        System.out.println("\n--- Listar Incidencias por Propiedad ---");
        System.out.print("Ingrese el ID de la propiedad para listar sus incidencias: ");
        String propiedadId = leerLinea();

        Optional<Propiedad> propOpt = gestor.buscarPropiedadPorId(propiedadId);
        if (!propOpt.isPresent()) {
            System.out.println("Error: Propiedad con ID '" + propiedadId + "' no encontrada.");
            return;
        }

        List<Incidencia> incidencias = gestor.listarIncidenciasPorPropiedad(propiedadId);
        if (incidencias.isEmpty()) {
            System.out.println("No hay incidencias registradas para la propiedad '" + propiedadId + "'.");
        } else {
            System.out.println("Incidencias para la propiedad '" + propiedadId + "':");
            for (Incidencia i : incidencias) {
                System.out.println("  ID: " + i.getId() +
                        ", Descripción: " + i.getDescripcion() +
                        ", Fecha Reporte: " + i.getFecha() +
                        ", Costo Estimado: " + i.getCostoEstimado() +
                        ", Estado: " + i.getEstado());
            }
        }
    }

    /**
     * Solicita un ID de incidencia y muestra la información de la incidencia encontrada.
     */
    private static void buscarIncidenciaPorId() {
        System.out.println("\n--- Buscar Incidencia por ID ---");
        System.out.print("Ingrese el ID de la incidencia a buscar: ");
        String id = leerLinea();
        Optional<Incidencia> incidenciaOpt = gestor.buscarIncidenciaPorId(id);
        if (incidenciaOpt.isPresent()) {
            Incidencia i = incidenciaOpt.get();
            System.out.println("Incidencia encontrada:");
            System.out.println("ID: " + i.getId() +
                    ", Propiedad: " + i.getPropiedad().getDireccion() + " (ID: " + i.getPropiedad().getId() + ")" +
                    ", Descripción: " + i.getDescripcion() +
                    ", Fecha Reporte: " + i.getFecha() +
                    ", Costo Estimado: " + i.getCostoEstimado() +
                    ", Estado: " + i.getEstado());
        } else {
            System.out.println("Incidencia con ID '" + id + "' no encontrada.");
        }
    }

    /**
     * Solicita el ID de una incidencia a actualizar y luego los nuevos datos para descripción, fecha, estado y costo.
     * Permite mantener datos existentes si el usuario deja un campo vacío.
     */
    private static void actualizarIncidencia() {
        System.out.println("\n--- Actualizar Incidencia ---");
        System.out.print("Ingrese el ID de la incidencia a actualizar: ");
        String id = leerLinea();
        Optional<Incidencia> incidenciaOpt = gestor.buscarIncidenciaPorId(id);

        if (incidenciaOpt.isPresent()) {
            Incidencia incidenciaExistente = incidenciaOpt.get();
            System.out.println("Incidencia actual para propiedad " + incidenciaExistente.getPropiedad().getDireccion() + ":");
            System.out.println("  Descripción: " + incidenciaExistente.getDescripcion() + ", Estado: " + incidenciaExistente.getEstado());

            System.out.print("Nueva Descripción (" + incidenciaExistente.getDescripcion() + "): ");
            String nuevaDescripcion = leerLinea();
            if (!nuevaDescripcion.isEmpty()) {
                incidenciaExistente.setDescripcion(nuevaDescripcion);
            }

            System.out.print("Nueva Fecha de Reporte (" + incidenciaExistente.getFecha() + ") (DD-MM-YYYY): ");
            String fechaStr = leerLinea();
            LocalDate nuevaFechaReporte = incidenciaExistente.getFecha(); // Valor por defecto
            if (!fechaStr.isEmpty()) {
                try {
                    nuevaFechaReporte = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd-MM-yyyy")); // Usar formatter
                } catch (DateTimeParseException e) {
                    System.out.println("Formato de fecha inválido, se mantiene la anterior.");
                }
            }
            incidenciaExistente.setFecha(nuevaFechaReporte);

            System.out.print("Nuevo Estado (Pendiente, En Proceso, Resuelta, Cancelada) (" + incidenciaExistente.getEstado() + "): ");
            String nuevoEstado = leerLinea();
            if (!nuevoEstado.isEmpty()) {
                incidenciaExistente.setEstado(nuevoEstado);
            }

            System.out.print("Nuevo Costo Estimado (" + incidenciaExistente.getCostoEstimado() + "): ");
            String costoStr = leerLinea();
            double nuevoCostoEstimado = incidenciaExistente.getCostoEstimado(); // Valor por defecto
            if (!costoStr.isEmpty()) {
                try {
                    nuevoCostoEstimado = Double.parseDouble(costoStr);
                } catch (NumberFormatException e) {
                    System.out.println("Costo estimado inválido, se mantiene el anterior.");
                }
            }
            incidenciaExistente.setCostoEstimado(nuevoCostoEstimado);

            // Se crea un nuevo objeto Incidencia con los datos actualizados (o existentes).
            // La referencia a la propiedad no cambia.
            Incidencia incidenciaConDatosActualizados = new Incidencia(
                    incidenciaExistente.getId(),
                    incidenciaExistente.getPropiedad(),
                    incidenciaExistente.getDescripcion(),
                    incidenciaExistente.getFecha(),
                    incidenciaExistente.getEstado(),
                    incidenciaExistente.getCostoEstimado()
            );

            gestor.actualizarIncidencia(incidenciaConDatosActualizados);

        } else {
            System.out.println("Incidencia con ID '" + id + "' no encontrada para actualizar.");
        }
    }

    /**
     * Solicita el ID de una incidencia a eliminar y la elimina a través del gestor.
     */
    private static void eliminarIncidencia() {
        System.out.println("\n--- Eliminar Incidencia ---");
        System.out.print("Ingrese el ID de la incidencia a eliminar: ");
        String id = leerLinea();
        gestor.eliminarIncidencia(id);
    }
}
package com.Sebaacostam3.gestoralquileres.servicio;

import com.Sebaacostam3.gestoralquileres.modelo.Propiedad;
import com.Sebaacostam3.gestoralquileres.modelo.Inquilino;
import com.Sebaacostam3.gestoralquileres.modelo.Pago;
import com.Sebaacostam3.gestoralquileres.modelo.Incidencia;
import com.Sebaacostam3.gestoralquileres.modelo.ContratoAlquiler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase de servicio que gestiona las operaciones de negocio para propiedades,
 * inquilinos, contratos, pagos e incidencias.
 * Actualmente, los datos se almacenan en memoria.
 */
public class GestorAlquileres {
    // Listas internas que simulan el almacenamiento de datos en memoria
    private List<Propiedad> propiedades;
    private List<Inquilino> inquilinos;
    private List<ContratoAlquiler> contratos;
    private List<Pago> pagos;
    private List<Incidencia> incidencias;

    // Contadores para generar IDs únicos para cada entidad
    private int nextPropiedadId = 1;
    private int nextInquilinoId = 1;
    private int nextContratoAlquilerId = 1;
    private int nextPagoId = 1;
    private int nextIncidenciaId = 1;

    /**
     * Constructor que inicializa todas las listas de almacenamiento.
     */
    public GestorAlquileres() {
        this.propiedades = new ArrayList<>();
        this.inquilinos = new ArrayList<>();
        this.contratos = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.incidencias = new ArrayList<>();
    }

    // --- Métodos de Gestión (CRUD y lógica de negocio) ---

    // ----- Propiedad -----

    /**
     * Genera un ID único para una nueva propiedad en formato "PRPXXX".
     * @return El ID generado.
     */
    private String generarSiguientePropiedadId() {
        return String.format("PRP%03d", nextPropiedadId++);
    }

    /**
     * Agrega una nueva propiedad al sistema. Si la propiedad no tiene ID, le asigna uno.
     * Realiza validación de ID duplicado.
     * @param propiedad El objeto Propiedad a agregar.
     */
    public void agregarPropiedad(Propiedad propiedad) {
        // Asigna un ID si la propiedad es nueva o no tiene uno asignado
        if (propiedad.getId() == null || propiedad.getId().trim().isEmpty()) {
            propiedad.setId(generarSiguientePropiedadId());
        }

        // Verifica si el ID ya existe para evitar duplicados
        if (buscarPropiedadPorId(propiedad.getId()).isPresent()) {
            System.out.println("Error: La propiedad con el ID " + propiedad.getId() + " ya existe.");
            return;
        }
        this.propiedades.add(propiedad);
        System.out.println("Propiedad '" + propiedad.getDireccion() + "' agregada correctamente.");
    }

    /**
     * Devuelve una lista de todas las propiedades registradas.
     * @return Una copia de la lista de propiedades para evitar modificaciones externas.
     */
    public List<Propiedad> listarPropiedades() {
        return new ArrayList<>(this.propiedades);
    }

    /**
     * Busca una propiedad por su ID único.
     * @param id El ID de la propiedad a buscar.
     * @return Un Optional que contiene la Propiedad si se encuentra, o un Optional vacío si no.
     */
    public Optional<Propiedad> buscarPropiedadPorId(String id) {
        for (Propiedad p : propiedades) {
            if (p.getId().equalsIgnoreCase(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * Actualiza la información de una propiedad existente.
     * @param propiedad El objeto Propiedad con los datos actualizados (el ID debe existir).
     * @return true si la propiedad fue actualizada, false si no se encontró.
     */
    public boolean actualizarPropiedad(Propiedad propiedad) {
        Optional<Propiedad> propiedadEncontrada = buscarPropiedadPorId(propiedad.getId());
        if (propiedadEncontrada.isPresent()) {
            Propiedad propiedadActualizada = propiedadEncontrada.get();
            propiedadActualizada.setDireccion(propiedad.getDireccion());
            propiedadActualizada.setTipo(propiedad.getTipo());
            propiedadActualizada.setMetrosCuadrados(propiedad.getMetrosCuadrados());
            propiedadActualizada.setAlquilada(propiedad.isAlquilada());
            propiedadActualizada.setPrecioMensual(propiedad.getPrecioMensual());

            System.out.println("Propiedad '" + propiedadActualizada.getId() + "' actualizada correctamente.");
            return true;
        }
        System.out.println("Error: No se encontró la propiedad con el ID " + propiedad.getId() + ".");
        return false;
    }

    /**
     * Elimina una propiedad del sistema, aplicando reglas de negocio.
     * Una propiedad no puede ser eliminada si tiene contratos vigentes o incidencias pendientes.
     * @param id El ID de la propiedad a eliminar.
     * @return true si la propiedad fue eliminada, false si no se encontró o no se pudo eliminar por reglas de negocio.
     */
    public boolean eliminarPropiedad(String id) {
        Optional<Propiedad> propiedadEncontrada = buscarPropiedadPorId(id);
        if (propiedadEncontrada.isPresent()) {
            Propiedad propiedad = propiedadEncontrada.get();
            // Regla de negocio: No se puede eliminar una propiedad si tiene un contrato vigente.
            for (ContratoAlquiler contrato : contratos) {
                if (contrato.getPropiedad().getId().equals(id) && contrato.isActivo()) {
                    System.out.println("Error: No se puede eliminar la propiedad con el ID " + id + " porque tiene un contrato vigente.");
                    return false;
                }
            }
            // Regla de negocio: No se puede eliminar una propiedad si tiene incidencias pendientes.
            for (Incidencia incidencia : incidencias) {
                if (incidencia.getPropiedad().getId().equals(id) && !incidencia.getEstado().equalsIgnoreCase("Resuelta") && !incidencia.getEstado().equalsIgnoreCase("Cancelada")) {
                    System.out.println("Error: No se puede eliminar la propiedad con el ID " + id + " porque tiene incidencias pendientes.");
                    return false;
                }
            }
            propiedades.remove(propiedad);
            System.out.println("Propiedad '" + id + "' eliminada correctamente.");
            return true;
        }
        System.out.println("Error: No se encontró la propiedad con el ID " + id + ".");
        return false;
    }

    // ----- Inquilino -----

    /**
     * Genera un ID único para un nuevo inquilino en formato "INQXXX".
     * @return El ID generado.
     */
    private String generarSiguienteInquilinoId() {
        return String.format("INQ%03d", nextInquilinoId++);
    }

    /**
     * Agrega un nuevo inquilino al sistema. Si el inquilino no tiene ID, le asigna uno.
     * Realiza validación de ID duplicado.
     * @param inquilino El objeto Inquilino a agregar.
     */
    public void agregarInquilino(Inquilino inquilino) {
        // Asigna un ID si el inquilino es nuevo o no tiene uno asignado
        if (inquilino.getId() == null || inquilino.getId().trim().isEmpty()) {
            inquilino.setId(generarSiguienteInquilinoId());
        }
        // Verifica si el ID ya existe para evitar duplicados
        if (buscarInquilinoPorId(inquilino.getId()).isPresent()) {
            System.out.println("Error: El inquilino con el ID " + inquilino.getId() + " ya existe.");
            return;
        }

        inquilinos.add(inquilino);
        System.out.println("Inquilino '" + inquilino.getNombre() + " " + inquilino.getApellido() + "' agregado correctamente. ID: " + inquilino.getId());
    }

    /**
     * Devuelve una lista de todos los inquilinos registrados.
     * @return Una copia de la lista de inquilinos para evitar modificaciones externas.
     */
    public List<Inquilino> listarInquilinos() {
        return new ArrayList<>(this.inquilinos);
    }

    /**
     * Busca un inquilino por su ID único.
     * @param id El ID del inquilino a buscar.
     * @return Un Optional que contiene el Inquilino si se encuentra, o un Optional vacío si no.
     */
    public Optional<Inquilino> buscarInquilinoPorId(String id) {
        for (Inquilino i : inquilinos) {
            if (i.getId().equalsIgnoreCase(id)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    /**
     * Actualiza la información de un inquilino existente.
     * @param inquilino El objeto Inquilino con los datos actualizados (el ID debe existir).
     * @return true si el inquilino fue actualizado, false si no se encontró.
     */
    public boolean actualizarInquilino(Inquilino inquilino) {
        Optional<Inquilino> inquilinoEncontrado = buscarInquilinoPorId(inquilino.getId());
        if (inquilinoEncontrado.isPresent()) {
            Inquilino inquilinoActualizado = inquilinoEncontrado.get();
            inquilinoActualizado.setNombre(inquilino.getNombre());
            inquilinoActualizado.setApellido(inquilino.getApellido());
            inquilinoActualizado.setTelefono(inquilino.getTelefono());
            inquilinoActualizado.setEmail(inquilino.getEmail());

            System.out.println("Inquilino '" + inquilinoActualizado.getId() + "' actualizado correctamente.");
            return true;
        }
        System.out.println("Error: No se encontró el inquilino con el ID " + inquilino.getId() + ".");
        return false;
    }

    /**
     * Elimina un inquilino del sistema, aplicando reglas de negocio.
     * Un inquilino no puede ser eliminado si tiene contratos vigentes.
     * @param id El ID del inquilino a eliminar.
     * @return true si el inquilino fue eliminado, false si no se encontró o no se pudo eliminar por reglas de negocio.
     */
    public boolean eliminarInquilino(String id) {
        Optional<Inquilino> inquilinoEncontrado = buscarInquilinoPorId(id);
        if (inquilinoEncontrado.isPresent()) {
            Inquilino inquilino = inquilinoEncontrado.get();
            // Regla de negocio: No se puede eliminar un inquilino si tiene un contrato vigente.
            for (ContratoAlquiler contrato : contratos) {
                if (contrato.getInquilino().getId().equals(id) && contrato.isActivo()) {
                    System.out.println("Error: No se puede eliminar el inquilino con el ID " + id + " porque tiene un contrato vigente.");
                    return false;
                }
            }
            inquilinos.remove(inquilino);
            System.out.println("Inquilino '" + id + "' eliminado correctamente.");
            return true;
        }
        System.out.println("Error: No se encontró el inquilino con el ID " + id + ".");
        return false;
    }

    // ----- ContratoAlquiler -----

    /**
     * Genera un ID único para un nuevo contrato en formato "CONXXX".
     * @return El ID generado.
     */
    private String generarSiguienteContratoId() {
        return String.format("CON%03d", nextContratoAlquilerId++);
    }

    /**
     * Busca un contrato de alquiler por su ID único.
     * @param id El ID del contrato a buscar.
     * @return Un Optional que contiene el ContratoAlquiler si se encuentra, o un Optional vacío si no.
     */
    public Optional<ContratoAlquiler> buscarContratoPorId(String id) {
        for (ContratoAlquiler c : contratos) {
            if (c.getId().equalsIgnoreCase(id)) {
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    /**
     * Crea un nuevo contrato de alquiler, aplicando diversas validaciones y reglas de negocio.
     * @param nuevoContrato El objeto ContratoAlquiler a crear, con referencias a Propiedad e Inquilino.
     */
    public void crearContrato(ContratoAlquiler nuevoContrato) {
        // Validar que el contrato referencia a una Propiedad y un Inquilino con IDs válidos
        if (nuevoContrato.getPropiedad() == null || nuevoContrato.getPropiedad().getId() == null ||
                nuevoContrato.getInquilino() == null || nuevoContrato.getInquilino().getId() == null) {
            System.out.println("Error: El contrato debe referenciar una Propiedad y un Inquilino válidos.");
            return;
        }

        // 1. Validar que la propiedad y el inquilino existen en el gestor
        Optional<Propiedad> propiedadEncontrada = buscarPropiedadPorId(nuevoContrato.getPropiedad().getId());
        Optional<Inquilino> inquilinoEncontrado = buscarInquilinoPorId(nuevoContrato.getInquilino().getId());
        if (!propiedadEncontrada.isPresent() || !inquilinoEncontrado.isPresent()) {
            System.out.println("Error: No se encontró la propiedad o el inquilino con el ID " + nuevoContrato.getPropiedad().getId() + " o " + nuevoContrato.getInquilino().getId() + ".");
            return;
        }

        Propiedad propiedadReal = propiedadEncontrada.get();
        Inquilino inquilinoReal = inquilinoEncontrado.get();

        // 2. Regla de negocio: No se puede crear un contrato si la propiedad ya está alquilada.
        if (propiedadReal.isAlquilada()) {
            System.out.println("Error: No se puede crear un contrato para la propiedad con ID " + nuevoContrato.getPropiedad().getId() + " porque ya está alquilada.");
            return;
        }

        // 3. Asignar el ID al contrato si no lo tiene, o verificar que el ID proporcionado no exista
        if (nuevoContrato.getId() == null || nuevoContrato.getId().trim().isEmpty()) {
            nuevoContrato.setId(generarSiguienteContratoId());
        } else {
            if (buscarContratoPorId(nuevoContrato.getId()).isPresent()) {
                System.out.println("Error: El contrato con el ID " + nuevoContrato.getId() + " ya existe.");
                return;
            }
        }

        // 4. Establecer las referencias reales de Propiedad e Inquilino en el nuevoContrato
        // Esto asegura que el contrato apunte a los objetos gestionados por GestorAlquileres.
        nuevoContrato.setPropiedad(propiedadReal);
        nuevoContrato.setInquilino(inquilinoReal);
        nuevoContrato.setActivo(true); // Un contrato nuevo se crea como activo.

        // 5. Marcar la propiedad como alquilada
        propiedadReal.setAlquilada(true);

        // 6. Añadir el contrato a la lista
        this.contratos.add(nuevoContrato);
        System.out.println("Contrato creado con exito para la propiedad '" + propiedadReal.getDireccion() + "' e inquilino '" + inquilinoReal.getNombre() + "'. ID: " + nuevoContrato.getId());
    }

    /**
     * Lista todos los contratos registrados en el sistema.
     * @return Una copia de la lista de todos los contratos.
     */
    public List<ContratoAlquiler> listarContratos() {
        return new ArrayList<>(this.contratos);
    }

    /**
     * Lista todos los contratos que están actualmente activos.
     * @return Una lista de contratos activos.
     */
    public List<ContratoAlquiler> listarContratosActivos() {
        List<ContratoAlquiler> activos = new ArrayList<>();
        for (ContratoAlquiler c : contratos) {
            if (c.isActivo()) {
                activos.add(c);
            }
        }
        return activos;
    }

    /**
     * Actualiza la información de un contrato existente.
     * Solo permite actualizar ciertos campos como fecha de finalización y valor mensual.
     * El estado 'activo' se maneja con el método finalizarContrato.
     * @param contratoActualizado El objeto ContratoAlquiler con los datos a actualizar.
     * @return true si el contrato fue actualizado, false en caso de no encontrarlo.
     */
    public boolean actualizarContrato(ContratoAlquiler contratoActualizado) {
        Optional<ContratoAlquiler> contratoEncontrado = buscarContratoPorId(contratoActualizado.getId());
        if (contratoEncontrado.isPresent()) {
            ContratoAlquiler contratoExistente = contratoEncontrado.get();
            contratoExistente.setFechaFin(contratoActualizado.getFechaFin());
            contratoExistente.setValorMensual(contratoActualizado.getValorMensual());
            System.out.println("Contrato '"+ contratoActualizado.getId() + "' actualizado con éxito.");
            return true;
        }
        System.out.println("Error: No se encontró el contrato con ID " + contratoActualizado.getId() + ".");
        return false;
    }

    /**
     * Finaliza un contrato de alquiler, marcándolo como inactivo y liberando la propiedad asociada.
     * @param contratoId El ID del contrato a finalizar.
     * @return true si fue finalizado, false si no se encuentra o ya estaba inactivo.
     */
    public boolean finalizarContrato(String contratoId){
        Optional<ContratoAlquiler> contratoEncontrado = buscarContratoPorId(contratoId);
        if (contratoEncontrado.isPresent()) {
            ContratoAlquiler contrato = contratoEncontrado.get();
            if (!contrato.isActivo()) {
                System.out.println("Error: El contrato " + contratoId + " ya está inactivo.");
                return false;
            }

            contrato.setActivo(false);
            contrato.getPropiedad().setAlquilada(false);
            System.out.println("Contrato '" + contratoId + "' finalizado y propiedad '" + contrato.getPropiedad().getDireccion() + "' liberada.");

            // Nota de diseño: En un sistema real con persistencia de pagos, los contratos no se eliminarían,
            // solo se inactivarían para mantener el historial de pagos.
            System.out.println("Contrato '" + contratoId + "' inactivo.");
            return true;
        }
        System.out.println("Error: No se encontró el contrato con el ID " + contratoId + ".");
        return false;
    }

    /**
     * Elimina un contrato de alquiler del sistema de forma definitiva.
     * Esta acción debe ser usada con precaución, ya que elimina el registro
     * y los pagos asociados a este contrato también serán eliminados
     * para mantener la integridad referencial.
     *
     * @param id El ID del contrato a eliminar.
     * @return true si el contrato fue eliminado, false si no se encontró o no se pudo eliminar.
     */
    public boolean eliminarContrato(String id) {
        Optional<ContratoAlquiler> contratoEncontrado = buscarContratoPorId(id);
        if (contratoEncontrado.isPresent()) {
            ContratoAlquiler contrato = contratoEncontrado.get();

            // Antes de eliminar el contrato, eliminar todos los pagos asociados para mantener la integridad
            // Se hace una copia de la lista para evitar ConcurrentModificationException al modificar la lista original.
            List<Pago> pagosAEliminar = new ArrayList<>(listarPagosPorContrato(id));
            for (Pago pago : pagosAEliminar) {
                eliminarPago(pago.getId());
            }
            System.out.println("Se han eliminado los pagos asociados al contrato '" + id + "'.");

            // Si el contrato estaba activo, liberar la propiedad
            if (contrato.isActivo()) {
                contrato.getPropiedad().setAlquilada(false);
                System.out.println("Propiedad '" + contrato.getPropiedad().getDireccion() + "' liberada al eliminar el contrato.");
            }

            // Finalmente, elimina el contrato de la lista
            contratos.remove(contrato);
            System.out.println("Contrato '" + id + "' eliminado permanentemente del sistema.");
            return true;
        }
        System.out.println("Error: No se encontró el contrato con el ID " + id + " para eliminar.");
        return false;
    }


    // ----- Pago -----

    /**
     * Genera un ID único para un nuevo pago en formato "PAGXXX".
     * @return El ID generado.
     */
    private String generarSiguientePagoId() {
        return String.format("PAG%03d", nextPagoId++);
    }

    /**
     * Lista todos los pagos registrados en el sistema.
     * @return Una copia de la lista de todos los pagos.
     */
    public List<Pago> listarPagos() {
        return new ArrayList<>(this.pagos);
    }

    /**
     * Busca un pago por su ID único.
     * @param id El ID del pago a buscar.
     * @return Un Optional que contiene el Pago si se encuentra, o un Optional vacío si no.
     */
    public Optional<Pago> buscarPagoPorId(String id) {
        for (Pago p : pagos) {
            if (p.getId().equalsIgnoreCase(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /**
     * Registra un nuevo pago en el sistema.
     * @param nuevoPago El pago a registrar (debe referenciar un contrato existente por ID).
     */
    public void registrarPago(Pago nuevoPago) {
        // Validar que el pago referencia a un contrato con ID válido
        if (nuevoPago.getContrato() == null || nuevoPago.getContrato().getId() == null) {
            System.out.println("Error: El nuevo pago debe referenciar un Contrato de Alquiler válido (con ID).");
            return;
        }

        // 1. Validar que el contrato exista
        Optional<ContratoAlquiler> contratoEncontrado = buscarContratoPorId(nuevoPago.getContrato().getId());
        if (!contratoEncontrado.isPresent()) {
            System.out.println("Error: El contrato con ID " + nuevoPago.getContrato().getId() + " no existe.");
            return;
        }

        ContratoAlquiler contratoReal = contratoEncontrado.get();

        // 2. Asignar el ID al pago si no lo tiene, o verificar que el ID proporcionado no exista
        if (nuevoPago.getId() == null || nuevoPago.getId().trim().isEmpty()) {
            nuevoPago.setId(generarSiguientePagoId());
        } else {
            if (buscarPagoPorId(nuevoPago.getId()).isPresent()) {
                System.out.println("Error: El pago con ID " + nuevoPago.getId() + " ya existe.");
                return;
            }
        }

        // 3. Establecer la referencia real al Contrato en el nuevoPago
        nuevoPago.setContrato(contratoReal);
        nuevoPago.setConfirmado(false); // Por defecto, un pago nuevo no está confirmado.

        // Añadir el pago a la lista.
        this.pagos.add(nuevoPago);
        System.out.println("Pago registrado para el contrato " + contratoReal.getId() + " del periodo " + nuevoPago.getPeriodoCorrespondiente() + ". ID: " + nuevoPago.getId());
    }

    /**
     * Lista todos los pagos asociados a un contrato específico.
     * @param contratoId El ID del contrato cuyos pagos se desean listar.
     * @return Una lista de pagos de ese contrato.
     */
    public List<Pago> listarPagosPorContrato(String contratoId) {
        List<Pago> pagosDelContrato = new ArrayList<>();
        for (Pago p : pagos) {
            if (p.getContrato() != null && p.getContrato().getId().equalsIgnoreCase(contratoId)) {
                pagosDelContrato.add(p);
            }
        }
        return pagosDelContrato;
    }

    /**
     * Marca un pago como confirmado (recibido y procesado).
     * @param pagoId El ID del pago a confirmar.
     * @return true si el pago fue confirmado, false si no se encontró o ya estaba confirmado.
     */
    public boolean confirmarPago(String pagoId) {
        Optional<Pago> pagoEncontrado = buscarPagoPorId(pagoId);
        if (pagoEncontrado.isPresent()) {
            Pago pago = pagoEncontrado.get();
            if (!pago.isConfirmado()) {
                pago.setConfirmado(true);
                System.out.println("Pago " + pago.getId() + " confirmado.");
                return true;
            } else {
                System.out.println("El pago con ID " + pagoId + " ya estaba confirmado.");
                return false;
            }
        }
        System.out.println("Error: Pago con ID " + pagoId + " no encontrado para confirmar.");
        return false;
    }

    /**
     * Actualiza la información de un pago existente.
     * Permite actualizar fechaPago, monto y periodoCorrespondiente.
     * El estado de confirmado se maneja con el método confirmarPago.
     * @param pagoActualizado El objeto Pago con los datos actualizados.
     * @return true si el pago fue actualizado, false si no se encontró.
     */
    public boolean actualizarPago(Pago pagoActualizado) {
        Optional<Pago> pagoEncontrado = buscarPagoPorId(pagoActualizado.getId());
        if (pagoEncontrado.isPresent()) {
            Pago pagoExistente = pagoEncontrado.get();
            pagoExistente.setFechaPago(pagoActualizado.getFechaPago());
            pagoExistente.setValorPago(pagoActualizado.getValorPago());
            pagoExistente.setPeriodoCorrespondiente(pagoActualizado.getPeriodoCorrespondiente());

            System.out.println("Pago '" + pagoActualizado.getId() + "' actualizado correctamente.");
            return true;
        }
        System.out.println("Error: No se encontró el pago con el ID " + pagoActualizado.getId() + " para actualizar.");
        return false;
    }

    /**
     * Elimina un pago del sistema.
     * Nota: En sistemas reales, los pagos raramente se eliminan de forma definitiva;
     * en su lugar, se "anulan" o "reversan" para mantener un registro de auditoría.
     * @param id El ID del pago a eliminar.
     * @return true si el pago fue eliminado, false si no se encontró.
     */
    public boolean eliminarPago(String id) {
        Optional<Pago> pagoEncontrado = buscarPagoPorId(id);
        if (pagoEncontrado.isPresent()) {
            pagos.remove(pagoEncontrado.get());
            System.out.println("Pago '" + id + "' eliminado correctamente.");
            return true;
        }
        System.out.println("Error: No se encontró el pago con el ID " + id + " para eliminar.");
        return false;
    }


    // ----- Incidencia -----

    /**
     * Genera un ID único para una nueva incidencia en formato "INCXXX".
     * @return El ID generado.
     */
    public String generarSiguienteIncidenciaId() {
        return String.format("INC%03d", nextIncidenciaId++);
    }


    /**
     * Busca una incidencia por su ID único.
     * @param id El ID de la incidencia a buscar.
     * @return Un Optional que contiene la Incidencia si se encuentra, o un Optional vacío si no.
     */
    public Optional<Incidencia> buscarIncidenciaPorId(String id) {
        for (Incidencia i : incidencias) {
            if (i.getId().equalsIgnoreCase(id)) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }

    /**
     * Reporta una nueva incidencia para una propiedad.
     * @param nuevaIncidencia El objeto Incidencia a reportar (debe referenciar una propiedad existente por ID).
     */
    public void reportarIncidencia(Incidencia nuevaIncidencia) {
        // Validar que la incidencia referencia una propiedad con ID válido
        if (nuevaIncidencia.getPropiedad() == null || nuevaIncidencia.getPropiedad().getId() == null) {
            System.out.println("Error: La incidencia debe referenciar una Propiedad válida (con ID).");
            return;
        }

        // 1. Validar que la propiedad exista
        Optional<Propiedad> propOpt = buscarPropiedadPorId(nuevaIncidencia.getPropiedad().getId());
        if (!propOpt.isPresent()) {
            System.out.println("Error: La propiedad con ID " + nuevaIncidencia.getPropiedad().getId() + " no existe.");
            return;
        }

        Propiedad propiedadReal = propOpt.get(); // Obtener la instancia real de la propiedad

        // 2. Asignar el ID a la incidencia si no lo tiene, o verificar que el ID proporcionado no exista
        if (nuevaIncidencia.getId() == null || nuevaIncidencia.getId().trim().isEmpty()) {
            nuevaIncidencia.setId(generarSiguienteIncidenciaId());
        } else {
            if (buscarIncidenciaPorId(nuevaIncidencia.getId()).isPresent()) {
                System.out.println("Error: Ya existe una incidencia con el ID " + nuevaIncidencia.getId() + ".");
                return;
            }
        }

        // 3. Establecer la referencia real a la Propiedad en la nuevaIncidencia
        nuevaIncidencia.setPropiedad(propiedadReal);
        nuevaIncidencia.setEstado("Pendiente"); // Estado inicial por defecto para una nueva incidencia.

        // 4. Añadir la incidencia a la lista
        this.incidencias.add(nuevaIncidencia);
        System.out.println("Incidencia reportada para la propiedad '" + propiedadReal.getDireccion() + "'. ID: " + nuevaIncidencia.getId());
    }

    /**
     * Lista todas las incidencias registradas en el sistema.
     * @return Una copia de la lista de todas las incidencias.
     */
    public List<Incidencia> listarIncidencias() {
        return new ArrayList<>(this.incidencias);
    }

    /**
     * Lista todas las incidencias asociadas a una propiedad específica.
     * @param propiedadId El ID de la propiedad cuyas incidencias se desean listar.
     * @return Una lista de incidencias de esa propiedad.
     */
    public List<Incidencia> listarIncidenciasPorPropiedad(String propiedadId) {
        List<Incidencia> incidenciasDePropiedad = new ArrayList<>();
        for (Incidencia i : incidencias) {
            if (i.getPropiedad() != null && i.getPropiedad().getId().equalsIgnoreCase(propiedadId)) {
                incidenciasDePropiedad.add(i);
            }
        }
        return incidenciasDePropiedad;
    }

    /**
     * Actualiza la información de una incidencia existente.
     * Permite actualizar descripción, fechaReporte, estado y costoEstimado.
     * @param incidenciaActualizada El objeto Incidencia con los datos actualizados.
     * @return true si la incidencia fue actualizada, false si no se encontró.
     */
    public boolean actualizarIncidencia(Incidencia incidenciaActualizada) {
        Optional<Incidencia> incidenciaEncontrada = buscarIncidenciaPorId(incidenciaActualizada.getId());
        if (incidenciaEncontrada.isPresent()) {
            Incidencia incidenciaExistente = incidenciaEncontrada.get();
            incidenciaExistente.setDescripcion(incidenciaActualizada.getDescripcion());
            incidenciaExistente.setFecha(incidenciaActualizada.getFecha());
            incidenciaExistente.setEstado(incidenciaActualizada.getEstado());
            incidenciaExistente.setCostoEstimado(incidenciaActualizada.getCostoEstimado());

            System.out.println("Incidencia '" + incidenciaActualizada.getId() + "' actualizada correctamente.");
            return true;
        }
        System.out.println("Error: No se encontró la incidencia con el ID " + incidenciaActualizada.getId() + " para actualizar.");
        return false;
    }

    /**
     * Elimina una incidencia del sistema.
     * @param id El ID de la incidencia a eliminar.
     * @return true si la incidencia fue eliminada, false si no se encontró.
     */
    public boolean eliminarIncidencia(String id) {
        Optional<Incidencia> incidenciaEncontrada = buscarIncidenciaPorId(id);
        if (incidenciaEncontrada.isPresent()) {
            incidencias.remove(incidenciaEncontrada.get());
            System.out.println("Incidencia '" + id + "' eliminada correctamente.");
            return true;
        }
        System.out.println("Error: No se encontró la incidencia con el ID " + id + " para eliminar.");
        return false;
    }

}
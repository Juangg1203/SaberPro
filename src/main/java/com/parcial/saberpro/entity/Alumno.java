package com.parcial.saberpro.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "alumnos")
public class Alumno {

    @Id
    private String id;

    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @Indexed(unique = true)
    @NotBlank
    private String cedula;
    private String email;
    private String telefono;
    private LocalDate fechaNacimiento;

    private String facultadId;
    private String facultadNombre;
    private String programaAcademico;
    private String semestre;
    private String codigoEstudiante;

    // ── Créditos académicos ───────────────────────────────────────────────────
    private Integer creditosTotalesPrograma;   // Total de créditos que tiene el programa
    private Integer creditosCursados;          // Créditos que ya cursó el estudiante
    private Double  porcentajeCreditos;        // % calculado al guardar
    private static final double MINIMO_CREDITOS_PORCENTAJE = 75.0; // Regla UTS

    private String estadoSaberPro;
    private boolean aprobadoPorCoordinacion;
    private LocalDateTime fechaAprobacion;
    private String aprobadoPor;

    private String comprobantePagoPath;
    private LocalDateTime fechaCargoPago;
    private boolean pagoVerificado;
    private LocalDateTime fechaVerificacionPago; // ✅ NUEVO

    // ✅ NUEVO: "TYT" para tecnólogos, "SABER_PRO" para ingenieros
    private String tipoPrueba;

    // ✅ NUEVO: PENDIENTE_PRUEBA → EN_PROCESO → RESULTADOS_PUBLICADOS
    private String estadoPrueba;

    // ── Convocatoria de presentación ──────────────────────────────────────
    private String lugarPresentacion;     // Ej: "UIS — Bucaramanga, Bloque A Salon 201"
    private java.time.LocalDate fechaPresentacionConvocatoria; // Fecha oficial asignada por coordinación
    private String horaPresentacion;      // Ej: "08:00 AM"
    private String observacionesConvocatoria; // Instrucciones adicionales

    // ── Historial de pruebas (múltiples intentos) ────────────────────────
    private java.util.List<ResultadoSaberPro> historialResultados = new java.util.ArrayList<>();
    private Integer numeroPrueba = 1;     // Número de la prueba actual (1, 2, 3...)

    private ResultadoSaberPro resultadoUnico;
    private ResultadoSaberPro resultadoTotal;

    private boolean tieneBeneficio;
    private String tipoBeneficio;
    private boolean activo;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Alumno() {
        this.estadoSaberPro = "PENDIENTE";
        this.aprobadoPorCoordinacion = false;
        this.pagoVerificado = false;
        this.tieneBeneficio = false;
        this.activo = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Calcula el porcentaje de créditos cursados sobre el total del programa.
     * Retorna 0 si no hay datos suficientes.
     */
    public double calcularPorcentajeCreditos() {
        if (creditosTotalesPrograma == null || creditosTotalesPrograma <= 0
                || creditosCursados == null) return 0.0;
        return (creditosCursados * 100.0) / creditosTotalesPrograma;
    }

    /**
     * Indica si el estudiante cumple el mínimo del 75% de créditos cursados.
     */
    public boolean cumpleRequisitoCreditos() {
        return calcularPorcentajeCreditos() >= MINIMO_CREDITOS_PORCENTAJE;
    }

    /**
     * Cuántos créditos le faltan para llegar al 75%.
     * Retorna 0 si ya cumple o si no hay datos.
     */
    public int creditosFaltantes() {
        if (creditosTotalesPrograma == null || creditosTotalesPrograma <= 0) return 0;
        int minimo = (int) Math.ceil(creditosTotalesPrograma * MINIMO_CREDITOS_PORCENTAJE / 100.0);
        int cursados = creditosCursados != null ? creditosCursados : 0;
        return Math.max(0, minimo - cursados);
    }

    /**
     * Determina automáticamente el tipo de prueba según el programa académico.
     * Tecnólogo → TYT | Ingeniero / Profesional → SABER_PRO
     */
    public String getTipoPruebaCalculado() {
        if (facultadNombre == null) return "SABER_PRO";
        String fn = facultadNombre.toLowerCase();
        if (fn.startsWith("tecnolog") || fn.startsWith("técnolog")) return "TYT";
        return "SABER_PRO";
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getFacultadId() { return facultadId; }
    public void setFacultadId(String facultadId) { this.facultadId = facultadId; }

    public String getFacultadNombre() { return facultadNombre; }
    public void setFacultadNombre(String facultadNombre) { this.facultadNombre = facultadNombre; }

    public String getProgramaAcademico() { return programaAcademico; }
    public void setProgramaAcademico(String programaAcademico) { this.programaAcademico = programaAcademico; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }

    public Integer getCreditosTotalesPrograma() { return creditosTotalesPrograma; }
    public void setCreditosTotalesPrograma(Integer creditosTotalesPrograma) {
        this.creditosTotalesPrograma = creditosTotalesPrograma;
        if (creditosTotalesPrograma != null && creditosCursados != null)
            this.porcentajeCreditos = calcularPorcentajeCreditos();
    }

    public Integer getCreditosCursados() { return creditosCursados; }
    public void setCreditosCursados(Integer creditosCursados) {
        this.creditosCursados = creditosCursados;
        if (creditosTotalesPrograma != null && creditosCursados != null)
            this.porcentajeCreditos = calcularPorcentajeCreditos();
    }

    public Double getPorcentajeCreditos() { return porcentajeCreditos; }
    public void setPorcentajeCreditos(Double porcentajeCreditos) { this.porcentajeCreditos = porcentajeCreditos; }

    public String getEstadoSaberPro() { return estadoSaberPro; }
    public void setEstadoSaberPro(String estadoSaberPro) { this.estadoSaberPro = estadoSaberPro; }

    public boolean isAprobadoPorCoordinacion() { return aprobadoPorCoordinacion; }
    public void setAprobadoPorCoordinacion(boolean aprobadoPorCoordinacion) { this.aprobadoPorCoordinacion = aprobadoPorCoordinacion; }

    public LocalDateTime getFechaAprobacion() { return fechaAprobacion; }
    public void setFechaAprobacion(LocalDateTime fechaAprobacion) { this.fechaAprobacion = fechaAprobacion; }

    public String getAprobadoPor() { return aprobadoPor; }
    public void setAprobadoPor(String aprobadoPor) { this.aprobadoPor = aprobadoPor; }

    public String getComprobantePagoPath() { return comprobantePagoPath; }
    public void setComprobantePagoPath(String comprobantePagoPath) { this.comprobantePagoPath = comprobantePagoPath; }

    public LocalDateTime getFechaCargoPago() { return fechaCargoPago; }
    public void setFechaCargoPago(LocalDateTime fechaCargoPago) { this.fechaCargoPago = fechaCargoPago; }

    public boolean isPagoVerificado() { return pagoVerificado; }
    public void setPagoVerificado(boolean pagoVerificado) { this.pagoVerificado = pagoVerificado; }

    public LocalDateTime getFechaVerificacionPago() { return fechaVerificacionPago; }
    public void setFechaVerificacionPago(LocalDateTime fechaVerificacionPago) { this.fechaVerificacionPago = fechaVerificacionPago; }

    public String getTipoPrueba() { return tipoPrueba; }
    public void setTipoPrueba(String tipoPrueba) { this.tipoPrueba = tipoPrueba; }

    public String getEstadoPrueba() { return estadoPrueba; }
    public void setEstadoPrueba(String estadoPrueba) { this.estadoPrueba = estadoPrueba; }

    public String getLugarPresentacion() { return lugarPresentacion; }
    public void setLugarPresentacion(String v) { this.lugarPresentacion = v; }
    public java.time.LocalDate getFechaPresentacionConvocatoria() { return fechaPresentacionConvocatoria; }
    public void setFechaPresentacionConvocatoria(java.time.LocalDate v) { this.fechaPresentacionConvocatoria = v; }
    public String getHoraPresentacion() { return horaPresentacion; }
    public void setHoraPresentacion(String v) { this.horaPresentacion = v; }
    public String getObservacionesConvocatoria() { return observacionesConvocatoria; }
    public void setObservacionesConvocatoria(String v) { this.observacionesConvocatoria = v; }
    public java.util.List<ResultadoSaberPro> getHistorialResultados() { return historialResultados; }
    public void setHistorialResultados(java.util.List<ResultadoSaberPro> v) { this.historialResultados = v != null ? v : new java.util.ArrayList<>(); }
    public Integer getNumeroPrueba() { return numeroPrueba != null ? numeroPrueba : 1; }
    public void setNumeroPrueba(Integer v) { this.numeroPrueba = v; }

    public ResultadoSaberPro getResultadoUnico() { return resultadoUnico; }
    public void setResultadoUnico(ResultadoSaberPro resultadoUnico) { this.resultadoUnico = resultadoUnico; }

    public ResultadoSaberPro getResultadoTotal() { return resultadoTotal; }
    public void setResultadoTotal(ResultadoSaberPro resultadoTotal) { this.resultadoTotal = resultadoTotal; }

    public boolean isTieneBeneficio() { return tieneBeneficio; }
    public void setTieneBeneficio(boolean tieneBeneficio) { this.tieneBeneficio = tieneBeneficio; }

    public String getTipoBeneficio() { return tipoBeneficio; }
    public void setTipoBeneficio(String tipoBeneficio) { this.tipoBeneficio = tipoBeneficio; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
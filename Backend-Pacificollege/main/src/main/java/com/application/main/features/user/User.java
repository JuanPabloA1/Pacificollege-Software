package com.application.main.features.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_pacificollege", schema="public")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "contrato")
    private String contrato;

    @Column(name = "nombres_titular")
    private String nombres_titular;

    @Column(name = "apellidos_titular")
    private String apellidos_titular;

    @Column(name = "correo")
    private String correo;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "celular")
    private String celular;

    @Column(name = "plan")
    private String plan;

    @Column(name = "fecha_inicio")
    private Date fecha_inicio;

    @Column(name = "fecha_final")
    private Date fecha_final;

    @Column(name = "total")
    private Integer total;

    @Column(name = "cuota_inicial")
    private Integer cuota_inicial;

    @Column(name = "saldo")
    private Integer saldo;

    @Column(name = "valor_cuota")
    private Integer valor_cuota;

    @Column(name = "fecha_pago")
    private Date fecha_pago;

    @Column(name = "cedula_beneficiario")
    private String cedula_beneficiario;

    @Column(name = "nombres_b")
    private String nombres_b;

    @Column(name = "apellidos_b")
    private String apellidos_b;

    @Column(name = "sexo_b")
    private String sexo_b;

    @Column(name = "correo_b")
    private String correo_b;

    @Column(name = "edad_b")
    private String edad_b;

    @Column(name = "fecha_nacimiento_b")
    private Date fecha_nacimiento_b;

    @Column(name = "direccion_b")
    private String direccion_b;

    @Column(name = "telefono_b")
    private String telefono_b;

    @Column(name = "consultante_b")
    private String consultante_b;

    @Column(name = "actual_episodio_b")
    private String actual_episodio;

    @Column(name = "ultimo_episodio_b")
    private Date ultimo_episodio;

    @Column(name = "induccion_b")
    private Date induccion_b;

    @Column(name = "congelamiento")
    private Date congelamiento;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "fecha_graduando")
    private Date fecha_graduando;

    @Column(name = "tipo_estudiante")
    private String tipoEstudiante;
}

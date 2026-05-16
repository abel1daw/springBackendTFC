package com.pidawe.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaPedido =
            LocalDateTime.now();

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado =
            EstadoPedido.PENDIENTE;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @JsonIgnore
    @OneToMany(mappedBy = "pedido",
            cascade = CascadeType.ALL)
    private List<PedidoItem> items;

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PedidoItem> getItems() {
        return items;
    }
}
package com.alonsocode.trakyflow.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Nombre de categoría es requerido")
    @Size(max = 100, message = "Nombre no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;
    
    @Size(max = 500, message = "Descripción no puede exceder 500 caracteres")
    @Column(length = 500)
    private String description;
    
    @NotNull(message = "Tipo de categoría es requerido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private CategoryType type;
    
    @NotNull(message = "Usuario es requerido")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    
    @Column(name = "is_default", nullable = false)
    private Boolean isDefault = false;
    
    @Size(max = 50, message = "Icono no puede exceder 50 caracteres")
    @Column(length = 50)
    private String icon;
    
    @Size(max = 20, message = "Color no puede exceder 20 caracteres")
    @Column(length = 20)
    private String color;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relación con transacciones
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Transaction> transactions;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Métodos de utilidad
    public boolean isIncome() {
        return CategoryType.INCOME.equals(this.type);
    }
    
    public boolean isExpense() {
        return CategoryType.EXPENSE.equals(this.type);
    }
    
    public String getDisplayName() {
        return name + (isDefault ? " (Por defecto)" : "");
    }
    
    // Enum para el tipo de categoría
    public enum CategoryType {
        INCOME("Ingreso"),
        EXPENSE("Gasto");
        
        private final String displayName;
        
        CategoryType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}

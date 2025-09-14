package com.alonsocode.trakyflow.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Monto es requerido")
    @DecimalMin(value = "0.01", message = "Monto debe ser mayor a 0")
    @Digits(integer = 13, fraction = 2, message = "Monto debe tener máximo 13 dígitos enteros y 2 decimales")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    @NotBlank(message = "Descripción es requerida")
    @Size(max = 255, message = "Descripción no puede exceder 255 caracteres")
    @Column(nullable = false, length = 255)
    private String description;
    
    @NotNull(message = "Tipo de transacción es requerido")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TransactionType type;
    
    @NotNull(message = "Fecha de transacción es requerida")
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;
    
    @NotNull(message = "Usuario es requerido")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    
    @NotNull(message = "Categoría es requerida")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
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
        return TransactionType.INCOME.equals(this.type);
    }
    
    public boolean isExpense() {
        return TransactionType.EXPENSE.equals(this.type);
    }
    
    public BigDecimal getSignedAmount() {
        return isIncome() ? amount : amount.negate();
    }
    
    public String getFormattedAmount() {
        return String.format("%.2f", amount);
    }
    
    public String getDisplayDescription() {
        return description + " (" + category.getName() + ")";
    }
    
    // Enum para el tipo de transacción
    public enum TransactionType {
        INCOME("Ingreso"),
        EXPENSE("Gasto");
        
        private final String displayName;
        
        TransactionType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
}

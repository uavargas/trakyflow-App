package com.alonsocode.trakyflow.repositories;

import com.alonsocode.trakyflow.models.Category;
import com.alonsocode.trakyflow.models.Transaction;
import com.alonsocode.trakyflow.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Buscar transacciones por usuario
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
    
    // Buscar transacciones por ID de usuario
    List<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId);
    
    // Buscar transacciones por usuario con paginación
    Page<Transaction> findByUserOrderByTransactionDateDesc(User user, Pageable pageable);
    
    // Buscar transacciones por ID de usuario con paginación
    Page<Transaction> findByUserIdOrderByTransactionDateDesc(Long userId, Pageable pageable);
    
    // Buscar transacciones por tipo y usuario
    List<Transaction> findByUserAndTypeOrderByTransactionDateDesc(User user, Transaction.TransactionType type);
    
    // Buscar transacciones por tipo e ID de usuario
    List<Transaction> findByUserIdAndTypeOrderByTransactionDateDesc(Long userId, Transaction.TransactionType type);
    
    // Buscar transacciones por categoría
    List<Transaction> findByCategoryOrderByTransactionDateDesc(Category category);
    
    // Buscar transacciones por categoría e ID de usuario
    List<Transaction> findByCategoryIdAndUserIdOrderByTransactionDateDesc(Long categoryId, Long userId);
    
    // Buscar transacciones por rango de fechas y usuario
    List<Transaction> findByUserAndTransactionDateBetweenOrderByTransactionDateDesc(
            User user, LocalDate startDate, LocalDate endDate);
    
    // Buscar transacciones por rango de fechas e ID de usuario
    List<Transaction> findByUserIdAndTransactionDateBetweenOrderByTransactionDateDesc(
            Long userId, LocalDate startDate, LocalDate endDate);
    
    // Buscar transacciones por rango de fechas, tipo y usuario
    List<Transaction> findByUserAndTypeAndTransactionDateBetweenOrderByTransactionDateDesc(
            User user, Transaction.TransactionType type, LocalDate startDate, LocalDate endDate);
    
    // Buscar transacciones por rango de fechas, tipo e ID de usuario
    List<Transaction> findByUserIdAndTypeAndTransactionDateBetweenOrderByTransactionDateDesc(
            Long userId, Transaction.TransactionType type, LocalDate startDate, LocalDate endDate);
    
    // Buscar transacciones por mes y año
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND YEAR(t.transactionDate) = :year AND MONTH(t.transactionDate) = :month ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserAndYearAndMonth(@Param("user") User user, @Param("year") int year, @Param("month") int month);
    
    // Buscar transacciones por año
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND YEAR(t.transactionDate) = :year ORDER BY t.transactionDate DESC")
    List<Transaction> findByUserAndYear(@Param("user") User user, @Param("year") int year);
    
    // Buscar transacciones por ID y usuario (para validar pertenencia)
    Optional<Transaction> findByIdAndUser(Long id, User user);
    
    // Buscar transacciones por ID e ID de usuario
    Optional<Transaction> findByIdAndUserId(Long id, Long userId);
    
    // Calcular total por tipo y usuario
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = :type")
    BigDecimal sumAmountByUserAndType(@Param("user") User user, @Param("type") Transaction.TransactionType type);
    
    // Calcular total por tipo e ID de usuario
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.userId = :userId AND t.type = :type")
    BigDecimal sumAmountByUserIdAndType(@Param("userId") Long userId, @Param("type") Transaction.TransactionType type);
    
    // Calcular total por rango de fechas, tipo y usuario
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.user = :user AND t.type = :type AND t.transactionDate BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByUserAndTypeAndDateRange(@Param("user") User user, @Param("type") Transaction.TransactionType type, 
                                                  @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    // Calcular total por categoría y usuario
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.category = :category")
    BigDecimal sumAmountByCategory(@Param("category") Category category);
    
    // Calcular total por categoría e ID de usuario
    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE t.categoryId = :categoryId AND t.userId = :userId")
    BigDecimal sumAmountByCategoryIdAndUserId(@Param("categoryId") Long categoryId, @Param("userId") Long userId);
    
    // Buscar transacciones recientes (últimos N días)
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.transactionDate >= :startDate ORDER BY t.transactionDate DESC")
    List<Transaction> findRecentTransactionsByUser(@Param("user") User user, @Param("startDate") LocalDate startDate);
    
    // Buscar transacciones recientes por ID de usuario
    @Query("SELECT t FROM Transaction t WHERE t.userId = :userId AND t.transactionDate >= :startDate ORDER BY t.transactionDate DESC")
    List<Transaction> findRecentTransactionsByUserId(@Param("userId") Long userId, @Param("startDate") LocalDate startDate);
    
    // Contar transacciones por usuario
    long countByUser(User user);
    
    // Contar transacciones por usuario y tipo
    long countByUserAndType(User user, Transaction.TransactionType type);
    
    // Verificar si existe transacción con ID específico para usuario
    boolean existsByIdAndUser(Long id, User user);
    
    // Verificar si existe transacción con ID específico para ID de usuario
    boolean existsByIdAndUserId(Long id, Long userId);
}

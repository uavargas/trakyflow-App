package com.alonsocode.trakyflow.repositories;

import com.alonsocode.trakyflow.models.Category;
import com.alonsocode.trakyflow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Buscar categorías por usuario
    List<Category> findByUserOrderByNameAsc(User user);
    
    // Buscar categorías por ID de usuario
    List<Category> findByUserIdOrderByNameAsc(Long userId);
    
    // Buscar categorías por tipo y usuario
    List<Category> findByUserAndTypeOrderByNameAsc(User user, Category.CategoryType type);
    
    // Buscar categorías por tipo e ID de usuario
    List<Category> findByUserIdAndTypeOrderByNameAsc(Long userId, Category.CategoryType type);
    
    // Buscar categorías por defecto
    List<Category> findByIsDefaultTrueOrderByNameAsc();
    
    // Buscar categorías por defecto y tipo
    List<Category> findByIsDefaultTrueAndTypeOrderByNameAsc(Category.CategoryType type);
    
    // Buscar categoría por ID y usuario (para validar pertenencia)
    Optional<Category> findByIdAndUser(Long id, User user);
    
    // Buscar categoría por ID e ID de usuario
    Optional<Category> findByIdAndUserId(Long id, Long userId);
    
    // Buscar categoría por nombre y usuario (para evitar duplicados)
    Optional<Category> findByNameAndUser(String name, User user);
    
    // Buscar categoría por nombre e ID de usuario
    Optional<Category> findByNameAndUserId(String name, Long userId);
    
    // Contar categorías por usuario
    long countByUser(User user);
    
    // Contar categorías por usuario y tipo
    long countByUserAndType(User user, Category.CategoryType type);
    
    // Buscar categorías que no tienen transacciones
    @Query("SELECT c FROM Category c WHERE c.user = :user AND c.transactions IS EMPTY")
    List<Category> findUnusedCategoriesByUser(@Param("user") User user);
    
    // Buscar categorías más usadas por usuario
    @Query("SELECT c FROM Category c LEFT JOIN c.transactions t WHERE c.user = :user GROUP BY c.id ORDER BY COUNT(t.id) DESC")
    List<Category> findMostUsedCategoriesByUser(@Param("user") User user);
    
    // Verificar si existe categoría con nombre específico para usuario
    boolean existsByNameAndUser(String name, User user);
    
    // Verificar si existe categoría con nombre específico para ID de usuario
    boolean existsByNameAndUserId(String name, Long userId);
}

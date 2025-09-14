package com.alonsocode.trakyflow.repositories;

import com.alonsocode.trakyflow.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Buscar usuario por email
    Optional<User> findByEmail(String email);
    
    // Verificar si existe usuario por email
    boolean existsByEmail(String email);
    
    // Buscar usuarios por nombre
    List<User> findByFirstNameContainingIgnoreCase(String firstName);
    
    // Buscar usuarios por apellido
    List<User> findByLastNameContainingIgnoreCase(String lastName);
    
    // Buscar usuarios por nombre completo
    @Query("SELECT u FROM User u WHERE CONCAT(u.firstName, ' ', u.lastName) LIKE %:fullName%")
    List<User> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName);
    
    // Buscar usuarios habilitados
    List<User> findByEnabledTrue();
    
    // Buscar usuarios deshabilitados
    List<User> findByEnabledFalse();
    
    // Buscar usuarios con email verificado
    List<User> findByEmailVerifiedTrue();
    
    // Buscar usuarios con email no verificado
    List<User> findByEmailVerifiedFalse();
    
    // Buscar usuarios por rango de fechas de creación
    List<User> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Buscar usuarios activos recientemente (último login)
    @Query("SELECT u FROM User u WHERE u.lastLogin >= :since ORDER BY u.lastLogin DESC")
    List<User> findActiveUsersSince(@Param("since") LocalDateTime since);
    
    // Buscar usuarios inactivos (sin login reciente)
    @Query("SELECT u FROM User u WHERE u.lastLogin < :since OR u.lastLogin IS NULL ORDER BY u.createdAt DESC")
    List<User> findInactiveUsersSince(@Param("since") LocalDateTime since);
    
    // Contar usuarios habilitados
    long countByEnabledTrue();
    
    // Contar usuarios deshabilitados
    long countByEnabledFalse();
    
    // Contar usuarios con email verificado
    long countByEmailVerifiedTrue();
    
    // Contar usuarios con email no verificado
    long countByEmailVerifiedFalse();
    
    // Buscar usuarios por patrón de email
    List<User> findByEmailContainingIgnoreCase(String emailPattern);
    
    // Verificar si existe usuario por ID y email (para validaciones)
    boolean existsByIdAndEmail(Long id, String email);
    
    // Buscar usuarios ordenados por fecha de creación
    List<User> findAllByOrderByCreatedAtDesc();
    
    // Buscar usuarios ordenados por último login
    @Query("SELECT u FROM User u ORDER BY u.lastLogin DESC NULLS LAST")
    List<User> findAllByOrderByLastLoginDesc();
}

package com.vincentrungogh.domain.drawing.repository;

import com.vincentrungogh.domain.drawing.entity.DrawingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawingDetailRepository extends JpaRepository<DrawingDetail, Integer> {
    
}

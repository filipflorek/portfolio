package com.florek.NBA_backend.repository;

import com.florek.NBA_backend.model.entries.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}

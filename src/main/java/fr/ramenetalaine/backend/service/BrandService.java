package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.dto.BrandDto;
import java.util.List;

public interface BrandService {
    List<BrandDto> getAllBrands();
}

package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.dto.BrandDto;
import fr.ramenetalaine.backend.model.Brand;
import fr.ramenetalaine.backend.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(brand -> new BrandDto(brand.getId(), brand.getName()))
                .collect(Collectors.toList());
    }
}

package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.dto.BrandDto;
import fr.ramenetalaine.backend.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public List<BrandDto> getAllBrands() {
        return brandService.getAllBrands();
    }
}

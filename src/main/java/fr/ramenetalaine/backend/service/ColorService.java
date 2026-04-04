package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.model.Color;
import fr.ramenetalaine.backend.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {
    private final ColorRepository colorRepository;

    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }
}

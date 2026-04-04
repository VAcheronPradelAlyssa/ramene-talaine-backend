package fr.ramenetalaine.backend.service;

import fr.ramenetalaine.backend.model.Composition;
import fr.ramenetalaine.backend.repository.CompositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompositionService {
    private final CompositionRepository compositionRepository;

    public List<Composition> getAllCompositions() {
        return compositionRepository.findAll();
    }
}

package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.model.Composition;
import fr.ramenetalaine.backend.service.CompositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/compositions")
@RequiredArgsConstructor
public class CompositionController {
    private final CompositionService compositionService;

    @GetMapping
    public ResponseEntity<List<Composition>> getAllCompositions() {
        return ResponseEntity.ok(compositionService.getAllCompositions());
    }
}

package fr.ramenetalaine.backend.controller;

import fr.ramenetalaine.backend.model.Color;
import fr.ramenetalaine.backend.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
@RequiredArgsConstructor
public class ColorController {
    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> getAllColors() {
        return ResponseEntity.ok(colorService.getAllColors());
    }
}

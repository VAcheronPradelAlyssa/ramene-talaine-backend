
            package fr.ramenetalaine.backend.service;

            import fr.ramenetalaine.backend.dto.CompositionRequestDto;
            import fr.ramenetalaine.backend.dto.ListingColorRequestDto;
import fr.ramenetalaine.backend.model.*;
import fr.ramenetalaine.backend.repository.*;
import fr.ramenetalaine.backend.exception.ListingNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListingService {
    private final ListingRepository listingRepository;
    private final BrandRepository brandRepository;
    private final CompositionRepository compositionRepository;
    private final ColorRepository colorRepository;

    public Listing createListing(Listing listing) {
        // Validation simple : au moins brand ou customBrand
        if (listing.getBrand() == null && (listing.getCustomBrand() == null || listing.getCustomBrand().isBlank())) {
            throw new IllegalArgumentException("brandId ou customBrand doit être renseigné");
        }
        applyPriceRules(listing);
        return listingRepository.save(listing);
    }

    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

    public Listing getListingById(Long id) {
        return listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException(id));
    }

    public void deleteListing(Long id) {
        if (!listingRepository.existsById(id)) {
            throw new ListingNotFoundException(id);
        }
        listingRepository.deleteById(id);
    }

    private void applyPriceRules(Listing listing) {
        if (listing.getType() != ListingType.SALE) {
            listing.setPrice(null);
        }
    }

    public Listing updateListingByIdForUser(Long id, fr.ramenetalaine.backend.dto.ListingUpdateRequestDto dto, User user) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException(id));
        if (listing.getSeller() == null || !listing.getSeller().getId().equals(user.getId())) {
            throw new SecurityException("Vous n'êtes pas autorisé à modifier cette annonce.");
        }

        if (dto.getTitle() != null) listing.setTitle(dto.getTitle());
        if (dto.getDescription() != null) listing.setDescription(dto.getDescription());
        if (dto.getCity() != null) listing.setCity(dto.getCity());
        if (dto.getPostalCode() != null) listing.setPostalCode(dto.getPostalCode());
        if (dto.getWeight() != null) listing.setWeight(dto.getWeight());
        if (dto.getLength() != null) listing.setLength(dto.getLength());

        if (dto.getBrandId() != null) {
            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new IllegalArgumentException("Marque non trouvée"));
            listing.setBrand(brand);
            listing.setCustomBrand(null);
        } else if (dto.getCustomBrand() != null) {
            String customBrand = dto.getCustomBrand().trim();
            if (customBrand.isBlank()) {
                listing.setCustomBrand(null);
            } else {
                Brand brand = brandRepository.findByName(customBrand)
                        .orElseGet(() -> brandRepository.save(new Brand(customBrand)));
                listing.setBrand(brand);
                listing.setCustomBrand(customBrand);
            }
        }

        if (dto.getType() != null) listing.setType(dto.getType());
        if (dto.getPrice() != null || listing.getType() != ListingType.SALE) listing.setPrice(dto.getPrice());
        if (dto.getComposition() != null) listing.setComposition(dto.getComposition());
        if (dto.getImageUrls() != null) listing.setImageUrls(dto.getImageUrls());

        if (dto.getColors() != null) {
            listing.getListingColors().clear();
            for (ListingColorRequestDto colorDto : dto.getColors()) {
                ListingColor lc = new ListingColor();
                lc.setListing(listing);

                if (colorDto.getColorId() != null) {
                    Color color = colorRepository.findById(colorDto.getColorId())
                            .orElseThrow(() -> new IllegalArgumentException("Color non trouvée pour id : " + colorDto.getColorId()));
                    lc.setColor(color);
                }

                if (colorDto.getCustomColor() != null && !colorDto.getCustomColor().isBlank()) {
                    lc.setCustomColor(colorDto.getCustomColor().trim());
                }

                if (lc.getColor() == null && (lc.getCustomColor() == null || lc.getCustomColor().isBlank())) {
                    throw new IllegalArgumentException("Chaque couleur doit avoir un colorId ou un customColor non vide");
                }

                listing.getListingColors().add(lc);
            }
        }

        if (dto.getCompositions() != null) {
            listing.getListingCompositions().clear();
            for (CompositionRequestDto compoDto : dto.getCompositions()) {
                if (compoDto.getCompositionId() == null) {
                    throw new IllegalArgumentException("compositionId manquant dans CompositionRequestDto");
                }

                Composition composition = compositionRepository.findById(compoDto.getCompositionId())
                        .orElseThrow(() -> new IllegalArgumentException("Composition non trouvée pour id : " + compoDto.getCompositionId()));

                ListingComposition lc = new ListingComposition();
                lc.setListing(listing);
                lc.setComposition(composition);
                lc.setPercentage(compoDto.getPercentage());
                listing.getListingCompositions().add(lc);
            }
        }

        applyPriceRules(listing);
        return listingRepository.save(listing);
    }

    public void deleteListingByIdForUser(Long id, User user) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ListingNotFoundException(id));
        if (listing.getSeller() == null || !listing.getSeller().getId().equals(user.getId())) {
            throw new SecurityException("Vous n'êtes pas autorisé à supprimer cette annonce.");
        }
        listingRepository.deleteById(id);
    }

    public List<Listing> getListingsForUser(User user) {
        return listingRepository.findBySeller(user);
    }
}

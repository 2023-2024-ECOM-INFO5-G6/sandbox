package polytech.g6.blog.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import polytech.g6.blog.domain.Chambre;
import polytech.g6.blog.repository.ChambreRepository;
import polytech.g6.blog.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link polytech.g6.blog.domain.Chambre}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ChambreResource {

    private final Logger log = LoggerFactory.getLogger(ChambreResource.class);

    private static final String ENTITY_NAME = "chambre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChambreRepository chambreRepository;

    public ChambreResource(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    /**
     * {@code POST  /chambres} : Create a new chambre.
     *
     * @param chambre the chambre to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chambre, or with status {@code 400 (Bad Request)} if the chambre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/chambres")
    public ResponseEntity<Chambre> createChambre(@Valid @RequestBody Chambre chambre) throws URISyntaxException {
        log.debug("REST request to save Chambre : {}", chambre);
        if (chambre.getId() != null) {
            throw new BadRequestAlertException("A new chambre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chambre result = chambreRepository.save(chambre);
        return ResponseEntity
            .created(new URI("/api/chambres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /chambres/:id} : Updates an existing chambre.
     *
     * @param id the id of the chambre to save.
     * @param chambre the chambre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chambre,
     * or with status {@code 400 (Bad Request)} if the chambre is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chambre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/chambres/{id}")
    public ResponseEntity<Chambre> updateChambre(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Chambre chambre
    ) throws URISyntaxException {
        log.debug("REST request to update Chambre : {}, {}", id, chambre);
        if (chambre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chambre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chambreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Chambre result = chambreRepository.save(chambre);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chambre.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /chambres/:id} : Partial updates given fields of an existing chambre, field will ignore if it is null
     *
     * @param id the id of the chambre to save.
     * @param chambre the chambre to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chambre,
     * or with status {@code 400 (Bad Request)} if the chambre is not valid,
     * or with status {@code 404 (Not Found)} if the chambre is not found,
     * or with status {@code 500 (Internal Server Error)} if the chambre couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/chambres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Chambre> partialUpdateChambre(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Chambre chambre
    ) throws URISyntaxException {
        log.debug("REST request to partial update Chambre partially : {}, {}", id, chambre);
        if (chambre.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, chambre.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!chambreRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Chambre> result = chambreRepository
            .findById(chambre.getId())
            .map(existingChambre -> {
                if (chambre.getNumC() != null) {
                    existingChambre.setNumC(chambre.getNumC());
                }

                return existingChambre;
            })
            .map(chambreRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chambre.getId().toString())
        );
    }

    /**
     * {@code GET  /chambres} : get all the chambres.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of chambres in body.
     */
    @GetMapping("/chambres")
    public List<Chambre> getAllChambres() {
        log.debug("REST request to get all Chambres");
        return chambreRepository.findAll();
    }

    /**
     * {@code GET  /chambres/:id} : get the "id" chambre.
     *
     * @param id the id of the chambre to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chambre, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/chambres/{id}")
    public ResponseEntity<Chambre> getChambre(@PathVariable Long id) {
        log.debug("REST request to get Chambre : {}", id);
        Optional<Chambre> chambre = chambreRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(chambre);
    }

    /**
     * {@code DELETE  /chambres/:id} : delete the "id" chambre.
     *
     * @param id the id of the chambre to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/chambres/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        log.debug("REST request to delete Chambre : {}", id);
        chambreRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

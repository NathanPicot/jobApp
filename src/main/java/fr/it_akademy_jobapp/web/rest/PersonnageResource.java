package fr.it_akademy_jobapp.web.rest;

import fr.it_akademy_jobapp.repository.PersonnageRepository;
import fr.it_akademy_jobapp.service.PersonnageService;
import fr.it_akademy_jobapp.service.dto.PersonnageDTO;
import fr.it_akademy_jobapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy_jobapp.domain.Personnage}.
 */
@RestController
@RequestMapping("/api/personnages")
public class PersonnageResource {

    private final Logger log = LoggerFactory.getLogger(PersonnageResource.class);

    private static final String ENTITY_NAME = "personnage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonnageService personnageService;

    private final PersonnageRepository personnageRepository;

    public PersonnageResource(PersonnageService personnageService, PersonnageRepository personnageRepository) {
        this.personnageService = personnageService;
        this.personnageRepository = personnageRepository;
    }

    /**
     * {@code POST  /personnages} : Create a new personnage.
     *
     * @param personnageDTO the personnageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personnageDTO, or with status {@code 400 (Bad Request)} if the personnage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PersonnageDTO> createPersonnage(@RequestBody PersonnageDTO personnageDTO) throws URISyntaxException {
        log.debug("REST request to save Personnage : {}", personnageDTO);
        if (personnageDTO.getId() != null) {
            throw new BadRequestAlertException("A new personnage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonnageDTO result = personnageService.save(personnageDTO);
        return ResponseEntity
            .created(new URI("/api/personnages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personnages/:id} : Updates an existing personnage.
     *
     * @param id the id of the personnageDTO to save.
     * @param personnageDTO the personnageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personnageDTO,
     * or with status {@code 400 (Bad Request)} if the personnageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personnageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonnageDTO> updatePersonnage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonnageDTO personnageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Personnage : {}, {}", id, personnageDTO);
        if (personnageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personnageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personnageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonnageDTO result = personnageService.update(personnageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personnageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personnages/:id} : Partial updates given fields of an existing personnage, field will ignore if it is null
     *
     * @param id the id of the personnageDTO to save.
     * @param personnageDTO the personnageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personnageDTO,
     * or with status {@code 400 (Bad Request)} if the personnageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personnageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personnageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonnageDTO> partialUpdatePersonnage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PersonnageDTO personnageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Personnage partially : {}, {}", id, personnageDTO);
        if (personnageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personnageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personnageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonnageDTO> result = personnageService.partialUpdate(personnageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personnageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /personnages} : get all the personnages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personnages in body.
     */
    @GetMapping("")
    public List<PersonnageDTO> getAllPersonnages() {
        log.debug("REST request to get all Personnages");
        return personnageService.findAll();
    }

    /**
     * {@code GET  /personnages/:id} : get the "id" personnage.
     *
     * @param id the id of the personnageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personnageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonnageDTO> getPersonnage(@PathVariable Long id) {
        log.debug("REST request to get Personnage : {}", id);
        Optional<PersonnageDTO> personnageDTO = personnageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personnageDTO);
    }

    /**
     * {@code DELETE  /personnages/:id} : delete the "id" personnage.
     *
     * @param id the id of the personnageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonnage(@PathVariable Long id) {
        log.debug("REST request to delete Personnage : {}", id);
        personnageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

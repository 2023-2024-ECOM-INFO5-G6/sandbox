package polytech.g6.blog.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import polytech.g6.blog.IntegrationTest;
import polytech.g6.blog.domain.Etablissement;
import polytech.g6.blog.domain.Patient;
import polytech.g6.blog.domain.UserRole;
import polytech.g6.blog.domain.Utilisateur;
import polytech.g6.blog.repository.UtilisateurRepository;

/**
 * Integration tests for the {@link UtilisateurResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UtilisateurResourceIT {

    private static final Long DEFAULT_ID_U = 1L;
    private static final Long UPDATED_ID_U = 2L;

    private static final String DEFAULT_EMAIL_U = "ei+vrL@Q.8mdd9.1oS.jxAr.P.s2r.tP4YRNlyhe671Cpiv";
    private static final String UPDATED_EMAIL_U = "A@0KZF.ADgX.AZv.5K";

    private static final String DEFAULT_PASSWORD_U = "jnyF4MN(a4";
    private static final String UPDATED_PASSWORD_U = "r:w}.wo=tRa";

    private static final String DEFAULT_NOM_U = "AAAAAAAAAA";
    private static final String UPDATED_NOM_U = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_U = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_U = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISSANCE_U = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE_U = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/utilisateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Mock
    private UtilisateurRepository utilisateurRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUtilisateurMockMvc;

    private Utilisateur utilisateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Utilisateur createEntity(EntityManager em) {
        Utilisateur utilisateur = new Utilisateur()
            .idU(DEFAULT_ID_U)
            .emailU(DEFAULT_EMAIL_U)
            .passwordU(DEFAULT_PASSWORD_U)
            .nomU(DEFAULT_NOM_U)
            .prenomU(DEFAULT_PRENOM_U)
            .dateNaissanceU(DEFAULT_DATE_NAISSANCE_U);
        // Add required entity
        UserRole userRole;
        if (TestUtil.findAll(em, UserRole.class).isEmpty()) {
            userRole = UserRoleResourceIT.createEntity(em);
            em.persist(userRole);
            em.flush();
        } else {
            userRole = TestUtil.findAll(em, UserRole.class).get(0);
        }
        utilisateur.getUserRoles().add(userRole);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        utilisateur.getPatients().add(patient);
        // Add required entity
        Etablissement etablissement;
        if (TestUtil.findAll(em, Etablissement.class).isEmpty()) {
            etablissement = EtablissementResourceIT.createEntity(em);
            em.persist(etablissement);
            em.flush();
        } else {
            etablissement = TestUtil.findAll(em, Etablissement.class).get(0);
        }
        utilisateur.getEtablissements().add(etablissement);
        return utilisateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Utilisateur createUpdatedEntity(EntityManager em) {
        Utilisateur utilisateur = new Utilisateur()
            .idU(UPDATED_ID_U)
            .emailU(UPDATED_EMAIL_U)
            .passwordU(UPDATED_PASSWORD_U)
            .nomU(UPDATED_NOM_U)
            .prenomU(UPDATED_PRENOM_U)
            .dateNaissanceU(UPDATED_DATE_NAISSANCE_U);
        // Add required entity
        UserRole userRole;
        if (TestUtil.findAll(em, UserRole.class).isEmpty()) {
            userRole = UserRoleResourceIT.createUpdatedEntity(em);
            em.persist(userRole);
            em.flush();
        } else {
            userRole = TestUtil.findAll(em, UserRole.class).get(0);
        }
        utilisateur.getUserRoles().add(userRole);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        utilisateur.getPatients().add(patient);
        // Add required entity
        Etablissement etablissement;
        if (TestUtil.findAll(em, Etablissement.class).isEmpty()) {
            etablissement = EtablissementResourceIT.createUpdatedEntity(em);
            em.persist(etablissement);
            em.flush();
        } else {
            etablissement = TestUtil.findAll(em, Etablissement.class).get(0);
        }
        utilisateur.getEtablissements().add(etablissement);
        return utilisateur;
    }

    @BeforeEach
    public void initTest() {
        utilisateur = createEntity(em);
    }

    @Test
    @Transactional
    void createUtilisateur() throws Exception {
        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();
        // Create the Utilisateur
        restUtilisateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isCreated());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate + 1);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getIdU()).isEqualTo(DEFAULT_ID_U);
        assertThat(testUtilisateur.getEmailU()).isEqualTo(DEFAULT_EMAIL_U);
        assertThat(testUtilisateur.getPasswordU()).isEqualTo(DEFAULT_PASSWORD_U);
        assertThat(testUtilisateur.getNomU()).isEqualTo(DEFAULT_NOM_U);
        assertThat(testUtilisateur.getPrenomU()).isEqualTo(DEFAULT_PRENOM_U);
        assertThat(testUtilisateur.getDateNaissanceU()).isEqualTo(DEFAULT_DATE_NAISSANCE_U);
    }

    @Test
    @Transactional
    void createUtilisateurWithExistingId() throws Exception {
        // Create the Utilisateur with an existing ID
        utilisateur.setId(1L);

        int databaseSizeBeforeCreate = utilisateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUtilisateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdUIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setIdU(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailUIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setEmailU(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordUIsRequired() throws Exception {
        int databaseSizeBeforeTest = utilisateurRepository.findAll().size();
        // set the field null
        utilisateur.setPasswordU(null);

        // Create the Utilisateur, which fails.

        restUtilisateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isBadRequest());

        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUtilisateurs() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get all the utilisateurList
        restUtilisateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(utilisateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].idU").value(hasItem(DEFAULT_ID_U.intValue())))
            .andExpect(jsonPath("$.[*].emailU").value(hasItem(DEFAULT_EMAIL_U)))
            .andExpect(jsonPath("$.[*].passwordU").value(hasItem(DEFAULT_PASSWORD_U)))
            .andExpect(jsonPath("$.[*].nomU").value(hasItem(DEFAULT_NOM_U)))
            .andExpect(jsonPath("$.[*].prenomU").value(hasItem(DEFAULT_PRENOM_U)))
            .andExpect(jsonPath("$.[*].dateNaissanceU").value(hasItem(DEFAULT_DATE_NAISSANCE_U.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUtilisateursWithEagerRelationshipsIsEnabled() throws Exception {
        when(utilisateurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUtilisateurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(utilisateurRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllUtilisateursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(utilisateurRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUtilisateurMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(utilisateurRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        // Get the utilisateur
        restUtilisateurMockMvc
            .perform(get(ENTITY_API_URL_ID, utilisateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(utilisateur.getId().intValue()))
            .andExpect(jsonPath("$.idU").value(DEFAULT_ID_U.intValue()))
            .andExpect(jsonPath("$.emailU").value(DEFAULT_EMAIL_U))
            .andExpect(jsonPath("$.passwordU").value(DEFAULT_PASSWORD_U))
            .andExpect(jsonPath("$.nomU").value(DEFAULT_NOM_U))
            .andExpect(jsonPath("$.prenomU").value(DEFAULT_PRENOM_U))
            .andExpect(jsonPath("$.dateNaissanceU").value(DEFAULT_DATE_NAISSANCE_U.toString()));
    }

    @Test
    @Transactional
    void getNonExistingUtilisateur() throws Exception {
        // Get the utilisateur
        restUtilisateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Update the utilisateur
        Utilisateur updatedUtilisateur = utilisateurRepository.findById(utilisateur.getId()).get();
        // Disconnect from session so that the updates on updatedUtilisateur are not directly saved in db
        em.detach(updatedUtilisateur);
        updatedUtilisateur
            .idU(UPDATED_ID_U)
            .emailU(UPDATED_EMAIL_U)
            .passwordU(UPDATED_PASSWORD_U)
            .nomU(UPDATED_NOM_U)
            .prenomU(UPDATED_PRENOM_U)
            .dateNaissanceU(UPDATED_DATE_NAISSANCE_U);

        restUtilisateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUtilisateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUtilisateur))
            )
            .andExpect(status().isOk());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getIdU()).isEqualTo(UPDATED_ID_U);
        assertThat(testUtilisateur.getEmailU()).isEqualTo(UPDATED_EMAIL_U);
        assertThat(testUtilisateur.getPasswordU()).isEqualTo(UPDATED_PASSWORD_U);
        assertThat(testUtilisateur.getNomU()).isEqualTo(UPDATED_NOM_U);
        assertThat(testUtilisateur.getPrenomU()).isEqualTo(UPDATED_PRENOM_U);
        assertThat(testUtilisateur.getDateNaissanceU()).isEqualTo(UPDATED_DATE_NAISSANCE_U);
    }

    @Test
    @Transactional
    void putNonExistingUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();
        utilisateur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, utilisateur.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(utilisateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();
        utilisateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(utilisateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();
        utilisateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(utilisateur)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUtilisateurWithPatch() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Update the utilisateur using partial update
        Utilisateur partialUpdatedUtilisateur = new Utilisateur();
        partialUpdatedUtilisateur.setId(utilisateur.getId());

        partialUpdatedUtilisateur.idU(UPDATED_ID_U).passwordU(UPDATED_PASSWORD_U).prenomU(UPDATED_PRENOM_U);

        restUtilisateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUtilisateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUtilisateur))
            )
            .andExpect(status().isOk());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getIdU()).isEqualTo(UPDATED_ID_U);
        assertThat(testUtilisateur.getEmailU()).isEqualTo(DEFAULT_EMAIL_U);
        assertThat(testUtilisateur.getPasswordU()).isEqualTo(UPDATED_PASSWORD_U);
        assertThat(testUtilisateur.getNomU()).isEqualTo(DEFAULT_NOM_U);
        assertThat(testUtilisateur.getPrenomU()).isEqualTo(UPDATED_PRENOM_U);
        assertThat(testUtilisateur.getDateNaissanceU()).isEqualTo(DEFAULT_DATE_NAISSANCE_U);
    }

    @Test
    @Transactional
    void fullUpdateUtilisateurWithPatch() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();

        // Update the utilisateur using partial update
        Utilisateur partialUpdatedUtilisateur = new Utilisateur();
        partialUpdatedUtilisateur.setId(utilisateur.getId());

        partialUpdatedUtilisateur
            .idU(UPDATED_ID_U)
            .emailU(UPDATED_EMAIL_U)
            .passwordU(UPDATED_PASSWORD_U)
            .nomU(UPDATED_NOM_U)
            .prenomU(UPDATED_PRENOM_U)
            .dateNaissanceU(UPDATED_DATE_NAISSANCE_U);

        restUtilisateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUtilisateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUtilisateur))
            )
            .andExpect(status().isOk());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
        Utilisateur testUtilisateur = utilisateurList.get(utilisateurList.size() - 1);
        assertThat(testUtilisateur.getIdU()).isEqualTo(UPDATED_ID_U);
        assertThat(testUtilisateur.getEmailU()).isEqualTo(UPDATED_EMAIL_U);
        assertThat(testUtilisateur.getPasswordU()).isEqualTo(UPDATED_PASSWORD_U);
        assertThat(testUtilisateur.getNomU()).isEqualTo(UPDATED_NOM_U);
        assertThat(testUtilisateur.getPrenomU()).isEqualTo(UPDATED_PRENOM_U);
        assertThat(testUtilisateur.getDateNaissanceU()).isEqualTo(UPDATED_DATE_NAISSANCE_U);
    }

    @Test
    @Transactional
    void patchNonExistingUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();
        utilisateur.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, utilisateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(utilisateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();
        utilisateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(utilisateur))
            )
            .andExpect(status().isBadRequest());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUtilisateur() throws Exception {
        int databaseSizeBeforeUpdate = utilisateurRepository.findAll().size();
        utilisateur.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUtilisateurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(utilisateur))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Utilisateur in the database
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUtilisateur() throws Exception {
        // Initialize the database
        utilisateurRepository.saveAndFlush(utilisateur);

        int databaseSizeBeforeDelete = utilisateurRepository.findAll().size();

        // Delete the utilisateur
        restUtilisateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, utilisateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Utilisateur> utilisateurList = utilisateurRepository.findAll();
        assertThat(utilisateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

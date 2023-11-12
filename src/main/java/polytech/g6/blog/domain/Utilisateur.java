package polytech.g6.blog.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Utilisateur.
 */
@Entity
@Table(name = "utilisateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "id_u", nullable = false)
    private Long idU;

    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9_.+-])+\\@(([a-zA-Z0-9-])+\\.)+([a-zA-Z0-9]{2,4})+$")
    @Column(name = "email_u", nullable = false)
    private String emailU;

    @NotNull
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    @Column(name = "password_u", nullable = false)
    private String passwordU;

    @Size(min = 1)
    @Column(name = "nom_u")
    private String nomU;

    @Size(min = 1)
    @Column(name = "prenom_u")
    private String prenomU;

    @Column(name = "date_naissance_u")
    private LocalDate dateNaissanceU;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_utilisateur__user_roles",
        joinColumns = @JoinColumn(name = "utilisateur_id"),
        inverseJoinColumns = @JoinColumn(name = "user_roles_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "utilisateurs" }, allowSetters = true)
    private Set<UserRole> userRoles = new HashSet<>();

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_utilisateur__patients",
        joinColumns = @JoinColumn(name = "utilisateur_id"),
        inverseJoinColumns = @JoinColumn(name = "patients_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chambres", "utilisateurs" }, allowSetters = true)
    private Set<Patient> patients = new HashSet<>();

    @ManyToMany(mappedBy = "utilisateurs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "chambres", "utilisateurs" }, allowSetters = true)
    private Set<Etablissement> etablissements = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Utilisateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdU() {
        return this.idU;
    }

    public Utilisateur idU(Long idU) {
        this.setIdU(idU);
        return this;
    }

    public void setIdU(Long idU) {
        this.idU = idU;
    }

    public String getEmailU() {
        return this.emailU;
    }

    public Utilisateur emailU(String emailU) {
        this.setEmailU(emailU);
        return this;
    }

    public void setEmailU(String emailU) {
        this.emailU = emailU;
    }

    public String getPasswordU() {
        return this.passwordU;
    }

    public Utilisateur passwordU(String passwordU) {
        this.setPasswordU(passwordU);
        return this;
    }

    public void setPasswordU(String passwordU) {
        this.passwordU = passwordU;
    }

    public String getNomU() {
        return this.nomU;
    }

    public Utilisateur nomU(String nomU) {
        this.setNomU(nomU);
        return this;
    }

    public void setNomU(String nomU) {
        this.nomU = nomU;
    }

    public String getPrenomU() {
        return this.prenomU;
    }

    public Utilisateur prenomU(String prenomU) {
        this.setPrenomU(prenomU);
        return this;
    }

    public void setPrenomU(String prenomU) {
        this.prenomU = prenomU;
    }

    public LocalDate getDateNaissanceU() {
        return this.dateNaissanceU;
    }

    public Utilisateur dateNaissanceU(LocalDate dateNaissanceU) {
        this.setDateNaissanceU(dateNaissanceU);
        return this;
    }

    public void setDateNaissanceU(LocalDate dateNaissanceU) {
        this.dateNaissanceU = dateNaissanceU;
    }

    public Set<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Utilisateur userRoles(Set<UserRole> userRoles) {
        this.setUserRoles(userRoles);
        return this;
    }

    public Utilisateur addUserRoles(UserRole userRole) {
        this.userRoles.add(userRole);
        userRole.getUtilisateurs().add(this);
        return this;
    }

    public Utilisateur removeUserRoles(UserRole userRole) {
        this.userRoles.remove(userRole);
        userRole.getUtilisateurs().remove(this);
        return this;
    }

    public Set<Patient> getPatients() {
        return this.patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Utilisateur patients(Set<Patient> patients) {
        this.setPatients(patients);
        return this;
    }

    public Utilisateur addPatients(Patient patient) {
        this.patients.add(patient);
        patient.getUtilisateurs().add(this);
        return this;
    }

    public Utilisateur removePatients(Patient patient) {
        this.patients.remove(patient);
        patient.getUtilisateurs().remove(this);
        return this;
    }

    public Set<Etablissement> getEtablissements() {
        return this.etablissements;
    }

    public void setEtablissements(Set<Etablissement> etablissements) {
        if (this.etablissements != null) {
            this.etablissements.forEach(i -> i.removeUtilisateurs(this));
        }
        if (etablissements != null) {
            etablissements.forEach(i -> i.addUtilisateurs(this));
        }
        this.etablissements = etablissements;
    }

    public Utilisateur etablissements(Set<Etablissement> etablissements) {
        this.setEtablissements(etablissements);
        return this;
    }

    public Utilisateur addEtablissements(Etablissement etablissement) {
        this.etablissements.add(etablissement);
        etablissement.getUtilisateurs().add(this);
        return this;
    }

    public Utilisateur removeEtablissements(Etablissement etablissement) {
        this.etablissements.remove(etablissement);
        etablissement.getUtilisateurs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Utilisateur)) {
            return false;
        }
        return id != null && id.equals(((Utilisateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Utilisateur{" +
            "id=" + getId() +
            ", idU=" + getIdU() +
            ", emailU='" + getEmailU() + "'" +
            ", passwordU='" + getPasswordU() + "'" +
            ", nomU='" + getNomU() + "'" +
            ", prenomU='" + getPrenomU() + "'" +
            ", dateNaissanceU='" + getDateNaissanceU() + "'" +
            "}";
    }
}

package polytech.g6.blog.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import polytech.g6.blog.domain.Patient;

public interface PatientRepositoryWithBagRelationships {
    Optional<Patient> fetchBagRelationships(Optional<Patient> patient);

    List<Patient> fetchBagRelationships(List<Patient> patients);

    Page<Patient> fetchBagRelationships(Page<Patient> patients);
}

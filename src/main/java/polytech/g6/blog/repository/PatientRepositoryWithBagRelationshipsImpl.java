package polytech.g6.blog.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import polytech.g6.blog.domain.Patient;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PatientRepositoryWithBagRelationshipsImpl implements PatientRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Patient> fetchBagRelationships(Optional<Patient> patient) {
        return patient.map(this::fetchChambres);
    }

    @Override
    public Page<Patient> fetchBagRelationships(Page<Patient> patients) {
        return new PageImpl<>(fetchBagRelationships(patients.getContent()), patients.getPageable(), patients.getTotalElements());
    }

    @Override
    public List<Patient> fetchBagRelationships(List<Patient> patients) {
        return Optional.of(patients).map(this::fetchChambres).orElse(Collections.emptyList());
    }

    Patient fetchChambres(Patient result) {
        return entityManager
            .createQuery("select patient from Patient patient left join fetch patient.chambres where patient is :patient", Patient.class)
            .setParameter("patient", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Patient> fetchChambres(List<Patient> patients) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, patients.size()).forEach(index -> order.put(patients.get(index).getId(), index));
        List<Patient> result = entityManager
            .createQuery(
                "select distinct patient from Patient patient left join fetch patient.chambres where patient in :patients",
                Patient.class
            )
            .setParameter("patients", patients)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}

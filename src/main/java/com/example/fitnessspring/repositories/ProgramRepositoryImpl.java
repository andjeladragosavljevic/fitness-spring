package com.example.fitnessspring.repositories;

import com.example.fitnessspring.models.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProgramRepositoryImpl implements ProgramRepositoryCustom{
    @PersistenceContext
    private EntityManager em;


    @Override
    public List<Program> findFilteredPrograms(ProgramFilterDTO filterDTO, Pageable pageable, Integer userId, boolean isOwnPrograms) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Program> cq = cb.createQuery(Program.class);
        Root<FitnessProgramEntity> root = cq.from(FitnessProgramEntity.class);


        Join<FitnessProgramEntity, FitnessprogramHasAttributeEntity> attributeJoin = root.join("attributes", JoinType.LEFT);


        cq.select(cb.construct(
                Program.class,
                root.get("id"),
                root.get("name"),
                root.get("description"),
                cb.construct(User.class,
                        root.get("user").get("id"),
                        root.get("user").get("firstName"),
                        root.get("user").get("lastName"),
                        root.get("user").get("email")
                ),
                root.get("user").get("id"),
                cb.construct(Category.class,
                        root.get("category").get("id"),
                        root.get("category").get("name")),
                        root.get("contact"),
                root.get("location"),
                root.get("endDate"),
                root.get("startDate"),
                root.get("difficultyLevel").as(String.class),
                root.get("price")


        ));

        List<Predicate> predicates = new ArrayList<>();


        if(isOwnPrograms)
            predicates.add(cb.equal(root.get("user").get("id"), userId));
        else
            predicates.add(cb.notEqual(root.get("user").get("id"), userId));


        if (filterDTO.getName() != null && !filterDTO.getName().isEmpty()) {
            predicates.add(cb.like(root.get("name"), "%" + filterDTO.getName() + "%"));
        }
        if (filterDTO.getDescription() != null && !filterDTO.getDescription().isEmpty()) {
            predicates.add(cb.like(root.get("description"), "%" + filterDTO.getDescription() + "%"));
        }
        if (filterDTO.getInstructor() != null && !filterDTO.getInstructor().isEmpty()) {
            String[] nameParts = filterDTO.getInstructor().trim().split("\\s+");

            if (nameParts.length == 2) {
                String firstName = nameParts[0];
                String lastName = nameParts[1];

                Predicate firstNamePredicate = cb.like(root.get("user").get("firstName"), "%" + firstName + "%");
                Predicate lastNamePredicate = cb.like(root.get("user").get("lastName"), "%" + lastName + "%");

                predicates.add(cb.and(firstNamePredicate, lastNamePredicate));
            } else {

                String name = nameParts[0];
                Predicate firstNamePredicate = cb.like(root.get("user").get("firstName"), "%" + name + "%");
                Predicate lastNamePredicate = cb.like(root.get("user").get("lastName"), "%" + name + "%");

                predicates.add(cb.or(firstNamePredicate, lastNamePredicate));
            }

        }
        if (filterDTO.getCategoryId() != null) {
            predicates.add(cb.equal(root.get("category").get("id"), filterDTO.getCategoryId()));
        }
        if (filterDTO.getLocation() != null) {
            predicates.add(cb.equal(root.get("location"), filterDTO.getLocation()));
        }
        if (filterDTO.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), filterDTO.getStartDate()));
        }
        if (filterDTO.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), filterDTO.getEndDate()));
        }
        if (filterDTO.getMinPrice() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filterDTO.getMinPrice()));
        }
        if (filterDTO.getMaxPrice() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), filterDTO.getMaxPrice()));
        }

        if(filterDTO.getDifficultyLevel() != null) {
            predicates.add(cb.equal(root.get("difficultyLevel"), filterDTO.getDifficultyLevel()));
        }

        if (filterDTO.getSpecificAttributes() != null && !filterDTO.getSpecificAttributes().isEmpty()) {
            for (AttributeFilter attribute : filterDTO.getSpecificAttributes()) {
                predicates.add(cb.equal(attributeJoin.get("attribute").get("name"), attribute.getName()));
                predicates.add(cb.equal(attributeJoin.get("value"), attribute.getValue()));
            }
        }
        if(filterDTO.getStatus() != null && !filterDTO.getStatus().equals("Active")){
            predicates.add(cb.greaterThan(root.get("endDate"), LocalDate.now()));
            } else if (("Active").equals(filterDTO.getStatus())) {
                predicates.add(cb.lessThan(root.get("endDate"), LocalDate.now()));
            }


        cq.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Program> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Program> programs = query.getResultList();
        for (Program program : programs) {
            List<String> images = em.createQuery("SELECT i.url FROM ImageEntity i WHERE i.fitnessprogram.id = :programId", String.class)
                    .setParameter("programId", program.getId())
                    .getResultList();
            program.setImages(images);

        }
        return programs;
    }
}

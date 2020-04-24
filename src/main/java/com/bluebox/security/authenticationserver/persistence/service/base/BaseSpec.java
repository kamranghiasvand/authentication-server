package com.bluebox.security.authenticationserver.persistence.service.base;

import org.springframework.data.jpa.domain.Specification;

public class BaseSpec<E> {
    private Specification<E> main;

    public Specification<E> equal(String fieldName, final String value) {
        return (Specification<E>) (root, query, cb) -> cb.equal(root.get(fieldName), value);
    }


    public final void and(final Specification<E> specification) {
        if (specification == null)
            return;
        if (main == null) {
            main = Specification.where(specification);
        } else {
            main = main.and(specification);
        }
    }

    Specification<E> get() {
        return main;
    }
}

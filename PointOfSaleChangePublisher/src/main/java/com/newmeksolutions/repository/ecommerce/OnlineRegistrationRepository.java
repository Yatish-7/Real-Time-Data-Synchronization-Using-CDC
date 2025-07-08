package com.newmeksolutions.repository.ecommerce;

import com.newmeksolutions.model.ecommerce.OnlineRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineRegistrationRepository extends JpaRepository<OnlineRegistration, Integer> {
}

package com.test.service.inbound;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InboundRequestRepository extends JpaRepository<InboundRequest,Long> {
}

package com.fy.service;

import com.fy.model.Customer;
import com.fy.repository.CustomerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final @NonNull CustomerRepository customerRepository;

    @Transactional
    public Mono<Customer> save(Customer customer){
        return customerRepository.save(customer)
                .map(c -> {
                    if(c.getFirstname().equals("Dave")){
                        throw new IllegalStateException();
                    }
                    return c;
                });
    }
}

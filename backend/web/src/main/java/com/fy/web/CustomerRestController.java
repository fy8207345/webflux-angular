package com.fy.web;

import com.fy.model.Customer;
import com.fy.service.CustomerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerRestController {

    private final @NonNull CustomerService customerService;

    @PostMapping("")
    public Mono<Customer> add(@RequestBody Customer customer){
        return customerService.save(customer);
    }
}

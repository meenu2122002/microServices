package com.example.ecommerce.customer;


import com.example.ecommerce.exception.CustomerNotFoundException;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
   var customer= customerRepository.save(customerMapper.toCustomer(request));

    return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
    var customer=customerRepository.findById(request.id())
            .orElseThrow(()-> new CustomerNotFoundException(
                    String.format("Cannot update customer :: No customer found with the provided ID %s",request.id())
            ));

    mergeCustomer(customer,request);
    customerRepository.save(customer);





    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
  if(StringUtils.isNotBlank(request.firstname())){
      customer.setFirstname(request.firstname());

  }
        if(StringUtils.isNotBlank(request.lastname())){
            customer.setLastname(request.lastname());

        }
        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());

        }
        if(request.address() !=null ){
            customer.setAddress(request.address());

        }





    }

    public List<CustomerResponse> findAllCustomers() {
        return  customerRepository.findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .collect(Collectors.toList());

    }

    public Boolean existisById(String customerId) {
   return customerRepository.findById(customerId)
           .isPresent();


    }

    public CustomerResponse findById(String customerId) {
    return customerRepository.findById(customerId)
            .map(customerMapper::fromCustomer)
            .orElseThrow(() -> new CustomerNotFoundException( String.format("No customer found having id::%s",customerId)));




    }


    public void deleteCustomer(String customerId) {
    customerRepository.deleteById(customerId);

    }
}

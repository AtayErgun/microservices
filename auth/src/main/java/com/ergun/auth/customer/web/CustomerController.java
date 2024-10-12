package com.ergun.auth.customer.web;



import com.ergun.auth.customer.api.CustomerDto;
import com.ergun.auth.customer.api.CustomerService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/{id}")
    public CustomerResponse getCustomerId(@PathVariable(value = "id") String id) {
        return toResponse(customerService.getCustomer(id));
    }


    @PostMapping
    public CustomerResponse save(@RequestBody CustomerRequest customerRequest) {
        return toResponse(customerService.save(customerRequest.toDto()));
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable(value = "id") String id, @RequestBody CustomerRequest customerRequest) {
        return toResponse(customerService.update(customerRequest.toDto(), id));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable(value = "id") String id) {
        customerService.delete(id);
        return "Silme İşlemi Başarılı";
    }



    public CustomerResponse toResponse(CustomerDto customerDto) {
        return CustomerResponse.builder()
                .id(customerDto.getCustomerId())
                .name(customerDto.getName())
                .surname(customerDto.getSurname())
                .email(customerDto.getEmail())
                .phoneNumber(customerDto.getPhoneNumber())
                .address(customerDto.getAddress())
                .password(customerDto.getPassword())
                .code(200)
                .message("Successful")
                .build();
    }

}

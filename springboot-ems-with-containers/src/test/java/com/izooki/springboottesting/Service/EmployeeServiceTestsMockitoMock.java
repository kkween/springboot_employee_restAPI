package com.izooki.springboottesting.Service;

import com.izooki.springboottesting.model.Employee;
import com.izooki.springboottesting.repository.EmployeeRepository;
import com.izooki.springboottesting.service.EmployeeService;
import com.izooki.springboottesting.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeServiceTestsMockitoMock {

    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    public  void setup(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeOnject(){
        //given - precondition or setup
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();
        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or the behavior that we are going to test
        Employee savedEmployee = employeeService.savedEmployee(employee);

        System.out.println(savedEmployee);
        //then - verify the outputS
        assertThat(savedEmployee).isNotNull();

    }
}

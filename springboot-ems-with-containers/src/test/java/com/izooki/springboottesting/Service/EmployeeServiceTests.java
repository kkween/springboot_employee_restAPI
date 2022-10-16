package com.izooki.springboottesting.Service;

import com.izooki.springboottesting.exception.ResourceNotFoundException;
import com.izooki.springboottesting.model.Employee;
import com.izooki.springboottesting.repository.EmployeeRepository;
import com.izooki.springboottesting.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public  void setup(){
        employee = Employee.builder()
                .id(1L)
                .firstName("Deborah")
                .lastName("Miller")
                .email("deborah@gmail.com")
                .build();
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject(){
        //given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or the behavior that we are going to test
        Employee savedEmployee = employeeService.savedEmployee(employee);

        System.out.println(savedEmployee);
        //then - verify the outputS
        assertThat(savedEmployee).isNotNull();
    }

    //JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException(){
        //given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        //when - action or the behavior that we are going to test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.savedEmployee(employee);
        });

        //then
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    //JUnit test for getAllEmployees method. Positive scenario with full list
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList(){
        //given - precondition or setup
        Employee employee1 = Employee.builder() //1st employee
                .id(2L)
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();

        //This is called a stubbing method
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1)); //2nd employee

        //when - action or the behavior that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //JUnit test for getAllEmployees method. Negative scenario with empty list
    @DisplayName("JUnit test for getAllEmployees method")
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeList(){
        //given - precondition or setup
        Employee employee1 = Employee.builder() //1st employee
                .id(2L)
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();

        //This is called a stubbing method
        given(employeeRepository.findAll()).willReturn(Collections.emptyList()); //2nd employee

        //when - action or the behavior that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    //JUnit test for getEmployeeById method
    @DisplayName("JUnit test for getEmployeeById method")
    @Test
    public void givenGetEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject(){

        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or the behavior that we are going to test
        Employee saveEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertThat(saveEmployee).isNotNull();
    }

    //JUnit test for updateEmployee method
    @DisplayName("JUnit test for updateEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){

        //given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("lizette@hotmail.com");

        //when - action or the behavior that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("lizette@hotmail.com");
    }

    //JUnit test for deleteEmployee method
    @DisplayName("JUnit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        //given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when - action or the behavior that we are going to test
        employeeService.deleteEmployee(employeeId);

        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}

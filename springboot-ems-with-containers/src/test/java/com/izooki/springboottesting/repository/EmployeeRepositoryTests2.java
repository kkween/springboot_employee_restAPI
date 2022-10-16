package com.izooki.springboottesting.repository;

import com.izooki.springboottesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests2 {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Deborah")
                .lastName("miller")
                .email("deborah@gmail.com")
                .build();
    }

    //1
    //JUnit test for save employee operation
//    @DisplayName("JUnit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){



        //given - precondition or setup. Saves employee object to database
        /*Employee employee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();*/

        //when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    //2
    //JUnit test for get all employees operation
    @DisplayName("JUnit test for get all employees operation")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){
        //given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();*/

        Employee employee1 = Employee.builder()
                .firstName("Deborah")
                .lastName("Miller")
                .email("deborah@gmail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or the behavior that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //3
    //JUnit test for get employee by id operation
    @DisplayName("JUnit test for get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        //given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        //when - action or the behavior that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //4
    //JUnit test for get employee by email operation
    @DisplayName("JUnit test for get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject(){
        //given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Valorie")
                .lastName("Reese")
                .email("valorie@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        //when - action or the behavior that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    //5
    //JUnit test for update employee operation
    @DisplayName("JUnit test for update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Deborah")
                .lastName("Miller")
                .email("deborah@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        //when - action or the behavior that we are going to test
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        updatedEmployee.setFirstName("Anais");
        updatedEmployee.setEmail("anais@hotmail.com");
        employeeRepository.save(updatedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("anais@hotmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Anais");
    }

    //6
    //JUnit test for delete employee operation
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        //given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Deborah")
                .lastName("Miller")
                .email("deborah@gmail.com")
                .build();*/
        employeeRepository.save(employee);

        //when - action or the behavior that we are going to test
        //employeeRepository.delete(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    //7
    //JUnit test for custom query using JPQL with index
    @DisplayName("JUnit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject(){
        //given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Deborah")
                .lastName("Miller")
                .email("deborah@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        String firstName = "Deborah";
        String lastName = "Miller";

        //when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //8
    //JUnit test for custom query using JPQL with Named params
    @DisplayName("JUnit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject(){
        //given - precondition or setup
       /* Employee employee = Employee.builder()
                .firstName("Deborah")
                .lastName("Miller")
                .email("deborah@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        String firstName = "Deborah";
        String lastName = "Miller";

        //when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //9
    //JUnit test for custom query using Native  SQL with index
    @DisplayName("JUnit test for custom query using Native  SQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject(){
        //given - precondition or setup
        /*Employee employee = Employee.builder()
                .firstName("Deborah")
                .lastName("Miller")
                .email("deborah@gmail.com")
                .build();*/
        employeeRepository.save(employee);
        //String firstName = "Deborah";
        //String lastName = "Miller";

        //when - action or the behavior that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());
        //Employee savedEmployee = employeeRepository.findByNativeSQLNamed("Deborah", "Miller");

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }
}

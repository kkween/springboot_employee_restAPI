package com.izooki.springboottesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izooki.springboottesting.model.Employee;
import com.izooki.springboottesting.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerContainersIntTests  {

    @Autowired
    MockMvc mockMvc; //used to make Http request using perform() method

    @Autowired
    EmployeeRepository employeeRepository; //used to perform different operations on database

    @Autowired
    ObjectMapper objectMapper; //used for serialization and deserialization

    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{

        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the result or output using assert statements
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employee.getLastName())))
                .andExpect(jsonPath("$.email",
                        is(employee.getEmail())));
    }

    // JUnit test for Get All employees REST API
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Lizette").lastName("Jamison").email("lizette@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Deborah").lastName("Miller").email("deborah@gmail.com").build());
        employeeRepository.saveAll(listOfEmployees);
        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));//1. calls getAllEmployees Rest API

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    // positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();
        employeeRepository.save(employee);


        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    // negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    // JUnit test for update employee REST API - positive scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
        // given - precondition or setup
        //1. save employee object first.
        Employee savedEmployee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        //2. then prepare employee object which contains all the updated employee information
        Employee updatedEmployee = Employee.builder()
                .firstName("Liz")
                .lastName("Jamison")
                .email("liz@gmail.com")
                .build();

        // then call the updated employee REST API
        // when -  action or the behaviour that we are going test
        //Update Employee returns a response, a Json Object
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId()) //getId() = primary key
                .contentType(MediaType.APPLICATION_JSON) //used for upDate Employee
                .content(objectMapper.writeValueAsString(updatedEmployee))); //used for upDate Employee


        // then verify the response of the REST API.
        //verify Json objects firstName, lastName, and emil
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    // JUnit test for update employee REST API - negative scenario
    @Test
    public void

    givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();
        employeeRepository.save(savedEmployee); //will save an updated primary key 9 which will test against employeeId 1

        Employee updatedEmployee = Employee.builder()
                .firstName("Liz")
                .lastName("Jamison")
                .email("liz@gmail.com")
                .build();


        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId) //
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    //JUnit test for delete employee REST API
    @Test
    public void givenEmployeeId_whenDeleteEmployee_then200() throws Exception {
        //given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("Lizette")
                .lastName("Jamison")
                .email("lizette@gmail.com")
                .build();
        employeeRepository.save(savedEmployee); //saves employee object in database table with primary key

        //when - action or the behavior that we are going to test
        //will delete record from the database table
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }
}

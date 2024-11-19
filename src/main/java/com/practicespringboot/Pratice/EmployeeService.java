package com.practicespringboot.Pratice;

import com.practicespringboot.Pratice.databaselayer.EmployeeDatabase;
import com.practicespringboot.Pratice.dto.Employee;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class EmployeeService {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EmployeeService service=new EmployeeService();
        service.sendReminderToEmployee().get();
    }

    private CompletableFuture<Void> sendReminderToEmployee() {
        Executor executor= Executors.newFixedThreadPool(5);
        CompletableFuture<Void> mymethod=CompletableFuture.supplyAsync(()->{
               return EmployeeDatabase.fetchEmployee();
            },executor).thenApplyAsync((employees) -> {
                return employees.stream().filter(employee -> "TRUE".equals(employee.getNewJoiner())).collect(Collectors.toList());
                },executor).thenApplyAsync((employees)->{
                    return employees.stream().filter(employee -> "TRUE".equals(employee.getLearningPending())).collect(Collectors.toList());
                },executor).thenApplyAsync((employees) -> {
            System.out.println("get emails  : " + Thread.currentThread().getName());
            return employees.stream().map(Employee::getEmail).collect(Collectors.toList());
        },executor).thenAcceptAsync((emails)->{
            emails.forEach(EmployeeService::sendEmail);
        },executor);
        return mymethod;

    }

    private static void sendEmail(String email) {
        System.out.println("sending training reminder email to : " + email);
    }
}

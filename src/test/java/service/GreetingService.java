package service;

public class GreetingService {

    public String xmlGreet(String name) {
        return "<greeter><greeting>Hello " + name + "!</greeting></greeter>";
    }
}

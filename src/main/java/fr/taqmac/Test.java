package fr.taqmac;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

    @GetMapping(value = "/test")
    private String getTest(){
        return "Test";
    }

}
